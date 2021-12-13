package org.hbrs.se2.project.coll.views;

import com.vaadin.flow.component.button.Button;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;

class LoginViewTest {


    @Test
    public void login() throws InterruptedException
    {
        // chromedriver
        System.setProperty("webdriver.chrome.driver","src/main/resources/selenium/chromedriver/windows/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();


        //vaadinlogInusername
        driver.get("sepp-test.inf.h-brs.de:8080/Team_ALT-F4/login");
        Thread.sleep(1000);
        WebElement username=driver.findElement(By.id("vaadinLoginUsername"));
        WebElement password=driver.findElement(By.id("vaadinLoginPassword"));

        username.sendKeys("judithmeier@company.com");
        password.sendKeys("abc123");
        driver.findElement(By.xpath("/html/body/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button")).click();
    }

    public void loginFirefox() {
        WebDriver driver = new FirefoxDriver();
    }
}