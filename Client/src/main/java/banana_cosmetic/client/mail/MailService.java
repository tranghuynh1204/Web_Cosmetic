package banana_cosmetic.client.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService{


    @Autowired
    private JavaMailSender emailSender;

    public void sendMail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        message.setFrom("bananacosmeticweb@gmail.com");
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);

        message.setContent(body, "text/html; charset=utf-8");

        emailSender.send(message);
    }
}
