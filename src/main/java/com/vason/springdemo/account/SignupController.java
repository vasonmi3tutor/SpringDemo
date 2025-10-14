package com.vason.springdemo.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignupController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("SignupDto",new SignupDto());
        return "signup.html";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("SignupDto") SignupDto signupDto) {
        // Check if passwords match
        if(!signupDto.getPassword().equals(signupDto.getConfirmPassword())) {
            return "redirect:/signup?passwordError";
        }

        // Check for existing username
        if(userRepository.findByUsername(signupDto.getUsername()) != null) {
            return "redirect:/signup?usernameError";
        }

        // Create and save a new user
        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        userRepository.save(user);
        return "redirect:/login?signupSuccess";
    }
}
