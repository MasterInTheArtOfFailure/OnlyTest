package org.example;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;


public class OnlyWebFormTests extends TestCase {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://only.digital/projects#brief");
    }

    @Test
    public void checkValidPhone() {
        WebElement phone = driver.findElement(By.name("phone"));
        phone.clear();
        phone.click();
        phone.sendKeys("8005553535");
        String phoneText = phone.getAttribute("value");
        assertEquals("(800)-555-35-35", phoneText);
    }

    @Test
    public void checkPhoneMask() {
        WebElement phone = driver.findElement(By.name("phone"));
        phone.clear();
        phone.click();
        phone.sendKeys("a%#$()");
        String phoneText = phone.getAttribute("value");
        assertEquals("(___)-___-__-__", phoneText);
    }

    @Test
    public void checkEmailMask() {
        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys("test");

        String emailText = email.getAttribute("value");
        assertEquals("test@ .", emailText);

    }

    @Test
    public void testFullProjectForm() {
        WebElement name = driver.findElement(By.name("name"));
        WebElement email = driver.findElement(By.name("email"));
        WebElement phone = driver.findElement(By.name("phone"));
        WebElement company = driver.findElement(By.name("company"));

        name.clear();
        email.clear();
        phone.clear();
        company.clear();
        
        name.sendKeys("Test Name");
        email.sendKeys("test@example.com");
        phone.click();
        phone.sendKeys("8005553535");
        company.sendKeys("Test company");

        WebElement checkbox = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/" +
                "div/div[2]/form/div[2]/div[1]/label[1]"));
        checkbox.click();

        WebElement textArea = driver.findElement(By.tagName("textarea"));
        textArea.click();
        textArea.sendKeys("text area text");

        WebElement radiobuttonBudget = driver.findElement(By.xpath("//span[contains(text(),'Менее 2 млн')]"));
        radiobuttonBudget.click();

        WebElement radiobuttonSource = driver.findElement(By.xpath("//span[contains(text(),'Давно знаю')]"));
        radiobuttonSource.click();

        WebElement captchaCheckbox = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[2]/" +
                "div[1]/div[2]/form[1]/div[5]/div[1]/div[1]"));
        captchaCheckbox.click();

        WebElement submitButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/" +
                "div[2]/div[1]/div[2]/form[1]/div[6]/button[1]/span[1]"));
        submitButton.click();

        WebDriverWait waitForWarning = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForWarning.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[contains(text(),'Заявка успешно отправлена')]")));


        WebElement successMessage = driver.findElement(By.xpath("//span[contains(text(),'Заявка успешно отправлена')]"));
        assertTrue(successMessage.isDisplayed());

        driver.quit();
    }
}

