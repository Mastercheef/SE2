package org.hbrs.se2.project.coll.views;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;


class LoginViewTest {



    public void login()  {

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://sepp-test.inf.h-brs.de:8080/Team_ALT-F4/login");

        WebElement username=driver.findElement(By.id("vaadinLoginUsername"));
        WebElement password=driver.findElement(By.id("vaadinLoginPassword"));
        username.sendKeys("admin2@firma.de");
        password.sendKeys("password");
        WebElement element  = driver.findElement(By.xpath("/html/body/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));

        Actions builder = new Actions(driver);
        builder.moveToElement( element ).click( element );
        builder.perform();


    }

}