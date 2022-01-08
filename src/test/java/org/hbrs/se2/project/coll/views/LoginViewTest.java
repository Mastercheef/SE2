package org.hbrs.se2.project.coll.views;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("Disabled because Jenkins is not able to use Selenium!")
@ExtendWith(SpringExtension.class)
class LoginViewTest {


    @Disabled
    @Test
    public synchronized  void login()  {

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://sepp-test.inf.h-brs.de:8080/Team_ALT-F4/login");

        WebElement username=driver.findElement(By.id("vaadinLoginUsername"));
        WebElement password=driver.findElement(By.id("vaadinLoginPassword"));
        username.sendKeys("www@gmx.de");
        password.sendKeys("www123");
        WebElement element  = driver.findElement(By.xpath("/html/body/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));

        Actions builder = new Actions(driver);
        builder.moveToElement( element ).click( element );
        builder.perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-vertical-layout/img"));
        assertEquals("http://sepp-test.inf.h-brs.de:8080/Team_ALT-F4/" , driver.getCurrentUrl());

    }

    @Disabled
    @Test
    public   void loginFail()  {

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://sepp-test.inf.h-brs.de:8080/Team_ALT-F4/login");

        WebElement username=driver.findElement(By.id("vaadinLoginUsername"));
        WebElement password=driver.findElement(By.id("vaadinLoginPassword"));
        username.sendKeys("www@gmx.net");
        password.sendKeys("www123");
        WebElement element  = driver.findElement(By.xpath("/html/body/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));

        Actions builder = new Actions(driver);
        builder.moveToElement( element ).click( element );
        builder.perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"button\"]"));
        assertEquals("http://sepp-test.inf.h-brs.de:8080/Team_ALT-F4/login" , driver.getCurrentUrl());

    }

}