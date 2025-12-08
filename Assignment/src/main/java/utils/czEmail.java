package utils;

import dao.NewsLetterDAO;
import utils.czEmailUtils;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service xử lý gửi email bất đồng bộ (async)
 * Không chặn request của user
 */
public class czEmail {
    
    private final NewsLetterDAO newsletterDAO = new NewsLetterDAO();
    
    // Thread pool với 5 worker threads để xử lý email
    private static final ExecutorService emailExecutor = Executors.newFixedThreadPool(5);
    
    /**
     * Gửi email thông báo tin mới (ASYNC - không chặn request)
     * Method này return ngay lập tức, email được gửi ở background
     * 
     * @param newsId ID của tin tức mới
     * @param newsTitle Tiêu đề tin tức
     * @param newsUrl URL đầy đủ của tin tức
     * @param contextPath Context path của ứng dụng
     */
    public void sendNewsNotificationAsync(String newsId, String newsTitle, String newsUrl, String contextPath) {
        // Submit task vào thread pool và return ngay lập tức
        emailExecutor.submit(() -> {
            try {
                System.out.println("🚀 [EmailService] Bắt đầu gửi email cho tin: " + newsTitle);
                long startTime = System.currentTimeMillis();
                
                // Lấy tất cả email đã đăng ký newsletter
                List<String> emails = newsletterDAO.getAllEmails();
                
                if (emails == null || emails.isEmpty()) {
                    System.out.println("⚠️ [EmailService] Không có email nào đăng ký newsletter");
                    return;
                }
                
                System.out.println("📧 [EmailService] Tìm thấy " + emails.size() + " subscribers");
                
                // Tạo nội dung email HTML
                String subject = "📰 Tin mới từ ABC News: " + newsTitle;
                String body = createEmailBody(newsTitle, newsUrl, contextPath);
                
                // Gửi từng email riêng lẻ (privacy tốt hơn BCC)
                int successCount = sendIndividualEmails(emails, subject, body);
                
                long duration = System.currentTimeMillis() - startTime;
                System.out.println("✅ [EmailService] Hoàn thành gửi " + successCount + "/" + emails.size() 
                    + " emails trong " + duration + "ms");
                
            } catch (Exception e) {
                System.err.println("❌ [EmailService] Lỗi khi gửi email: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        System.out.println("⚡ [EmailService] Task đã được đưa vào queue, không chặn response");
    }
    
    /**
     * Gửi từng email riêng lẻ
     * Ưu điểm: Privacy tốt, mỗi người nhận email riêng
     * @return Số lượng email gửi thành công
     */
    private int sendIndividualEmails(List<String> emails, String subject, String body) {
        int successCount = 0;
        int failCount = 0;
        
        for (String email : emails) {
            try {
                czEmailUtils.sendEmail(email, subject, body);
                successCount++;
                System.out.println("  ✅ Sent to: " + email);
                
                // Delay nhỏ giữa các email để tránh spam filter
                Thread.sleep(100); // 100ms
                
            } catch (Exception e) {
                failCount++;
                System.err.println("  ❌ Failed to send to: " + email + " - " + e.getMessage());
            }
        }
        
        if (failCount > 0) {
            System.out.println("⚠️ [EmailService] Có " + failCount + " email gửi thất bại");
        }
        
        return successCount;
    }
    
    /**
     * Tạo HTML body cho email thông báo tin mới
     */
    private String createEmailBody(String newsTitle, String newsUrl, String contextPath) {
        return "<!DOCTYPE html>" +
               "<html lang='vi'>" +
               "<head>" +
               "  <meta charset='UTF-8'>" +
               "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "</head>" +
               "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;'>" +
               "  <table width='100%' cellpadding='0' cellspacing='0' style='background-color: #f4f4f4; padding: 20px;'>" +
               "    <tr>" +
               "      <td align='center'>" +
               "        <table width='600' cellpadding='0' cellspacing='0' style='background-color: white; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>" +
               "          " +
               "          <!-- Header -->" +
               "          <tr>" +
               "            <td style='background: linear-gradient(90deg, #0a1930, #102542); padding: 30px; text-align: center;'>" +
               "              <h1 style='color: white; margin: 0; font-size: 28px;'>📰 ABC NEWS</h1>" +
               "              <p style='color: #ffffff; opacity: 0.9; margin: 5px 0 0 0;'>Tin tức chuẩn xác - Cập nhật liên tục</p>" +
               "            </td>" +
               "          </tr>" +
               "          " +
               "          <!-- Content -->" +
               "          <tr>" +
               "            <td style='padding: 40px 30px;'>" +
               "              <h2 style='color: #0066cc; margin: 0 0 20px 0; font-size: 20px;'>Tin mới vừa được đăng!</h2>" +
               "              " +
               "              <h3 style='color: #333; margin: 0 0 20px 0; font-size: 22px; line-height: 1.4;'>" + 
               newsTitle + 
               "</h3>" +
               "              " +
               "              <p style='color: #666; line-height: 1.6; margin: 0 0 30px 0;'>" +
               "                Chúng tôi vừa đăng một tin tức mới mà bạn có thể quan tâm. " +
               "                Nhấn vào nút bên dưới để đọc toàn bộ bài viết." +
               "              </p>" +
               "              " +
               "              <!-- CTA Button -->" +
               "              <table width='100%' cellpadding='0' cellspacing='0'>" +
               "                <tr>" +
               "                  <td align='center' style='padding: 20px 0;'>" +
               "                    <a href='" + newsUrl + "' " +
               "                       style='background-color: #0066cc; color: white; padding: 15px 40px; " +
               "                              text-decoration: none; border-radius: 5px; display: inline-block; " +
               "                              font-weight: bold; font-size: 16px;'>" +
               "                      📖 Đọc ngay" +
               "                    </a>" +
               "                  </td>" +
               "                </tr>" +
               "              </table>" +
               "            </td>" +
               "          </tr>" +
               "          " +
               "          <!-- Footer -->" +
               "          <tr>" +
               "            <td style='background-color: #f8f9fa; padding: 25px 30px; border-top: 1px solid #e0e0e0;'>" +
               "              <p style='color: #666; font-size: 13px; line-height: 1.6; margin: 0 0 10px 0;'>" +
               "                Bạn nhận được email này vì đã đăng ký nhận thông báo tin tức từ <strong>ABC News</strong>." +
               "              </p>" +
               "              <p style='color: #999; font-size: 12px; margin: 0;'>" +
               "                © 2025 ABC News. All rights reserved. | " +
               "                <a href='" + contextPath + "/home' style='color: #0066cc; text-decoration: none;'>Trang chủ</a> | " +
               "                <a href='#' style='color: #0066cc; text-decoration: none;'>Hủy đăng ký</a>" +
               "              </p>" +
               "            </td>" +
               "          </tr>" +
               "        </table>" +
               "      </td>" +
               "    </tr>" +
               "  </table>" +
               "</body>" +
               "</html>";
    }
    
    /**
     * Shutdown thread pool khi ứng dụng tắt
     * Gọi method này trong ServletContextListener
     */
    public static void shutdown() {
        emailExecutor.shutdown();
        System.out.println("🛑 [EmailService] Thread pool đã shutdown");
    }
}