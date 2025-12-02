package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CategoryAdminTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String BASE_URL = "http://localhost:8080/Assignment";
    private final String ADMIN_USER = "a";
    private final String ADMIN_PASS = "a";

    @BeforeClass
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        login();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void login() {
        driver.get(BASE_URL + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")))
                .sendKeys(ADMIN_USER);
        driver.findElement(By.name("password")).sendKeys(ADMIN_PASS);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/admin"));
    }

    @Test(priority = 1)
    public void testAddNewCategory() {
        driver.get(BASE_URL + "/admin/category");
        clickAddButtonAndWaitModal();

        String categoryName = "Khoa Học Công Nghệ";
        WebElement nameInput = driver.findElement(By.id("categoryName"));
        nameInput.clear();
        nameInput.sendKeys(categoryName);

        WebElement displayId = driver.findElement(By.id("displayId"));
        waitForValueNotEmpty(displayId);

        String displayValue = displayId.getAttribute("value");
        String generatedId = displayValue.split(" ")[0].trim();

        submitModal();

        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));
        Assert.assertTrue(alert.getText().contains("Thêm mới loại tin"));

        String xpath = String.format("//td[strong[text()='%s']]/following-sibling::td[text()='%s']", generatedId, categoryName);
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        Assert.assertNotNull(row);
    }

    @Test(priority = 2)
    public void testEditCategory() {
        driver.get(BASE_URL + "/admin/category");
        String tempName = "Test Edit " + System.currentTimeMillis();
        String expectedId = "TE"; 
        addCategoryViaUI(tempName, expectedId);

        String editXpath = String.format("//td[text()='%s']/following-sibling::td//button[contains(.,'Sửa')]", tempName);
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(editXpath)));
        editBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryModal")));
        WebElement nameInput = driver.findElement(By.id("categoryName"));
        String newName = tempName + " - Đã Sửa";
        nameInput.clear();
        nameInput.sendKeys(newName);

        WebElement displayId = driver.findElement(By.id("displayId"));
        waitForValueNotEmpty(displayId);
        String actualId = displayId.getAttribute("value").split(" ")[0].trim();
        Assert.assertTrue(actualId.startsWith(expectedId), "ID phải bắt đầu bằng " + expectedId);

        submitModal();

        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));
        Assert.assertTrue(alert.getText().contains("Cập nhật"));

        Assert.assertNotNull(driver.findElement(By.xpath("//td[text()='" + newName + "']")));
    }

    @Test(priority = 3)
    public void testDeleteCategory() {
        driver.get(BASE_URL + "/admin/category");

        String tempName = "Tin Tạm Để Xóa " + System.currentTimeMillis();

        // === THÊM LOẠI TIN ===
        clickAddButtonAndWaitModal();
        WebElement nameInput = driver.findElement(By.id("categoryName"));
        nameInput.clear();
        nameInput.sendKeys(tempName);

        WebElement displayId = driver.findElement(By.id("displayId"));
        waitForValueNotEmpty(displayId);

        submitModal();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));
        String deleteXpath = String.format("//td[text()='%s']/following-sibling::td//a[contains(@href, 'action=delete')]", tempName);
        WebElement deleteLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(deleteXpath)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", deleteLink);
        ((JavascriptExecutor) driver).executeScript("window.confirm = function(){ return true; };");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteLink);

        new WebDriverWait(driver, Duration.ofSeconds(2))
            .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='" + tempName + "']")));

        Assert.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.xpath("//td[text()='" + tempName + "']"));
        });
    }

//    @Test(priority = 4)
//    public void testClientSideIdGeneration() {
//        driver.get(BASE_URL + "/admin/category");
//        clickAddButtonAndWaitModal();
//
//        WebElement nameInput = driver.findElement(By.id("categoryName"));
//        WebElement displayId = driver.findElement(By.id("displayId"));
//
//        // Test 1
//        nameInput.sendKeys("Giải Trí Việt Nam");
//        wait.until(ExpectedConditions.attributeContains(displayId, "value", "GTVN"));
//
//        // Test 2
//        nameInput.clear();
//        nameInput.sendKeys("Chính trị");
//        wait.until(ExpectedConditions.attributeContains(displayId, "value", "C"));
//
//        // Test 3: Rỗng → trigger bằng cách focus + blur
//        nameInput.clear();
//        nameInput.click(); // focus
//        driver.findElement(By.cssSelector("body")).click(); // blur
//        wait.until(ExpectedConditions.attributeContains(displayId, "value", "Sẽ tự động tạo khi lưu"));
//    }
    
    @Test(priority = 4)
    public void testClientSideIdGeneration() {
        driver.get(BASE_URL + "/admin/category");
        clickAddButtonAndWaitModal();

        WebElement nameInput = driver.findElement(By.id("categoryName"));
        WebElement displayId = driver.findElement(By.id("displayId"));

        nameInput.sendKeys("Giải Trí Việt Nam");
        wait.until(ExpectedConditions.attributeContains(displayId, "value", "GTVN"));

        nameInput.clear();
        nameInput.sendKeys("Chính trị");
        wait.until(ExpectedConditions.attributeContains(displayId, "value", "C"));
        nameInput.clear();
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", 
            nameInput
        );
        wait.until(ExpectedConditions.attributeContains(displayId, "value", "Sẽ tự động tạo khi lưu"));
    }

    private void clickAddButtonAndWaitModal() {
        driver.findElement(By.cssSelector("button[data-bs-target='#categoryModal']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryModal")));
    }

    private void submitModal() {
        driver.findElement(By.cssSelector("#categoryModal button[type='submit']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("categoryModal")));
    }

    private void waitForValueNotEmpty(WebElement element) {
        wait.until(d -> {
            String val = element.getAttribute("value");
            return val != null && !val.trim().isEmpty();
        });
    }

    private void addCategoryViaUI(String name, String expectedPrefix) {
        clickAddButtonAndWaitModal();
        WebElement nameInput = driver.findElement(By.id("categoryName"));
        nameInput.clear();
        nameInput.sendKeys(name);
        WebElement displayId = driver.findElement(By.id("displayId"));
        waitForValueNotEmpty(displayId);
        String displayValue = displayId.getAttribute("value");
        String generatedId = displayValue.split(" ")[0].trim();
        Assert.assertTrue(generatedId.startsWith(expectedPrefix), "ID phải bắt đầu bằng " + expectedPrefix);

        submitModal();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));
    }
}