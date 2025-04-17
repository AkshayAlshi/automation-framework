package utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    public static void main(String[] args) {
        final String username = System.getProperty("email.username");
        final String password = System.getProperty("email.password");

        if (username == null || password == null) {
            System.err.println("❌ Email credentials not provided.");
            return;
        }

        // Configure SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("alshiakshay55@gmail.com"));  // ✅ Update to your team
            message.setSubject("✅ Automation Test Report");

            // 🌐 Jenkins link to report
            String buildUrl = System.getenv("BUILD_URL");
            String reportLink = (buildUrl != null)
                    ? buildUrl + "artifact/target/ExtentReport.html"
                    : "Extent report link not available.";

            // 📧 Email body
            String htmlBody = "<h3>📊 Automation Test Execution Summary</h3>"
                    + "<p>The latest test run report is attached.</p>"
                    + "<p>📎 <a href='" + reportLink + "'>View Report on Jenkins</a></p>";

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlBody, "text/html");

            // 📎 Attach Extent Report
            File reportFile = new File("target/ExtentReport.html");
            if (!reportFile.exists()) {
                System.out.println("⚠️ ExtentReport.html not found.");
            }

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(reportFile);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("📧 Email with Extent Report sent successfully!");

        } catch (MessagingException | IOException e) {
            System.err.println("❌ Email sending failed:");
            e.printStackTrace();
        }
    }
}
