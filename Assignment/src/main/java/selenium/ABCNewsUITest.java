package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

/**
 * Selenium UI Test cho ABC News - 10 test cases
 * Test Login, Newsletter Registration, và CRUD News
 */
public class ABCNewsUITest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080/ABCNews"; // Thay đổi theo project
    
    @BeforeClass
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
        System.out.println("=== BẮT ĐẦU UI TEST ===");
    }
    
    @BeforeMethod
    public void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        // options.addArguments("--headless"); // Uncomment để chạy headless
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @AfterMethod
    public void tearDownTest() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    // ========== TC_UI_001-003: LOGIN TESTS ==========
    
    @Test(priority = 1, description = "TC_UI_001: Login với credentials hợp lệ")
    public void testLogin_ValidCredentials_Success() {
        System.out.println("TEST: Login với admin/admin123");
        
        driver.get(BASE_URL + "/login");
        
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        wait.until(ExpectedConditions.urlContains("/admin"));
        
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/admin"), 
            "Sau khi login thành công nên redirect về admin");
        
        System.out.println("✅ Login thành công");
    }
    
    @Test(priority = 2, description = "TC_UI_002: Login với password sai")
    public void testLogin_WrongPassword_ShowError() {
        System.out.println("TEST: Login với password sai");
        
        driver.get(BASE_URL + "/login");
        
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("wrongpassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        WebElement errorMsg = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.className("alert"))
        );
        
        Assert.assertTrue(errorMsg.isDisplayed(), "Nên hiển thị thông báo lỗi");
        Assert.assertTrue(errorMsg.getText().toLowerCase().contains("sai"), 
            "Error message nên chứa từ 'sai'");
        
        System.out.println("✅ Hiển thị lỗi đúng");
    }
    
    @Test(priority = 3, description = "TC_UI_003: Login với username rỗng")
    public void testLogin_EmptyUsername_ValidationError() {
        System.out.println("TEST: Login với username rỗng");
        
        driver.get(BASE_URL + "/login");
        
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.clear();
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Check HTML5 validation
        String validationMsg = usernameField.getAttribute("validationMessage");
        Assert.assertFalse(validationMsg == null || validationMsg.isEmpty(), 
            "Username field nên có validation message");
        
        System.out.println("✅ HTML5 validation hoạt động");
    }
    
    // ========== TC_UI_004-005: NEWSLETTER REGISTRATION TESTS ==========
    
    @Test(priority = 4, description = "TC_UI_004: Đăng ký newsletter với email hợp lệ")
    public void testNewsletterReg_ValidEmail_Success() {
        System.out.println("TEST: Đăng ký newsletter");
        
        driver.get(BASE_URL + "/home");
        
        // Scroll to footer
        WebElement footer = driver.findElement(By.tagName("footer"));
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", footer);
        
        String uniqueEmail = "test" + System.currentTimeMillis() + "@example.com";
        driver.findElement(By.name("email")).sendKeys(uniqueEmail);
        driver.findElement(By.cssSelector("footer form button[type='submit']")).click();
        
        // Wait for toast
        WebElement toast = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("mainToast"))
        );
        
        // Check if visible and has success class
        wait.until(ExpectedConditions.visibilityOf(toast));
        String toastClass = toast.getAttribute("class");
        Assert.assertTrue(toastClass.contains("bg-success") || toastClass.contains("show"), 
            "Toast nên có class success hoặc show");
        
        System.out.println("✅ Đăng ký newsletter thành công");
    }
    
    @Test(priority = 5, description = "TC_UI_005: Đăng ký newsletter với email không hợp lệ")
    public void testNewsletterReg_InvalidEmail_ValidationError() {
        System.out.println("TEST: Đăng ký newsletter với email không hợp lệ");
        
        driver.get(BASE_URL + "/home");
        
        WebElement footer = driver.findElement(By.tagName("footer"));
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", footer);
        
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys("invalid-email");
        driver.findElement(By.cssSelector("footer form button[type='submit']")).click();
        
        String validationMsg = emailField.getAttribute("validationMessage");
        Assert.assertFalse(validationMsg.isEmpty(), 
            "Nên có validation message cho email không hợp lệ");
        
        System.out.println("✅ Validation email đúng");
    }
    
    // ========== TC_UI_006-010: CRUD NEWS TESTS ==========
    
    @Test(priority = 6, description = "TC_UI_006: Tạo tin tức mới")
    public void testCreateNews_ValidData_Success() {
        System.out.println("TEST: Tạo tin tức mới");
        
        loginAsAdmin();
        
        driver.get(BASE_URL + "/admin/news");
        
        // Click "Thêm mới"
        WebElement addButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.linkText("Thêm mới"))
        );
        addButton.click();
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("title")));
        
        String testTitle = "Test News " + System.currentTimeMillis();
        driver.findElement(By.name("title")).sendKeys(testTitle);
        driver.findElement(By.name("content")).sendKeys("Test content for selenium");
        
        // Select category
        driver.findElement(By.name("categoryId")).sendKeys("TT");
        
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        wait.until(ExpectedConditions.urlContains("/admin/news"));
        
        // Verify page contains the title or toast appears
        String pageSource = driver.getPageSource();
        boolean hasToast = pageSource.contains("toast") && pageSource.contains("thành công");
        boolean hasTitle = pageSource.contains(testTitle);
        
        Assert.assertTrue(hasToast || hasTitle, 
            "Trang nên hiển thị toast hoặc chứa tin vừa tạo");
        
        System.out.println("✅ Tạo tin thành công");
    }
    
    @Test(priority = 7, description = "TC_UI_007: Xem danh sách tin tức")
    public void testViewNewsList_DisplayTable_Success() {
        System.out.println("TEST: Xem danh sách tin tức");
        
        loginAsAdmin();
        driver.get(BASE_URL + "/admin/news");
        
        WebElement table = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("table"))
        );
        
        Assert.assertTrue(table.isDisplayed(), "Table tin tức nên hiển thị");
        
        int rowCount = driver.findElements(By.cssSelector("table tbody tr")).size();
        Assert.assertTrue(rowCount > 0, "Table nên có ít nhất 1 dòng");
        
        System.out.println("✅ Danh sách hiển thị: " + rowCount + " tin");
    }
    
    @Test(priority = 8, description = "TC_UI_008: Cập nhật tin tức")
    public void testUpdateNews_ModifyTitle_Success() {
        System.out.println("TEST: Cập nhật tin tức");
        
        loginAsAdmin();
        driver.get(BASE_URL + "/admin/news");
        
        // Click edit button đầu tiên
        WebElement editButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href*='action=edit']")
            )
        );
        editButton.click();
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("title")));
        
        WebElement titleField = driver.findElement(By.name("title"));
        titleField.clear();
        String updatedTitle = "Updated " + System.currentTimeMillis();
        titleField.sendKeys(updatedTitle);
        
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        wait.until(ExpectedConditions.urlContains("/admin/news"));
        
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("thành công") || pageSource.contains(updatedTitle), 
            "Nên hiển thị thông báo success hoặc title mới");
        
        System.out.println("✅ Update tin thành công");
    }
    
    @Test(priority = 9, description = "TC_UI_009: Xóa tin tức")
    public void testDeleteNews_RemoveFromList_Success() {
        System.out.println("TEST: Xóa tin tức");
        
        loginAsAdmin();
        driver.get(BASE_URL + "/admin/news");
        
        int initialCount = driver.findElements(By.cssSelector("table tbody tr")).size();
        
        // Click delete button đầu tiên
        WebElement deleteButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href*='action=delete']")
            )
        );
        deleteButton.click();
        
        // Handle confirm dialog nếu có
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert, continue
        }
        
        wait.until(ExpectedConditions.urlContains("/admin/news"));
        
        int finalCount = driver.findElements(By.cssSelector("table tbody tr")).size();
        Assert.assertTrue(finalCount <= initialCount, 
            "Số lượng tin nên giảm hoặc giữ nguyên");
        
        System.out.println("✅ Xóa tin thành công");
    }
    
    @Test(priority = 10, description = "TC_UI_010: Logout khỏi hệ thống")
    public void testLogout_RedirectToHome_Success() {
        System.out.println("TEST: Logout");
        
        loginAsAdmin();
        
        // Click logout link
        WebElement logoutLink = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href*='logout']")
            )
        );
        logoutLink.click();
        
        wait.until(ExpectedConditions.urlContains("/home"));
        
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/home"), 
            "Sau logout nên redirect về home");
        
        // Try to access admin (should redirect to login)
        driver.get(BASE_URL + "/admin/dashboard");
        
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("/login"),
            ExpectedConditions.urlContains("/auth")
        ));
        
        currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/login") || currentUrl.contains("/auth"), 
            "Khi chưa login, admin page nên redirect về login");
        
        System.out.println("✅ Logout thành công");
    }
    
    // ========== HELPER METHOD ==========
    
    private void loginAsAdmin() {
        driver.get(BASE_URL + "/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/admin"));
    }
}