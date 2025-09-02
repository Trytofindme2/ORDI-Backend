package com.example.ordi2.controller;

import com.example.ordi2.DTO.*;
import com.example.ordi2.model.ChatDTO;
import com.example.ordi2.model.ChatMessage;
import com.example.ordi2.model.Comments;
import com.example.ordi2.model.FriendRequestUser;
import com.example.ordi2.model.Reaction;
import com.example.ordi2.model.Receipe;
import com.example.ordi2.model.Report;
import com.example.ordi2.model.User;
import com.example.ordi2.response.LoginResponse;
import com.example.ordi2.response.errorMessage;
import com.example.ordi2.response.successMessage;
import com.example.ordi2.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class userController {

	private final userService userService;
	private final emailService emailService;
	private final receipeService receipeService;
	private final reportService reportService;
	private final savePostService savePostService;
	private final FriendRequestUserService friendRequestUserservice;
	private final ChatMessageService chatMessageService;
	private final CommentsService commentsService;
	private final ReactionService reactionService;

	public userController(ReactionService reactionService, CommentsService commentsService,
			ChatMessageService chatMessageService, FriendRequestUserService friendRequestUserservice,
			userService userService, emailService emailService, receipeService receipeService,
			reportService reportService, savePostService savePostService) {
		this.userService = userService;
		this.emailService = emailService;
		this.receipeService = receipeService;
		this.reportService = reportService;
		this.savePostService = savePostService;
		this.chatMessageService = chatMessageService;
		this.friendRequestUserservice = friendRequestUserservice;
		this.commentsService = commentsService;
		this.reactionService = reactionService;
	}

	@GetMapping("/start")
	public ResponseEntity<ApiResponse<Object>> startup() {
		ApiResponse<Object> response = new ApiResponse<>("success", new successMessage("welcome to start up"));
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("/user/{userid}")
	public ResponseEntity<ApiResponse<Object>> getUserById(@PathVariable("userid") UUID userid) {
		try {
			User user = userService.getUserById(userid); // Implement in service
			ApiResponse<Object> response = new ApiResponse<>("success", user);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("failed", new errorMessage(e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PostMapping("/log-in")
	public ResponseEntity<ApiResponse<Object>> LogIn(@RequestBody User user, HttpServletResponse httpServletResponse) {
		try {
			LoginResponse loginResponse = userService.userLogIn(user.getEmail(), user.getPassword());
			Cookie cookie = new Cookie("user-token", loginResponse.getToken());
			cookie.setHttpOnly(true);
			cookie.setSecure(false);
			cookie.setPath("/");
			cookie.setMaxAge(7 * 24 * 60 * 60);
			httpServletResponse.addCookie(cookie);
			ApiResponse<Object> response = new ApiResponse<>("success", loginResponse);
			return ResponseEntity.status(200).body(response);
		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
			return ResponseEntity.status(401).body(response);
		}
	}


	@PostMapping("/sendVerificationCode")
	public ResponseEntity<ApiResponse<Object>> sendVerificationCode(@RequestBody User user) {
		try {
			String generatedCode = emailService.generateCode();
			emailService.saveCode(user.getEmail(), generatedCode);
			emailService.sendVerificationCode(user.getEmail(), generatedCode);
			ApiResponse<Object> response = new ApiResponse<>("success",
					new successMessage("Email already send to your" + " " + user.getEmail()));
			return ResponseEntity.status(200).body(response);
		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
			return ResponseEntity.status(500).body(response);
		}
	}

	@PostMapping("/verified")
	public ResponseEntity<ApiResponse<Object>> verified(@RequestBody VerificationRequest verificationRequest) {
		try {
			boolean isVerified = emailService.verifyCode(verificationRequest.getEmail(), verificationRequest.getCode());
			if (isVerified) {
				User user = userService.createUser(verificationRequest.getEmail(), verificationRequest.getPassword());
				ApiResponse<Object> response = new ApiResponse<>("success", user);
				return ResponseEntity.status(200).body(response);
			} else {
				ApiResponse<Object> response = new ApiResponse<>("Failed",
						new errorMessage("Invalid verification code"));
				return ResponseEntity.status(400).body(response);
			}
		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
			return ResponseEntity.status(500).body(response);
		}
	}

	@GetMapping("/findByEmail/{email}")
	public ResponseEntity<ApiResponse<Object>> findByEmail(@PathVariable("email") String email) {
		try {
			User user = userService.findUserByEmail(email);
			ApiResponse<Object> response = new ApiResponse<>("success", user);
			return ResponseEntity.status(200).body(response);
		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
			return ResponseEntity.status(401).body(response);
		}
	}

	@PatchMapping(value = "/editProfile/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<Object>> editProfile(@PathVariable("id") UUID id,
			@RequestPart("userUpdateRequest") UserUpdateRequest userUpdateRequest,
			@RequestPart(value = "profileImage", required = false) MultipartFile profileImage) throws IOException {

		User user = userService.updateUserInfo(id, userUpdateRequest, profileImage);
		ApiResponse<Object> response = new ApiResponse<>("success", user);
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping(value = "/createReceipe/{id}", consumes = { "multipart/form-data" })
	public ResponseEntity<ApiResponse<Object>> createReceipe(@PathVariable("id") UUID id,
			@RequestPart("receipe") Receipe receipe,
			@RequestPart(value = "video", required = false) MultipartFile videoFile,
			@RequestPart(value = "images", required = false) List<MultipartFile> imageFiles) {
		try {
			if (videoFile != null && !videoFile.isEmpty()) {
				String videoDir = "/home/lucas/Dev/Java EE/ORDI-Backend/src/main/resources/receipe-video/";
				File dir = new File(videoDir);
				if (!dir.exists())
					dir.mkdirs();

				String filename = UUID.randomUUID() + "_" + videoFile.getOriginalFilename();
				Path filePath = Paths.get(videoDir + filename);
				Files.write(filePath, videoFile.getBytes());

				receipe.setVideoUrl("/api/videos/view/" + filename);
			}

			if (imageFiles != null && !imageFiles.isEmpty()) {
				String imageDir = "/home/lucas/Dev/Java EE/ORDI-Backend/src/main/resources/receipe-images/";
				File dir = new File(imageDir);
				if (!dir.exists())
					dir.mkdirs();

				List<String> imageUrls = new ArrayList<>();
				for (MultipartFile imageFile : imageFiles) {
					String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
					Path filePath = Paths.get(imageDir + filename);
					Files.write(filePath, imageFile.getBytes());

					imageUrls.add("/api/images/view/" + filename);
				}
				receipe.setImageUrls(imageUrls);
			}
			Receipe created = receipeService.createReceipe(id, receipe);
			ApiResponse<Object> response = new ApiResponse<>("success", created);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("error", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}

	@GetMapping("/getPostDetail/{id}")
	public ResponseEntity<ApiResponse<Object>> getReceipeById(@PathVariable("id") UUID id) {
		try {
			Receipe receipe = receipeService.getReceipeById(id);
			return ResponseEntity.ok(new ApiResponse<>("success", receipe));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(new ApiResponse<>("error", e.getMessage()));
		}
	}

	@GetMapping("/getReceipeByUserId/{id}")
	public ResponseEntity<ApiResponse<?>> findReceipeByUserId(@PathVariable("id") UUID id) {
		List<Receipe> receipeList = receipeService.findReceipeByUserId(id);
		ApiResponse<Object> response = new ApiResponse<>("success", receipeList);
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/log-out")
	public ResponseEntity<String> adminLogOut(HttpServletResponse response) {
		Cookie cookie = new Cookie("user-token", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return ResponseEntity.ok("User logged out successfully");
	}

	@PostMapping("/getPosts/{id}")
	public ResponseEntity<ApiResponse<?>> getAllPost(@PathVariable("id") UUID id) {
		List<ReceipeDTO> receipeList = receipeService.getAllReceipePost(id);
		ApiResponse<Object> response = new ApiResponse<>("success", receipeList);
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/submitReport")
	public ResponseEntity<ApiResponse<?>> submitReport(@RequestBody ReportDTO report) {
		Report submitReport = reportService.submitReport(report.getReportedBy(), report.getReceipe(),
				report.getReportReason());
		ApiResponse<Object> response = new ApiResponse<>("success", submitReport);
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/savePost")
	public ResponseEntity<?> savePost(@RequestBody Map<String, UUID> body) {
		UUID postId = body.get("postId");
		UUID userId = body.get("userId");

		boolean saved = savePostService.savePost(postId, userId);
		Map<String, Object> response = new HashMap<>();
		response.put("saved", saved);
		response.put("message", saved ? "Post saved successfully" : "Already saved");
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/unsavePost")
	public ResponseEntity<?> unsavePost(@RequestParam UUID postId, @RequestParam UUID userId) {
		boolean deleted = savePostService.unsavePost(postId, userId);
		Map<String, Object> response = new HashMap<>();
		response.put("unsaved", deleted);
		response.put("message", deleted ? "Post unsaved successfully" : "Not found in saved posts");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/isSaved")
	public ResponseEntity<?> isSaved(@RequestParam UUID postId, @RequestParam UUID userId) {
		boolean saved = savePostService.isSaved(postId, userId);
		Map<String, Object> response = new HashMap<>();
		response.put("saved", saved);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/savePostList/{userId}")
	public ResponseEntity<ApiResponse<List<SavedPostResponseDTO>>> getSavedPostsByUser(@PathVariable UUID userId) {
		List<SavedPostResponseDTO> savedPosts = savePostService.getSavedPostsByUser(userId);
		return ResponseEntity.ok(new ApiResponse<>("success", savedPosts));
	}

	@GetMapping("/count/{postId}")
	public long getSaveCount(@PathVariable UUID postId) {
		return savePostService.getSaveCountForPost(postId);
	}

	@GetMapping("/getOwnPost/{userId}")
	public ResponseEntity<ApiResponse<List<ReceipeDTO>>> getOwnPostByUserId(@PathVariable("userId") UUID userId) {
		try {
			List<ReceipeDTO> userPosts = receipeService.getOwnPostByUserId(userId);
			ApiResponse<List<ReceipeDTO>> response = new ApiResponse<>("success", userPosts

			);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			ApiResponse<List<ReceipeDTO>> response = new ApiResponse<>("error", null);
			return ResponseEntity.status(500).body(response);
		}
	}

	@PostMapping("/getUserData/{userId}")
	public ResponseEntity<ApiResponse<UserProfileDTO>> getUserProfile(@PathVariable("userId") UUID userId) {
		try {
			User currentUser = userService.getUserById(userId);
			Set<UserDTO> followers = currentUser.getFollowers().stream().map(UserDTO::fromEntity)
					.collect(Collectors.toSet());
			Set<UserDTO> followings = currentUser.getFollowing().stream().map(UserDTO::fromEntity)
					.collect(Collectors.toSet());
			Set<UserDTO> friends = currentUser.getFriends().stream().map(UserDTO::fromEntity)
					.collect(Collectors.toSet());
			UserProfileDTO profile = new UserProfileDTO("success", followers, followings, friends);
			ApiResponse<UserProfileDTO> response = new ApiResponse<>("success", profile);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<UserProfileDTO> response = new ApiResponse<>("error", null);
			return ResponseEntity.status(500).body(response);
		}
	}

	@PostMapping("/addFriend")
	public ResponseEntity<ApiResponse<Object>> addFriend(@RequestBody FriendRequestDTO friendRequest) {
		UUID addUserId = friendRequest.getAddUserId();
		UUID receiveUserId = friendRequest.getReceiveUserId();
		User senduser = userService.getUserById(addUserId);
		User receiveduser = userService.getUserById(receiveUserId);
		FriendRequestUser friendRequestUser = new FriendRequestUser();
		friendRequestUser.setSender(senduser);
		friendRequestUser.setReceiver(receiveduser);
		friendRequestUser.setRequestdate(LocalDate.now());
		friendRequestUser.setStatus("false");
		friendRequestUserservice.addFriendRequest(friendRequestUser);
		ApiResponse<Object> response = new ApiResponse<>("success", new successMessage("Friend added successfully!"));
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/makeUnFriend")
	public ResponseEntity<ApiResponse<Object>> makeUnFriend(@RequestBody FriendRequestDTO friendRequest) {
		UUID addUserId = friendRequest.getAddUserId();
		UUID receiveUserId = friendRequest.getReceiveUserId();
		User sendUser = userService.getUserById(addUserId);
		User receivedUser = userService.getUserById(receiveUserId);
		if (sendUser == null || receivedUser == null) {
			return ResponseEntity.status(404).body(new ApiResponse<>("error", "User not found"));
		}
		sendUser.getFriends().remove(receivedUser);
		receivedUser.getFriends().remove(sendUser);
		userService.saveUser(sendUser);
		userService.saveUser(receivedUser);
		ApiResponse<Object> response = new ApiResponse<>("success", new successMessage("UnFriend successfully!"));
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/makeBanFollower")
	public ResponseEntity<ApiResponse<Object>> makeBanFollower(@RequestBody FriendRequestDTO friendRequest) {
		UUID addUserId = friendRequest.getAddUserId();
		UUID receiveUserId = friendRequest.getReceiveUserId();
		User sendUser = userService.getUserById(addUserId);
		User receivedUser = userService.getUserById(receiveUserId);
		if (sendUser == null || receivedUser == null) {
			return ResponseEntity.status(404).body(new ApiResponse<>("error", "User not found"));
		}
		sendUser.getFollowers().remove(receivedUser);
		userService.saveUser(sendUser);
		ApiResponse<Object> response = new ApiResponse<>("success", new successMessage("Follower Ban successfully!"));
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/makeUnFollow")
	public ResponseEntity<ApiResponse<Object>> makeUnFollow(@RequestBody FriendRequestDTO friendRequest) {
		UUID addUserId = friendRequest.getAddUserId();
		UUID receiveUserId = friendRequest.getReceiveUserId();
		User sendUser = userService.getUserById(addUserId);
		User receivedUser = userService.getUserById(receiveUserId);
		if (sendUser == null || receivedUser == null) {
			return ResponseEntity.status(404).body(new ApiResponse<>("error", "User not found"));
		}
		sendUser.getFollowing().remove(receivedUser);
		userService.saveUser(sendUser);
		ApiResponse<Object> response = new ApiResponse<>("success", new successMessage("Follower Ban successfully!"));
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/addFollow")
	public ResponseEntity<ApiResponse<Object>> addFollow(@RequestBody FriendRequestDTO friendRequest) {
		UUID addUserId = friendRequest.getAddUserId();
		UUID receiveUserId = friendRequest.getReceiveUserId();
		User user = userService.getUserById(addUserId);
		User followUser = userService.getUserById(receiveUserId);
		user.getFollowing().add(followUser);
		followUser.getFollowers().add(user);
		userService.saveUser(user);
		userService.saveUser(followUser);
		ApiResponse<Object> response = new ApiResponse<>("success", new successMessage("Follow successfully!"));
		return ResponseEntity.status(200).body(response);
	}

//	@PostMapping("/getFriendPageData/{userId}")
//	public ResponseEntity<ApiResponse<FriendDataDTO>> getFriendPageData(@PathVariable("userId") UUID userId) {
//		try {
//			User currentUser = userService.getUserById(userId);
//			Set<UserDTO> friendRequestlist = friendRequestUserservice.getRequestFriendListByUser(currentUser).stream()
//					.map(UserDTO::fromEntity).collect(Collectors.toSet());
//			Set<UserDTO> friendReceivelist = friendRequestUserservice.getSentFriendListByUser(currentUser).stream()
//					.map(UserDTO::fromEntity).collect(Collectors.toSet());
//			Set<UserDTO> friends = userService.getAllFriends().stream().map(UserDTO::fromEntity)
//					.collect(Collectors.toSet());
//			 Set<UserDTO> availableUsers = userService.getAllFriends().stream()
//		                .filter(user -> !user.getId().equals(currentUser.getId())) // exclude self
//		                .map(UserDTO::fromEntity)
//		                .filter(u -> !friends.contains(u)) // exclude already friends
//		                .filter(u -> !friendRequestlist.contains(u)) // exclude received requests
//		                .filter(u -> !friendReceivelist.contains(u)) // exclude sent requests
//		                .collect(Collectors.toSet());
//			FriendDataDTO data = new FriendDataDTO("success", availableUsers, friendRequestlist, friendReceivelist);
//			ApiResponse<FriendDataDTO> response = new ApiResponse<>("success", data);
//			return ResponseEntity.ok(response);
//		} catch (Exception e) {
//			ApiResponse<FriendDataDTO> response = new ApiResponse<>("error", null);
//			return ResponseEntity.status(500).body(response);
//		}
//	}

//	@PostMapping("/getFriendPageData/{userId}")
//	public ResponseEntity<ApiResponse<FriendDataDTO>> getFriendPageData(@PathVariable("userId") UUID userId) {
//	    try {
//	        User currentUser = userService.getUserById(userId);
//	        Set<UserDTO> friends = currentUser.getFriends().stream()
//	                .map(UserDTO::fromEntity)
//	                .collect(Collectors.toSet());
//	        Set<UserDTO> friendRequestlist = friendRequestUserservice.getRequestFriendListByUser(currentUser).stream()
//	                .map(UserDTO::fromEntity)
//	                .filter(u -> !friends.contains(u))
//	                .collect(Collectors.toSet());
//	        Set<UserDTO> friendReceivelist = friendRequestUserservice.getSentFriendListByUser(currentUser).stream()
//	                .map(UserDTO::fromEntity)
//	                .filter(u -> !friends.contains(u))
//	                .collect(Collectors.toSet());
//	        Set<UserDTO> availableUsers = userService.getAllUsers().stream()
//	                .filter(user -> !user.getId().equals(currentUser.getId())) // exclude self
//	                .map(UserDTO::fromEntity)
//	                .filter(u -> !friends.contains(u))
//	                .filter(u -> !friendRequestlist.contains(u))
//	                .filter(u -> !friendReceivelist.contains(u))
//	                .collect(Collectors.toSet());
//
//	        FriendDataDTO data = new FriendDataDTO("success", availableUsers, friendRequestlist, friendReceivelist);
//	        ApiResponse<FriendDataDTO> response = new ApiResponse<>("success", data);
//	        return ResponseEntity.ok(response);
//	    } catch (Exception e) {
//	        ApiResponse<FriendDataDTO> response = new ApiResponse<>("error", null);
//	        return ResponseEntity.status(500).body(response);
//	    }
//	}

	@PostMapping("/getFriendPageData/{userId}")
	public ResponseEntity<ApiResponse<FriendDataDTO>> getFriendPageData(@PathVariable("userId") UUID userId) {
		try {
			User currentUser = userService.getUserById(userId);
			Set<UserDTO> friends = currentUser.getFriends().stream().map(UserDTO::fromEntity)
					.collect(Collectors.toSet());
			Set<UserDTO> friendRequestlist = friendRequestUserservice.getRequestFriendListByUser(currentUser).stream()
					.map(UserDTO::fromEntity).filter(u -> !friends.contains(u)).collect(Collectors.toSet());
			Set<UserDTO> friendReceivelist = friendRequestUserservice.getSentFriendListByUser(currentUser).stream()
					.map(UserDTO::fromEntity).filter(u -> !friends.contains(u)).collect(Collectors.toSet());
			Set<UUID> excludedUserIds = new HashSet<>();
			excludedUserIds.add(currentUser.getId());
			friends.forEach(f -> excludedUserIds.add(f.getId()));
			friendRequestlist.forEach(f -> excludedUserIds.add(f.getId()));
			friendReceivelist.forEach(f -> excludedUserIds.add(f.getId()));
			Set<UserDTO> availableUsers = userService.getAllUsers().stream()
					.filter(user -> !excludedUserIds.contains(user.getId())).map(UserDTO::fromEntity)
					.collect(Collectors.toSet());
			FriendDataDTO data = new FriendDataDTO("success", availableUsers, friendRequestlist, friendReceivelist);
			ApiResponse<FriendDataDTO> response = new ApiResponse<>("success", data);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			ApiResponse<FriendDataDTO> response = new ApiResponse<>("error", null);
			return ResponseEntity.status(500).body(response);
		}
	}

	@PostMapping("/cancelFriend")
	public ResponseEntity<ApiResponse<Object>> cancelFriendRequest(@RequestBody FriendRequestDTO friendRequest) {
		UUID addUserId = friendRequest.getAddUserId();
		UUID receiveUserId = friendRequest.getReceiveUserId();
		User sender = userService.getUserById(addUserId);
		User receiver = userService.getUserById(receiveUserId);

		// Check if friend request exists
		Optional<FriendRequestUser> existingRequest = friendRequestUserservice.findBySenderAndReceiver(sender,
				receiver);

		if (existingRequest.isPresent()) {
			friendRequestUserservice.delete(existingRequest.get());
			ApiResponse<Object> response = new ApiResponse<>("success",
					new successMessage("Friend cancel successfully!"));
			return ResponseEntity.ok(response);
		} else {
			ApiResponse<Object> response = new ApiResponse<>("error",
					new successMessage("No friend request found to cancel."));
			return ResponseEntity.status(400).body(response);
		}
	}

	@PostMapping("/confirmFriend")
	public ResponseEntity<ApiResponse<Object>> confirmFriendRequest(@RequestBody FriendRequestDTO friendRequest) {
		UUID addUserId = friendRequest.getAddUserId(); // the one accepting
		UUID receiveUserId = friendRequest.getReceiveUserId(); // the one who sent
		User receiver = userService.getUserById(addUserId); // current user
		User sender = userService.getUserById(receiveUserId); // request sender

		Optional<FriendRequestUser> existingRequest = friendRequestUserservice.findBySenderAndReceiver(sender,
				receiver);
		if (existingRequest.isPresent()) {
			// Delete the friend request
			friendRequestUserservice.delete(existingRequest.get());

			// Add to each other’s friend lists
			receiver.getFriends().add(sender);
			sender.getFriends().add(receiver);

			// Save updates
			userService.saveUser(receiver);
			userService.saveUser(sender);

			ApiResponse<Object> response = new ApiResponse<>("success",
					new successMessage("Friend confirmed successfully!"));
			return ResponseEntity.ok(response);
		} else {
			ApiResponse<Object> response = new ApiResponse<>("error",
					new successMessage("No friend request found to confirm."));
			return ResponseEntity.status(400).body(response);
		}
	}

	@PostMapping("/getFriendList/{userId}")
	public ResponseEntity<ApiResponse<FriendListDTO>> getFriendlist(@PathVariable("userId") UUID userId) {
		try {
			User currentUser = userService.getUserById(userId);

			Set<UserDTO> friends = currentUser.getFriends().stream().map(UserDTO::fromEntity)
					.collect(Collectors.toSet());
			FriendListDTO data = new FriendListDTO("success", friends);
			ApiResponse<FriendListDTO> response = new ApiResponse<>("success", data);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<FriendListDTO> response = new ApiResponse<>("error", null);
			return ResponseEntity.status(500).body(response);
		}
	}

	@PostMapping("/getFriendData")
	public ResponseEntity<ApiResponse<List<FriendMessageDTO>>> getFriendData(
			@RequestBody FriendRequestDTO friendRequest) {
		try {
			UUID addUserId = friendRequest.getAddUserId();
			UUID receiveUserId = friendRequest.getReceiveUserId();
			User user1 = userService.getUserById(addUserId);
			User user2 = userService.getUserById(receiveUserId);
			List<ChatMessage> chatMessages = chatMessageService.getMessagesBetweenUsers(user1, user2);
			List<ChatMessageDTO> chatMessageDTOs = chatMessages.stream().map(m -> new ChatMessageDTO(
					m.getSender().getId(), m.getReceiver().getId(), m.getTextContent(),
					m.getSendReceipe() != null ? new ChatReceipeDTO(m.getSendReceipe().getId(),
							m.getSendReceipe().getTitle(), m.getSendReceipe().getDescription(),m.getSendReceipe().getImageUrls(),
							m.getSendReceipe().getUser().getId(), m.getSendReceipe().getUser().getName(),
							m.getSendReceipe().getUser().getProfile_URl(), m.getSendReceipe().getUser().getEmail())
							: null,
					m.getSentAt()))

					.toList();
			FriendMessageDTO friendMessageDTO = new FriendMessageDTO(UserDTO.fromEntity(user1), // current user
					UserDTO.fromEntity(user2), // friend
					chatMessageDTOs);
			List<FriendMessageDTO> messageDTOs = List.of(friendMessageDTO); // single conversation
			ApiResponse<List<FriendMessageDTO>> response = new ApiResponse<>("success", messageDTOs);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
			return ResponseEntity.status(500).body((ApiResponse) response);
		}
	}

	@PostMapping("/writemessage")
	public ResponseEntity<ApiResponse<List<CommentDTO>>> writeMessage(@RequestBody ChatDTO message) {
		User sender = userService.findUserByEmail(message.getSenderEmail());
		User receiver = userService.findUserByEmail(message.getReceiverEmail());
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setSender(sender);
		chatMessage.setReceiver(receiver);
		chatMessage.setTextContent(message.getTextcontent());
		chatMessage.setSentAt(LocalDateTime.now());
		chatMessageService.save(chatMessage);
		return null;
	}

	@PostMapping("/getComments/{selectedCommentPostId}")
	public ResponseEntity<ApiResponse<List<CommentDTO>>> getComments(
			@PathVariable("selectedCommentPostId") UUID postId) {
		try {
			Receipe receipe = receipeService.getReceipeById(postId);
			List<Comments> comments = commentsService.getCommentsByReceipe(receipe);
			List<CommentDTO> commentDTOs = comments.stream().map(c -> {
				CommentDTO dto = new CommentDTO();
				dto.setCommentId(c.getCommentId());
				dto.setPostId(postId);
				dto.setCommentUserId(c.getUser().getId()); // Assuming Comment has a User field
				dto.setName(c.getUser().getName());
				dto.setEmail(c.getUser().getEmail());
				dto.setProfile_URl(c.getUser().getProfile_URl());
				dto.setContent(c.getContent());
				return dto;
			}).toList();
			ApiResponse<List<CommentDTO>> response = new ApiResponse<>("success", commentDTOs);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
			return ResponseEntity.status(500).body((ApiResponse) response);
		}
	}

	@PostMapping("/addComment") // make it match frontend
	public ResponseEntity<ApiResponse<CommentDTO>> addComment(@RequestBody CommentDTO commentDTO) {
		try {
			Receipe receipe = receipeService.getReceipeById(commentDTO.getPostId());
			User user = userService.getUserById(commentDTO.getCommentUserId());
			Comments comment = new Comments();
			comment.setContent(commentDTO.getContent());
			comment.setUser(user);
			comment.setReceipe(receipe);
			comment.setCreatedAt(LocalDateTime.now());
			Comments savedComment = commentsService.save(comment);
			CommentDTO responseDTO = new CommentDTO();
			responseDTO.setCommentId(savedComment.getCommentId());
			responseDTO.setPostId(receipe.getId());
			responseDTO.setCommentUserId(user.getId());
			responseDTO.setContent(savedComment.getContent());
			ApiResponse<CommentDTO> response = new ApiResponse<>("success", responseDTO);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
			return ResponseEntity.status(500).body((ApiResponse) response);
		}
	}

	@PostMapping("/addReaction")
	public ResponseEntity<ApiResponse<Object>> addReaction(@RequestBody FriendRequestDTO friendRequest)
			throws Exception {
		UUID userId = friendRequest.getAddUserId();
		UUID postId = friendRequest.getReceiveUserId();
		User user = userService.getUserById(userId);
		Receipe receipe = receipeService.getReceipeById(postId);
		Reaction reaction = reactionService.addOrUpdateReaction(user, receipe, 1);
		String message = (reaction == null) ? "removed" : "added";

		Map<String, Object> data = new HashMap<>();
		data.put("action", message); // <-- easier for frontend
		ApiResponse<Object> response = new ApiResponse<>("success", data);
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/deleteComment/{deleteCommentId}")
	public ResponseEntity<?> deleteComments(@PathVariable("deleteCommentId") UUID deleteCommentid) {
		boolean deleted = commentsService.deleteCommentById(deleteCommentid);
		Map<String, Object> response = new HashMap<>();
		response.put("unsaved", deleted);
		response.put("message", deleted ? "Comment delete successfully" : "Not found in saved posts");
		return ResponseEntity.ok(response);

	}

	@PostMapping("/deletePost/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable("postId") UUID postid) {
		boolean deleted = receipeService.deleteReceipeById(postid);
		Map<String, Object> response = new HashMap<>();
		response.put("unsaved", deleted);
		response.put("message", deleted ? "Post delete successfully" : "Not found in delete post");
		return ResponseEntity.ok(response);

	}

}
