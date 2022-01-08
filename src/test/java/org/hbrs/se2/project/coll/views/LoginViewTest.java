package org.hbrs.se2.project.coll.views;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("Disabled because Jenkins is not able to use Selenium!")
@ExtendWith(SpringExtension.class)
class LoginViewTest {


    WebDriver driver;


    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(classes = { ChromeDriver.class , EdgeDriver.class , OperaDriver.class , FirefoxDriver.class , SafariDriver.class})
    void test(Class<? extends WebDriver> webDriverClass)   {

        driver = WebDriverManager.getInstance(webDriverClass).create();
        driver.manage().window().maximize();
        driver.get("http://sepp-test.inf.h-brs.de:8080/Team_ALT-F4/login");
        WebElement username=driver.findElement(By.id("vaadinLoginUsername"));
        WebElement password=driver.findElement(By.id("vaadinLoginPassword"));
        username.sendKeys("olaf@gmbh.de");
        password.sendKeys("passwd");
        WebElement element  = driver.findElement(By.xpath("/html/body/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
        Actions builder = new Actions(driver);
        builder.moveToElement( element ).click( element );
        builder.perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-vertical-layout/img"));
        assertEquals("http://sepp-test.inf.h-brs.de:8080/Team_ALT-F4/" , driver.getCurrentUrl());

    }

    @Disabled
    @ParameterizedTest
    @ValueSource(classes = { ChromeDriver.class , EdgeDriver.class , OperaDriver.class , FirefoxDriver.class , SafariDriver.class})
    void testLocalHost(Class<? extends WebDriver> webDriverClass)   {

        driver = WebDriverManager.getInstance(webDriverClass).create();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/login/");
        WebElement username=driver.findElement(By.id("vaadinLoginUsername"));
        WebElement password=driver.findElement(By.id("vaadinLoginPassword"));
        username.sendKeys("olaf@gmbh.de");
        password.sendKeys("passwd");
        WebElement element  = driver.findElement(By.xpath("/html/body/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
        Actions builder = new Actions(driver);
        builder.moveToElement( element ).click( element );
        builder.perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-vertical-layout/img"));
        assertEquals("http://localhost:8080/" , driver.getCurrentUrl());

    }


}