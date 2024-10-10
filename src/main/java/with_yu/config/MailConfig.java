package with_yu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${mail.admin-mail}") private String username;
    @Value("${mail.password}") private String password;
    @Value("${mail.host}") private String host;
    @Value("${mail.port}") private int port;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties javaMailProperties =  new Properties();
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.debug", "false");
        javaMailProperties.put("mail.smtp.ssl.trust", host);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.3");

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    public String getSender(){
        return username;
    }
}
