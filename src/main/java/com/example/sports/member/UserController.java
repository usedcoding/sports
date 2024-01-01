package com.example.sports.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
//import net.nurigo.sdk.NurigoApp;
//import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
//import net.nurigo.sdk.message.response.SingleMessageSentResponse;
//import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
//    private final DefaultMessageService messageService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }
        try {
            this.userService.create(userCreateForm.getUsername(), userCreateForm.getNickname(), userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/post/list";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

//    public MessageController() {
//        this.messageService = NurigoApp.INSTANCE.initialize("api key", "api key password", "https://api.coolsms.co.kr" )
//    }
//
//    @PostMapping("/check/secdSMS")
//    @ResponseBody
//    SingleMessageSentResponse sendSMS(PhoneForm phoneForm) {
//
//        Random rand = new Random();
//        String numStr = "";
//        for(int i = 0; i < 6; i++) {
//            String ran = Integer.toString(rand.nextInt(10));
//            numStr += ran;
//        }
//
//        Message message = new Massage();
//        message.setForm("번호 찍히는 핸드폰 번호");
//        message.setTo(phoneForm.getPhone());
//        message.setText("[인증번호안내] 입력하셔야 할 인증번호는 ["+numSet+"]입니다.");
//
//        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
//        System.out.println(response);
//
//        return response;
//    }


}
