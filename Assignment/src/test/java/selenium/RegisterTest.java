package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class RegisterTest {
    private WebDriver driver;
    private static final String BASE_URL = "http://localhost:8080/Assignment";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @Test(description = "Đăng ký tài khoản mới thành công")
    public void testRegisterSuccess() throws InterruptedException {
        driver.get(BASE_URL + "/register");
        String randomId = "user" + System.currentTimeMillis();
        String email = "test" + System.currentTimeMillis() + "@gmail.com";
        driver.findElement(By.name("id")).sendKeys(randomId);
        driver.findElement(By.name("fullname")).sendKeys("Tester Auto");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("12345678");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(3000);
        String pageSource = driver.getPageSource();
        boolean hasSuccess = pageSource.contains("Đăng ký thành công") || pageSource.contains("vui lòng đăng nhập");
        Assert.assertTrue(hasSuccess,"Phải hiển thị thông báo đăng ký thành công! Page có chứa: " + pageSource.substring(Math.max(0, pageSource.indexOf("Đăng ký") - 100), Math.min(pageSource.length(), pageSource.indexOf("Đăng ký") + 200)));
    }

    @Test(description = "Không cho đăng ký khi ID đã tồn tại")
    public void testRegisterDuplicateId() throws InterruptedException {
        driver.get(BASE_URL + "/register");

        driver.findElement(By.name("id")).sendKeys("admin"); 
        driver.findElement(By.name("fullname")).sendKeys("Test Trùng ID");
        driver.findElement(By.name("email")).sendKeys("unique" + System.currentTimeMillis() + "@test.com");
        driver.findElement(By.name("password")).sendKeys("12345678");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(3000);

        String pageSource = driver.getPageSource();
        boolean hasError = pageSource.contains("thất bại") || pageSource.contains("đã được sử dụng") || pageSource.contains("ID hoặc Email");
        Assert.assertTrue(hasError, "Phải hiển thị lỗi khi trùng ID! Nội dung trang: " + pageSource.substring(0, Math.min(500, pageSource.length())));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}