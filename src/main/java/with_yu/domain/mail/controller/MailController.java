package with_yu.domain.mail.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import with_yu.common.exception.CustomException;
import with_yu.common.response.error.ErrorType;
import with_yu.domain.mail.service.MailService;

@Controller
@RequestMapping("/v1/mail/")
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailService mailService;

    @PostMapping("/email-verification")
    public ResponseEntity<?> emailVerification(@RequestParam("email") String email) {
        try{
            mailService.sendMail(email);
        } catch(Exception e) {
            log.error("Failed to send email. error = {}, message={}", e.getClass(), e.getMessage());
            throw new CustomException(ErrorType.MAIL_SEND_FAILED);
        }

        return ResponseEntity.ok().body(null);
    }

}
