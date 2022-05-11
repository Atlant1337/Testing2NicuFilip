import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class DataProvider {
    private static final Logger logger = Logger.getLogger(DataProvider.class);
    @Test
    public void test() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Nicu\\Desktop\\geckodriver.exe");

        WebDriver driver = new FirefoxDriver();
        FirefoxOptions options = new FirefoxOptions();
        DOMConfigurator.configure("log4j.xml");
        options.addArguments("--start-maximized");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //wait before each element
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get("https://www.shino.de/parkcalc/");
        logger.info("[Nicu] loading page");
        Assert.assertEquals(driver.getTitle(), "Parking Cost Calculator");

        String parkingLot = "Valet Parking";
        WebElement parkingLot1 = driver.findElement(By.id("ParkingLot"));
        parkingLot1.sendKeys(parkingLot);

        String startdate = "5/10/2022";
        WebElement StartingDate1 = driver.findElement(By.id("StartingDate"));
        StartingDate1.sendKeys(startdate);

        String starttime = "16:05";
        WebElement starttime1 = driver.findElement(By.id("StartingTime"));
        starttime1.sendKeys(starttime);

        WebElement ampm1 = driver.findElement(By.xpath("//input[@name='StartingTimeAMPM']"));
        ampm1.click();
        logger.debug("[Nicu] clicked button");

        String LeavingDate = "5/11/2022";
        WebElement LeavingDate1 = driver.findElement(By.id("LeavingDate"));
        LeavingDate1.sendKeys(LeavingDate);

        String LeavingTime = "17:00";
        WebElement LeavingTime1 = driver.findElement(By.id("LeavingTime"));
        LeavingTime1.sendKeys(LeavingTime);

        WebElement ampm2 = driver.findElement(By.xpath("//input[@name='LeavingTimeAMPM']"));
        ampm2.click();
        logger.debug("[Nicu] clicked button");

        WebElement calculate = driver.findElement(By.xpath("//input[@type='submit']"));
        calculate.click();
        logger.debug("[Nicu] clicked button");

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(
                        driver.findElement(
                                By.xpath("//b[contains(text(),'')]"))));


    }
}