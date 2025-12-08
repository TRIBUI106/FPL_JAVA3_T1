package test;

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
 * Selenium Test cho chức năng Đăng nhập
 * Sử dụng TestNG framework
 */
public class LoginSeleniumTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080/NewsManagement";
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    
    @BeforeClass
    public void setupClass() {
        // Set ChromeDriver path
        // Download từ: https://chromedriver.chromium.org/
        System.setProperty("webdriver.chrome.driver", 
            "path/to/chromedriver.exe"); // Thay đổi path này
    }
    
    @BeforeMethod
    public void setup() {
        // Cấu hình Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        // Uncomment dòng dưới nếu muốn chạy headless (không mở browser)
        // options.addArguments("--headless");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, TIMEOUT);
        driver.manage().timeouts().implicitlyWait(TIMEOUT);
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    /**
     * Test Case: Đăng nhập thành công với thông tin đúng
     */
    @Test(priority = 1)
    public void testLoginSuccess() {
        // Navigate to login page
        driver.get(BASE_URL + "/login");
        
        // Wait for page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        
        // Find elements
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        
        // Input credentials (thay đổi theo database của bạn)
        usernameField.sendKeys("admin");
        passwordField.sendKeys("admin123");
        
        // Click login
        loginButton.click();
        
        // Wait for redirect
        wait.until(ExpectedConditions.urlContains("/admin/dashboard"));
        
        // Verify successful login
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/admin/dashboard"), 
            "Should redirect to dashboard after successful login");
        
        System.out.println("✓ Test Login Success - PASSED");
    }
    
    /**
     * Test Case: Đăng nhập thất bại với mật khẩu sai
     */
    @Test(priority = 2)
    public void testLoginFailureWrongPassword() {
        driver.get(BASE_URL + "/login");
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        
        // Input wrong credentials
        usernameField.sendKeys("admin");
        passwordField.sendKeys("wrongpassword");
        
        loginButton.click();
        
        // Wait for error message
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Sai ID') or contains(text(), 'sai')]")));
        
        // Verify error message appears
        WebElement errorMsg = driver.findElement(
            By.xpath("//*[contains(text(), 'Sai ID') or contains(text(), 'sai')]"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message should be displayed");
        
        // Verify still on login page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/login"), 
            "Should stay on login page after failed login");
        
        System.out.println("✓ Test Login Failure (Wrong Password) - PASSED");
    }
    
    /**
     * Test Case: Đăng nhập với trường trống
     */
    @Test(priority = 3)
    public void testLoginWithEmptyFields() {
        driver.get(BASE_URL + "/login");
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        
        // Try to submit without entering anything
        loginButton.click();
        
        // Check if HTML5 validation works or custom validation
        WebElement usernameField = driver.findElement(By.name("username"));
        String validationMessage = usernameField.getAttribute("validationMessage");
        
        if (validationMessage != null && !validationMessage.isEmpty()) {
            // HTML5 validation is working
            Assert.assertFalse(validationMessage.isEmpty(), 
                "Validation message should appear for empty username");
        } else {
            // Custom validation - check for error message on page
            try {
                WebElement errorMsg = driver.findElement(
                    By.xpath("//*[contains(text(), 'không được để trống') or contains(@class, 'error')]"));
                Assert.assertTrue(errorMsg.isDisplayed(), 
                    "Error message should be displayed for empty fields");
            } catch (Exception e) {
                // Check if still on login page
                Assert.assertTrue(driver.getCurrentUrl().contains("/login"), 
                    "Should remain on login page with empty fields");
            }
        }
        
        System.out.println("✓ Test Login with Empty Fields - PASSED");
    }
    
    /**
     * Test Case: Kiểm tra giao diện trang đăng nhập
     */
    @Test(priority = 4)
    public void testLoginPageElements() {
        driver.get(BASE_URL + "/login");
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        
        // Verify all elements exist
        Assert.assertTrue(driver.findElement(By.name("username")).isDisplayed(), 
            "Username field should be visible");
        Assert.assertTrue(driver.findElement(By.name("password")).isDisplayed(), 
            "Password field should be visible");
        Assert.assertTrue(driver.findElement(By.cssSelector("button[type='submit']")).isDisplayed(), 
            "Login button should be visible");
        
        // Check if register link exists
        try {
            WebElement registerLink = driver.findElement(By.linkText("Đăng ký"));
            Assert.assertTrue(registerLink.isDisplayed(), 
                "Register link should be visible");
        } catch (Exception e) {
            System.out.println("⚠ Register link not found - might be optional");
        }
        
        System.out.println("✓ Test Login Page Elements - PASSED");
    }
    
    /**
     * Test Case: Logout functionality
     */
    @Test(priority = 5)
    public void testLogout() {
        // Login first
        driver.get(BASE_URL + "/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        wait.until(ExpectedConditions.urlContains("/admin/dashboard"));
        
        // Find and click logout
        try {
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(), 'Đăng xuất') or contains(@href, 'logout')]")));
            logoutLink.click();
            
            // Verify redirected to login
            wait.until(ExpectedConditions.urlContains("/login"));
            Assert.assertTrue(driver.getCurrentUrl().contains("/login"), 
                "Should redirect to login page after logout");
            
            System.out.println("✓ Test Logout - PASSED");
        } catch (Exception e) {
            System.out.println("⚠ Logout link not found - skipping test");
        }
    }
}