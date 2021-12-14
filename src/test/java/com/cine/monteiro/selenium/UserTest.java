package com.cine.monteiro.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserTest {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().fullscreen();
		driver.get("http://localhost:4200");
	}
	
	@After
	public void finish() {
		driver.quit();
	}
	
	@Test
	public void t1_recoverError() {
		
		WebElement btnRecover = driver.findElement(By.className("btn-link-recover"));
		btnRecover.click();
		
		WebElement btnEnviar = driver.findElement(By.id("btn-enviar"));
		WebElement inputEmail = driver.findElement(By.id("input-email"));
		
		inputEmail.sendKeys("belijag308@gruppies.com");		
		btnEnviar.click();
		
	}
	
	public void t2_recoverSuccess() {
		
	}
	
	public void t3_signupError() {
		
	}
	
	public void t4_signupSuccess() {
		
	}
	
	public void t5_signinError() {
		
	}
	
	public void t6_signinSuccess() {
		
	}
	
	private void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	
}
