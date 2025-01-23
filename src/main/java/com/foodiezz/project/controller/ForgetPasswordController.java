package com.foodiezz.project.controller;

import com.foodiezz.project.model.User;
import com.foodiezz.project.repository.UserRepository;
import com.foodiezz.project.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Random;
import java.security.SecureRandom;

class OtpGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static int generateOtp() {
        int otp = 0;


            otp = secureRandom.nextInt(90000) + 10000; // Generate a random 5-digit number
            // Combine the OTP with the current timestamp to make it more unique
//            long timestamp = Instant.now().toEpochMilli();
//            otp = Integer.parseInt(Long.toString(otp) + Long.toString(timestamp).substring(10));
            // Check if the OTP is unique by querying your database or cache

        return otp;
    }
}

@Controller
public class ForgetPasswordController {
    @Autowired
     private EmailService emailService;
    @Autowired
     private UserRepository userRepository;
    @Autowired
      private BCryptPasswordEncoder bCrypt;
    Random random = new Random(10000);
    @GetMapping("/forgotpassword")
    public String forgotPassword(){
        return "forgetPass";
    }
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam("email")String email, HttpSession session){
        System.out.println(email);

        int otp= OtpGenerator.generateOtp();
        System.out.println(otp);
        String subject = "OTP Verfication";
        String message = ""+"<div>"+
                "<h1>"+"Hello user, Please enter the 5 digit OTP to reset your password."+"<b>"+"OTP is  "+otp+"</n>"+"<h1>"
                +"<div>";
        boolean flag = this.emailService.sendEmail(subject,message,email);
        if(flag){
            session.setAttribute("otp1",otp);
            session.setAttribute("email",email);
            return "verifyOtp";
        }
        else {
            session.setAttribute("message","Check your email id!!");
            return "forgetPass";
        }
    }
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp")int otp,HttpSession session){
        Integer otp1 =(int)session.getAttribute("otp1");
        String email=(String)session.getAttribute("email");
        if(otp1==otp){
            Optional<User> userOptional = userRepository.findUserByEmail(email);
            if (!userOptional.isPresent()) {
                session.setAttribute("message", "User with this email does not exist, Register Now");
                return "forgetPass";
            }
            else {
                return "passwordChange";
            }
        }
        else{
            session.setAttribute("message","You have entered wrong otp");
            return "verifyOtp";
        }
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newpassword")String newpassword,HttpSession session){
        String email = (String)session.getAttribute("email");
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        User user = optionalUser.get();
        user.setPassword(bCrypt.encode(newpassword));
        userRepository.save(user);
        return "redirect:/shop";
    }
}
