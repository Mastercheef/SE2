package org.hbrs.se2.project.coll.views;

import org.hbrs.se2.project.coll.util.Utils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import java.sql.SQLException;


class LoginViewTest {


    @Test
    public void login() throws InterruptedException, SQLException {

        System.setProperty("webdriver.chrome.driver",getLinkToChromeDriver());
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://sepp-test.inf.h-brs.de:8080/Team_ALT-F4/login");

        WebElement username=driver.findElement(By.id("vaadinLoginUsername"));
        WebElement password=driver.findElement(By.id("vaadinLoginPassword"));
        username.sendKeys("judithmeier@company.com");
        password.sendKeys("abc123");
        WebElement element  = driver.findElement(By.xpath("/html/body/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));

        Actions builder = new Actions(driver);
        builder.moveToElement( element ).click( element );
        builder.perform();

    }
    public void loginFirefox() {
        WebDriver driver = new FirefoxDriver();
    }

    private String getLinkToChromeDriver() {
        Utils.OS os = Utils.getOS();
        if(os.equals(Utils.OS.WINDOWS)) {
            return "src/main/resources/selenium/chromedriver/windows/chromedriver.exe";
        } else {
            return ("src/main/resources/selenium/chromedriver/" +  os.toString()   +"/chromedriver");
        }
    }
}