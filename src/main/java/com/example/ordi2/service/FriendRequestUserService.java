package com.example.ordi2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ordi2.model.FriendRequestUser;
import com.example.ordi2.model.User;
//import com.example.ordi2.model.FriendRequestUser;
import com.example.ordi2.repo.FriendRequestUserRepo;

import jakarta.transaction.Transactional;

@Service
public class FriendRequestUserService {

	@Autowired
	private FriendRequestUserRepo friendRequestUserRepo;
	public void addFriendRequest(FriendRequestUser friendRequestUser) {
		// TODO Auto-generated method stub
		friendRequestUserRepo.save(friendRequestUser);
	}
	public List<User> getSentFriendListByUser(User user) {
		List<FriendRequestUser> requests = friendRequestUserRepo.findByReceiver(user);
		return requests.stream().map(FriendRequestUser::getSender).collect(Collectors.toList());
	}

	public List<User> getRequestFriendListByUser(User user) {
		List<FriendRequestUser> requests = friendRequestUserRepo.findBySender(user);
		return requests.stream().map(FriendRequestUser::getReceiver).collect(Collectors.toList());
	}
//	@Transactional
//	public void deleteByReceiverAndSender(User receiver, User sender) {
//		FriendRequestUser request = friendRequestUserRepo.findByReceiverAndSender(receiver, sender);
//		if (request != null) {
//			friendRequestUserRepo.delete(request);
//		}
//	}
	public Optional<FriendRequestUser> findBySenderAndReceiver(User sender, User receiver) {
		// TODO Auto-generated method stub
		return friendRequestUserRepo.findByReceiverAndSender(receiver, sender);
	}
	@Transactional
	public void delete(FriendRequestUser friendRequestUser) {
		// TODO Auto-generated method stub
		friendRequestUserRepo.delete(friendRequestUser);
		
	}
	

}
