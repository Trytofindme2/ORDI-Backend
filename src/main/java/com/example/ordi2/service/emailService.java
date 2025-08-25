package com.example.ordi2.service;

import com.example.ordi2.helper.customException;
import com.example.ordi2.model.User;
import com.example.ordi2.repo.userRepo;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class emailService
{
    private final userRepo userRepo;
    @Value("${spring.mail.username}")
    private String senderMail;

    private final JavaMailSender javaMailSender;

    private final HashMap<String,String> codestore = new HashMap<>();

    private final ScheduledExecutorService schedulerService =  Executors.newScheduledThreadPool(1);

    public String generateCode(){
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    @Autowired
    public emailService(JavaMailSender javaMailSender, userRepo userRepo){
        this.javaMailSender = javaMailSender;
        this.userRepo = userRepo;
    }

    public void saveCode(String email, String code){
        codestore.put(email,code);
        schedulerService.schedule(()-> {
            codestore.remove(email);
        }, 5 , TimeUnit.MINUTES);
    }

    public boolean verifyCode(String email, String code) {
        String storedCode = codestore.get(email);
        if (storedCode == null) {
            throw new customException("Verification code has expired");
        }
        if (!storedCode.equals(code)) {
            throw new customException("Verification code does not match");
        }
        codestore.remove(email);
        return true;
    }

//    public void resendVerificationCode(){
//
//    }

    public void sendVerificationCode(String receiver_mail , String code){
        User user = userRepo.findUserByEmail(receiver_mail);
        if(user != null){
            throw new customException("User already Account exited");
        }
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message , true , "UTF-8");
            helper.setTo(receiver_mail);
            helper.setSubject("Your Verification Code");
            helper.setFrom(senderMail);

            String htmlContent = """
    <html>
    <body style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px;">
        <div style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 30px; box-shadow: 0 2px 6px rgba(0,0,0,0.1);">
            <h2 style="color: #333333; text-align: center;">Welcome to <span style="color: #FF5722;">ORDI</span>!</h2>
            <p style="font-size: 16px; color: #444444;">Thank you for signing up. To complete your registration, please use the verification code below:</p>
            <div style="font-size: 32px; font-weight: bold; color: #2E86DE; text-align: center; margin: 30px 0;">
                %s
            </div>
            <p style="font-size: 14px; color: #888888; text-align: center;">
                This code is valid for 5 minutes. Do not share it with anyone.
            </p>
            <p style="font-size: 14px; color: #888888; text-align: center; margin-top: 40px;">
                — The ORDI Team
            </p>
        </div>
    </body>
    </html>
""".formatted(code);

            helper.setText(htmlContent,true);
            javaMailSender.send(message);

        }catch (Exception e){
            throw new customException("Failed to send email" + e.getMessage());
        }

    }

}
