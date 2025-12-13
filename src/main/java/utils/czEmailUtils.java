package utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Utility class để gửi email
 * Hỗ trợ cả gửi đơn lẻ và BCC hàng loạt
 */
public class czEmailUtils {
    
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static String EMAIL_FROM;
    private static String EMAIL_PASSWORD;
    
    private static void setup() {
    	try (InputStream in = XJdbc.class.getResourceAsStream("/resources.properties")) {
    		Properties p = new Properties();
    		p.load(in);
    		EMAIL_FROM = p.getProperty("email.username");
    		EMAIL_PASSWORD = p.getProperty("email.password");
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
        
    /**
     * Gửi email đơn lẻ đến 1 người nhận
     */
    public static void sendEmail(String to, String subject, String body) throws MessagingException {

    	setup();
    	
        Session session = createSession();
        
        Message message = new MimeMessage(session);
        try {
			message.setFrom(new InternetAddress(EMAIL_FROM, "ABC News"));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        message.setSubject(subject);
	        message.setContent(body, "text/html; charset=UTF-8");
	        
	        Transport.send(message);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Gửi email BCC hàng loạt (nhanh hơn, nhưng có giới hạn recipients)
     * @param recipients Danh sách email (tối đa 50-100 tùy SMTP provider)
     * @throws UnsupportedEncodingException 
     */
    public static void sendBccEmail(List<String> recipients, String subject, String body) throws MessagingException, UnsupportedEncodingException {
        
    	setup();
    	
    	if (recipients == null || recipients.isEmpty()) {
            throw new IllegalArgumentException("Danh sách recipients không được rỗng");
        }
        
        Session session = createSession();
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_FROM, "ABC News"));
        
        // TO: gửi đến chính mình (hoặc một email dummy)
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_FROM));
        
        // BCC: tất cả subscribers
        InternetAddress[] bccAddresses = recipients.stream()
            .map(email -> {
                try {
                    return new InternetAddress(email);
                } catch (AddressException e) {
                    System.err.println("Invalid email: " + email);
                    return null;
                }
            })
            .filter(address -> address != null)
            .toArray(InternetAddress[]::new);
        
        message.setRecipients(Message.RecipientType.BCC, bccAddresses);
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=UTF-8");
        
        Transport.send(message);
    }
    
    /**
     * Tạo Mail Session với cấu hình SMTP
     */
    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });
    }
    
    /**
     * Test method
     */
    public static void main(String[] args) {
        try {
            // Test gửi email đơn
            sendEmail("test@example.com", "Test Subject", "<h1>Hello World</h1>");
            System.out.println("✅ Email sent successfully");
        } catch (Exception e) {
            System.err.println("❌ Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}