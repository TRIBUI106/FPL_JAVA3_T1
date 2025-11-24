package poly.com.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet("/mail")
public class MailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	req.getRequestDispatcher("/views/mail.jsp").forward(req, resp);
    }
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Cấu hình SMTP
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.ssl.protocols", "TLSv1.2");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", 587);
            p.put("mail.debug", "true");

            // Session mail
            Session s = Session.getInstance(p, new jakarta.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        "leanhtu222353@gmail.com",
                        "aknnhtwmatvnhrvi"
                    );
                }
            });

            // Message
            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress("leanhtu222353@gmail.com"));

            msg.setRecipients(Message.RecipientType.TO, 
                InternetAddress.parse(request.getParameter("to")));

            msg.setSubject(request.getParameter("subject"));

            String content = request.getParameter("body");

            // Nội dung text
            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content, "text/html; charset=utf-8");

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(contentPart);

            // Kiểm tra file đính kèm có hay không
            Part filePart = request.getPart("photo_file");

            if (filePart != null && filePart.getSize() > 0 && 
                    filePart.getSubmittedFileName() != null && 
                    !filePart.getSubmittedFileName().isEmpty()) {

                // Tạo folder /files
                File dir = new File(request.getServletContext().getRealPath("/files"));
                if (!dir.exists()) {
                    dir.mkdir();
                }

                // Lưu file
                File savedFile = new File(dir, filePart.getSubmittedFileName());
                filePart.write(savedFile.getAbsolutePath());

                // File đính kèm
                MimeBodyPart fileBodyPart = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(savedFile);
                fileBodyPart.setDataHandler(new DataHandler(fds));
                fileBodyPart.setFileName(savedFile.getName());

                // Gắn vào email
                multipart.addBodyPart(fileBodyPart);
            }

            msg.setContent(multipart);

            // Gửi mail
            Transport.send(msg);

            request.setAttribute("message", "Send email success!");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Send email fail!");
        }

        request.getRequestDispatcher("/views/mail.jsp").forward(request, response);
    }
}
