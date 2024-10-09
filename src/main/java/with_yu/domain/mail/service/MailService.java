package with_yu.domain.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import with_yu.common.util.MailProperties;
import with_yu.config.MailConfig;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailConfig mailConfig;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendMail(String email) throws MessagingException {
        try {


            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true, "utf-8");
            mimeMessageHelper.setFrom(mailConfig.getSender());
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(MailProperties.CODE_MAIL_SUBJECT);

            Context context = new Context();
            String code = createRandomCode();
            context.setVariable("code", code);
            String html = templateEngine.process("mail", context);
            mimeMessageHelper.setText(html, true);

            javaMailSender.send(mimeMailMessage);
            
            // redis 도입 필요
        } catch (MessagingException e) {
            throw e; // rethrown
        }

    }

    private String createRandomCode(){
        Random rand = new Random();

        return String.valueOf(rand.nextInt(999999));
    }

}
