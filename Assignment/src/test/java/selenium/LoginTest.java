package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080/Assignment";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1, description = "Đăng nhập thành công hoặc thử lại với tài khoản khác nếu thất bại")
    public void testLoginWithFallback() {
        driver.get(BASE_URL + "/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        try {
            wait.until(ExpectedConditions.urlContains("/admin/home"));
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("/admin/home"),
                    "Login admin thành công nhưng URL không đúng!");
            return; 
        } catch (TimeoutException e) {
            System.out.println("Login admin thất bại → thử lại với tài khoản a/a");
        }
        driver.get(BASE_URL + "/login");
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("username")).sendKeys("a");
        driver.findElement(By.name("password")).sendKeys("a");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/admin/home"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/admin/home"),
                "Login bằng tài khoản a/a phải thành công! URL hiện tại: " + currentUrl);
    }


    @Test(priority = 2, description = "Sai tài khoản + đúng mật khẩu → Hiển thị lỗi")
    public void WrongUsername() {
        driver.get(BASE_URL + "/login");
        driver.findElement(By.name("username")).sendKeys("saihetroiloi");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        By errorAlert = By.cssSelector(".alert.alert-danger");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorAlert));
        String errorText = driver.findElement(errorAlert).getText();
        Assert.assertTrue(errorText.contains("Sai ID hoặc mật khẩu"),"Phải hiện lỗi khi sai tài khoản! Thực tế thấy: " + errorText);
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Phải ở lại trang login");
    }

    @Test(priority = 3, description = "Đúng tài khoản + sai mật khẩu → Hiển thị lỗi")
    public void WrongPassword() {
        driver.get(BASE_URL + "/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("matkhausaibangtroi");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        By errorAlert = By.cssSelector(".alert.alert-danger");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorAlert));
        String errorText = driver.findElement(errorAlert).getText();
        Assert.assertTrue(errorText.contains("Sai ID hoặc mật khẩu"),"Phải hiện lỗi khi sai mật khẩu! Thực tế thấy: " + errorText);
    }

    @Test(priority = 4, description = "Để trống tài khoản + nhập mật khẩu → Không cho submit (HTML5 required)")
    public void EmptyUsername() throws InterruptedException {
        driver.get(BASE_URL + "/login");
        driver.findElement(By.name("username")).clear(); 
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1000); 
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),"Không được submit khi để trống tài khoản!");
        boolean hasServerError = !driver.findElements(By.cssSelector(".alert.alert-danger")).isEmpty();
        Assert.assertFalse(hasServerError, "Không được có thông báo lỗi từ server khi form chưa submit!");
    }

    @Test(priority = 5, description = "Nhập tài khoản + để trống mật khẩu → Không cho submit")
    public void EmptyPassword() throws InterruptedException {
        driver.get(BASE_URL + "/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).clear(); 
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),"Không được submit khi để trống mật khẩu!");
    }

    @Test(priority = 6, description = "Để trống cả tài khoản và mật khẩu → Không cho submit")
    public void EmptyBoth() throws InterruptedException {
        driver.get(BASE_URL + "/login");
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),"Không được submit khi để trống cả 2 trường!");
        String usernameValidationMsg = driver.findElement(By.name("username")).getAttribute("validationMessage");
        Assert.assertTrue(usernameValidationMsg.toLowerCase().contains("vui lòng") || usernameValidationMsg.contains("required") || usernameValidationMsg.contains("điền"),"Trình duyệt phải hiện thông báo required cho username");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}