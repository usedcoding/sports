package com.example.sports;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Component;

@Component
public class SmsUtil {

    //value 사용 했더니 오류 발생 해결 필요
    //child "apiKey" fails because ["apiKey" length must be 16 characters long]
//   @Value("${coolsms.api.key}")
    private String apiKey = "NCS8AFP40RW1XMBJ";
    //변수 안에 정해진 경로를 담을 수 있다.
//    @Value("${coolsms.api.secret}")
    private String apiSecretKey = "EMR2ALYP1LSA12KQQB9B3Z9C8JCTM8JK";

    private DefaultMessageService messageService;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    // 단일 메시지 발송 예제
    public SingleMessageSentResponse sendOne(String from, String to) {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(from);
        message.setTo(to);
        message.setText("수락 되었습니다.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        return response;
    }

}