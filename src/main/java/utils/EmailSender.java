package utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public static void main(String[] args) {
        final String username = "your_email@example.com";
        final String password = "your_password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.example.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your_email@example.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("qa@example.com"));
            message.setSubject("📋 Automation Test Execution Report");

            String htmlBody = "<h3>Test Execution Summary</h3><p>Report available <a href='file:///path/to/ExtentReport.html'>here</a></p>";
            message.setContent(htmlBody, "text/html");

            Transport.send(message);
            System.out.println("📧 Email Sent!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}