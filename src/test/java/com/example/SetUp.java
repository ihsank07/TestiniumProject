package com.example;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class SetUp {
    public WebDriver driver;
    @Before
        public void beforeExc()
    {
        System.setProperty("webdriver.chrome.driver","files/ChromeDriver/chromedriver.exe");
        driver = new ChromeDriver();
        
        driver.manage().window().maximize();
        
    }

    
    @After
    public void afterExc() throws InterruptedException {
    	Thread.sleep(10000);
    	driver.quit();
    }
}
