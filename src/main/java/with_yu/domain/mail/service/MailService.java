package with_yu.domain.mail.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import with_yu.common.exception.CustomException;
import with_yu.common.response.error.ErrorType;
import with_yu.common.util.MailProperties;
import with_yu.common.util.RedisProperties;
import with_yu.common.util.RedisUtil;
import with_yu.config.MailConfig;
import with_yu.domain.mail.dto.CodeVerificationReq;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailConfig mailConfig;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;

    @Async
    public void sendMail(String email) throws Exception {

            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, false, "utf-8");
            mimeMessageHelper.setFrom(mailConfig.getSender());
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(MailProperties.CODE_MAIL_SUBJECT);

            Context context = new Context();
            String code = createRandomCode();
            context.setVariable("code", code);
            String html = templateEngine.process("mail", context);
            mimeMessageHelper.setText(html, true);

            javaMailSender.send(mimeMailMessage);
            
            String key = RedisProperties.CODE_PREFIX + email;
            redisUtil.save(key, code, RedisProperties.CODE_EXPIRATION);
    }

    @Transactional
    public void verifyCode(CodeVerificationReq req){
        String key = RedisProperties.CODE_PREFIX + req.email();
        if(!redisUtil.exists(key))
            throw new CustomException(ErrorType.VERIFICATION_CODE_EXPIRED);

        String code = (String) redisUtil.get(key);

        if(!isValidCode(req.code(), code))
            throw new CustomException(ErrorType.WRONG_CODE);
        else{
            redisUtil.delete(key);
        }
    }

    private boolean isValidCode(String actual, String expected){
        return actual.equals(expected);
    }

    private String createRandomCode(){
        Random rand = new Random();

        return String.valueOf(rand.nextInt(999999));
    }

}
