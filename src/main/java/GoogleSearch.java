import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoogleSearch {
    @Test
    public void googleSearch() throws InterruptedException {

        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Nicu\\Desktop\\geckodriver.exe");

        WebDriver driver = new FirefoxDriver();
        FirefoxOptions options = new FirefoxOptions();

        String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        options.addArguments("--start-maximized");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);


        driver.get("https://www.google.com");

        WebElement element = driver.findElement(By.name("q"));

        element.sendKeys("Program");
        element.submit();

        System.out.println("Page title " + driver.getTitle());

        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver1) {
                return driver1.getTitle().toLowerCase().startsWith("program");
            }
        });

        List<WebElement> links = driver.findElements(By.xpath("//div[@class='yuRUbf']/a"));

       System.out.println("Page title tab" + driver.getTitle());
        System.out.println("Page url tab" + driver.getCurrentUrl());

        int numberOfLinks = links.size();
        for (int iLinks = 0; iLinks < numberOfLinks; iLinks++) {
            links.get(iLinks).sendKeys(selectLinkOpenInNewTab);
        }

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (int iTabs = 0; iTabs < tabs.size(); iTabs++) {
            driver.switchTo().window(tabs.get(iTabs));
            driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
            System.out.println("Page title  " + iTabs + " " + driver.getTitle());
            System.out.println("Page url "  + iTabs + " " +  driver.getCurrentUrl());
            System.out.println("Number of multiple: on "  +  driver.getTitle() + "   "  + StringUtils.countMatches(driver.getPageSource().toLowerCase(), "program"));
        }
        System.out.println("numberOfLinks " + numberOfLinks);
        try{
            List<WebElement> links51 = driver.findElements(By.xpath( "//div[@class='yuRUbf']/a"));
            for (WebElement link : links51) {
                link.sendKeys(selectLinkOpenInNewTab);
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document document = dbBuilder.newDocument();
            Element linksToSite = document.createElement("links");
            document.appendChild(linksToSite);

            ArrayList<String> tabs1 = new ArrayList<>(driver.getWindowHandles());
            for (int i = 0; i < tabs1.size(); i++) {
                Element linkToSite = document.createElement("link");
                linksToSite.appendChild(linkToSite);
                Element url = document.createElement("url");
                Element pageName = document.createElement("page_name");
                Element wordCount = document.createElement("number_of_occurrence_of_the_word_on_page");
                String tab = tabs1.get(i);
                driver.switchTo().window(tab);
                driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                url.appendChild(document.createTextNode(driver.getCurrentUrl()));
                linkToSite.appendChild(url);
                pageName.appendChild(document.createTextNode(driver.getTitle()));
                linkToSite.appendChild(pageName);
                wordCount.appendChild(document.createTextNode(String.valueOf(StringUtils.countMatches(driver.getPageSource().toLowerCase(), "program"))));
                linkToSite.appendChild(wordCount);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("C:\\Users\\Nicu\\IdeaProjects\\Testing2NicuFilip\\search.xml"));
            transformer.transform(source, result);
        }
        catch(ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
        Thread.sleep(30000);
        driver.quit();

    }
}