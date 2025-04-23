package utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailSender  {

    public static void main(String[] args) {
        final String username = System.getProperty("email.username");
        final String password = System.getProperty("email.password");

        if (username == null || password == null) {
            System.err.println("❌ Email credentials not provided.");
            return;
        }

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
                    InternetAddress.parse("vgawande@microproindia.com"));
            message.setSubject("📋 Automation Test Report");

            String testSummary = getTestSummary();
            String buildUrl = System.getenv("BUILD_URL");
            String reportLink = (buildUrl != null)
                    ? buildUrl + "artifact/target/reports/ExtentReport.html"
                    : "Report path not available.";

            String htmlBody = testSummary +
                    "<p>📎 Full ExtentReport available <a href='" + reportLink + "'>here</a>.</p>";

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlBody, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Attach zipped Extent report
            File reportZip = new File("target/ExtentReport.zip");
            if (reportZip.exists()) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(reportZip);
                multipart.addBodyPart(attachmentPart);
            } else {
                System.out.println("⚠️ Zipped report not found. Skipping attachment.");
            }

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("📧 Email with report sent successfully!");

        } catch (MessagingException | IOException e) {
            System.err.println("❌ Email sending failed:");
            e.printStackTrace();
        }
    }

    private static String getTestSummary() {
        try {
            File file = new File("target/surefire-reports/testng-results.xml");
            if (!file.exists()) {
                return "<p>⚠️ testng-results.xml not found.</p>";
            }

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            Element root = doc.getDocumentElement();

            String passed = root.getAttribute("passed");
            String failed = root.getAttribute("failed");
            String skipped = root.getAttribute("skipped");

            return "<h3>📊 Test Execution Summary</h3>" +
                    "✔ Passed: " + passed + "<br>" +
                    "❌ Failed: " + failed + "<br>" +
                    "🚫 Skipped: " + skipped + "<br><br>";

        } catch (Exception e) {
            return "<p>⚠️ Unable to parse test results.</p>";
        }
    }
}
