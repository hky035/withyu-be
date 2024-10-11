package with_yu.domain.mail.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import with_yu.common.exception.CustomException;
import with_yu.common.response.error.ErrorType;
import with_yu.common.response.success.SuccessRes;
import with_yu.common.response.success.SuccessType;
import with_yu.domain.mail.dto.CodeVerificationReq;
import with_yu.domain.mail.service.MailService;

@Controller
@RequestMapping("/v1/mail/")
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailService mailService;

    @GetMapping("/email-verification")
    public ResponseEntity<?> emailVerification(@RequestParam("email") String email) {
        try{
            mailService.sendMail(email);
        } catch(Exception e) {
            log.error("Failed to send email. error = {}, message={}", e.getClass(), e.getMessage());
            throw new CustomException(ErrorType.MAIL_SEND_FAILED);
        }

        return ResponseEntity.status(SuccessType.MAIL_SEND_SUCCESS.getStatus())
                .body(SuccessRes.from(SuccessType.MAIL_SEND_SUCCESS));
    }

    @PostMapping("/email-verification")
    public ResponseEntity<?> codeVerification(@RequestBody CodeVerificationReq req){
        mailService.verifyCode(req);
        return ResponseEntity.status(SuccessType.VERIFY_CODE_SUCCESS.getStatus())
                .body(SuccessRes.from(SuccessType.VERIFY_CODE_SUCCESS));
    }

}
