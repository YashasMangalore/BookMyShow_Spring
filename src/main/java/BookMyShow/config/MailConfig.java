package BookMyShow.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    private final Dotenv dotenv;

    public MailConfig() {
        dotenv = Dotenv.load();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(dotenv.get("SPRING_MAIL_HOST"));
        mailSender.setPort(Integer.parseInt(dotenv.get("SPRING_MAIL_PORT")));

        mailSender.setUsername(dotenv.get("SPRING_MAIL_USERNAME"));
        mailSender.setPassword(dotenv.get("SPRING_MAIL_PASSWORD"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH"));
        props.put("mail.smtp.starttls.enable", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE"));
        props.put("mail.debug", "true");

        return mailSender;
    }
}
