package Internet_tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class Lesson16 {

    private static final String URL = "http://admin:admin@the-internet.herokuapp.com/basic_auth";

    public WebDriver driver;
    public WebDriverWait wait;
    public JavascriptExecutor js;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(URL);
        js = (JavascriptExecutor) driver;
    }

    @BeforeMethod
    public void UrlOpen() {
        driver.get(URL);
    }

    @AfterMethod
    public void pauseBetweenTests() throws InterruptedException {
        Thread.sleep(3000); // пауза перед виконанням наступного тесту
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
    }


    @Test
    public void t1() throws InterruptedException {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"content\"]/div/p")));

        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"content\"]/div/p"));

        Assert.assertEquals(elements.get(0).getText(), "Congratulations! You must have the proper credentials.", "Something");

        driver.get("chrome://settings/clearBrowserData");
        //Thread.sleep(1000);
        String emailInput = "return document.querySelector(\"body > settings-ui\").shadowRoot.querySelector(\"#main\").shadowRoot.querySelector(\"settings-basic-page\").shadowRoot.querySelector(\"#basicPage > settings-section:nth-child(10) > settings-privacy-page\").shadowRoot.querySelector(\"settings-clear-browsing-data-dialog\").shadowRoot.querySelector(\"#clearBrowsingDataConfirm\").click()";
        js.executeScript(emailInput);

        driver.get("http://@the-internet.herokuapp.com/basic_auth");

        List<WebElement> elements2 = driver.findElements(By.xpath("//*[@id=\"content\"]/div/p"));

        Assert.assertTrue(elements2.isEmpty());

    }

}
