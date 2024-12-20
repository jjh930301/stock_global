package stock.global.core.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SmtpConfig {

    @Bean
    public JavaMailSender mailSender(Environment environment) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        String active = environment.getProperty("spring.profiles.active");
        Properties props = mailSender.getJavaMailProperties();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        if(active != null && active.equals("local")) {
            mailSender.setUsername(environment.getProperty("spring.mail.username"));
            mailSender.setPassword(environment.getProperty("spring.mail.password"));
            props.put("mail.debug", "true");
        } else {
            mailSender.setUsername(System.getenv("MAIL_ID"));
            mailSender.setPassword(System.getenv("MAIL_PW"));
        }
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(props);
        mailSender.setDefaultEncoding("UTF-8");
        return mailSender;
    }
}
