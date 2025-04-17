package utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.*;
import java.nio.file.*;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class EmailSender {

    public static void main(String[] args) {
        final String username = System.getProperty("email.username");
        final String password = System.getProperty("email.password");

        if (username == null || password == null) {
            System.err.println("❌ Email credentials not provided.");
            return;
        }

        try {
            // Zip the ExtentReports folder (target/reports)
            String reportDirPath = "target/reports";
            String zipFilePath = "target/ExtentReport.zip";
            zipDirectory(reportDirPath, zipFilePath);

            // Prepare email properties
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

            // Compose email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("alshiakshay55@gmail.com"));
            message.setSubject("📋 Automation Test Report (Extent)");

            String htmlBody = "<h3>📊 Automation Test Summary</h3>" +
                    "<p>Please find the attached ExtentReport.zip for detailed test results.</p>";

            // Email body
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(htmlBody, "text/html");

            // Attachment
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile(zipFilePath);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            multipart.addBodyPart(attachment);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("📧 Email sent with ExtentReport.zip attached!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to send email.");
        }
    }

    // Utility to zip a directory
    private static void zipDirectory(String sourceDirPath, String zipFilePath) throws IOException {
        Path zipPath = Paths.get(zipFilePath);
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            Path sourcePath = Paths.get(sourceDirPath);
            Files.walk(sourcePath).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(path).toString());
                try {
                    zs.putNextEntry(zipEntry);
                    Files.copy(path, zs);
                    zs.closeEntry();
                } catch (IOException e) {
                    System.err.println("❌ Error zipping file: " + path);
                    e.printStackTrace();
                }
            });
        }
    }
}
