package crypto_archive.com;

import crypto_archive.com.api.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        SpringApplication.run(Main.class, args);

    }
}