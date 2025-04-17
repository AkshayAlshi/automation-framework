package utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public static void main(String[] args) {
        // Replace with your actual sender's email and app password (not your real Gmail password)
        final String username = "akshayalshi@gmail.com";
        final String password = "your_app_password"; // Use an app password if Gmail has 2FA

        // Set up SMTP server (Gmail SMTP example)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create session with authentication
        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            // Prepare the message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse("qa@example.com") // Replace with your actual recipient
            );
            message.setSubject("📋 Automation Test Execution Report");

            // Use Jenkins BUILD_URL to create report link dynamically
            String buildUrl = System.getenv("BUILD_URL");
            String reportLink = (buildUrl != null)
                ? buildUrl + "artifact/target/surefire-reports/"
                : "Report path not available in Jenkins environment.";

            String htmlBody = "<h3>Test Execution Summary</h3>"
                + "<p>Report available <a href='" + reportLink + "'>here</a></p>";

            message.setContent(htmlBody, "text/html");

            // Send the message
            Transport.send(message);
            System.out.println("📧 Email Sent Successfully!");

        } catch (MessagingException e) {
            System.err.println("❌ Failed to send email:");
            e.printStackTrace();
        }
    }
}
