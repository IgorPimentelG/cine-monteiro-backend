package com.cine.monteiro.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
	
	@LocalServerPort
	private int port = 8080;
	
	private WebDriver driver;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:4200");
	}
	
	@After
	public void finish() {
		driver.quit();
	}
	
	@Test
	public void t1_signupError() throws InterruptedException {
		
		// Screen SignIn
		WebElement btnSignup = driver.findElement(By.className("btn-link-cadastro"));
		btnSignup.click();
		
		assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
		
		// Screen SignUp
		WebElement inputNome = driver.findElement(By.id("input-nome"));
		WebElement inputCPF = driver.findElement(By.xpath("//p-inputmask[@id='input-cpf']/input"));
		WebElement inputTelefone = driver.findElement(By.xpath("//p-inputmask[@id='input-telefone']/input"));
		WebElement inputDataNascimento = driver.findElement(By.xpath("//p-inputmask[@id='input-data-nascimento']/input"));
		WebElement inputEmail = driver.findElement(By.id("input-email"));
		WebElement inputPassword = driver.findElement(By.id("input-password"));
		WebElement inputPasswordConfirm = driver.findElement(By.id("input-password-confirm"));
		
		inputNome.click();
		Thread.sleep(500);
		inputNome.sendKeys("Henrique Severino Yuri Pereira");
		
		inputCPF.click();
		Thread.sleep(500);
		inputCPF.sendKeys("11111111111");
		
		inputTelefone.click();
		Thread.sleep(500);
		inputTelefone.sendKeys("83991905120");
	
		inputDataNascimento.click();
		Thread.sleep(500);
		inputDataNascimento.sendKeys("18061962");
	
		inputEmail.click();
		Thread.sleep(500);
		inputEmail.sendKeys("henriqueseverinoyuripereira_@otlokk.com");
	
		inputPassword.click();
		Thread.sleep(500);
		inputPassword.sendKeys("1234");
	
		inputPasswordConfirm.click();
		Thread.sleep(500);
		inputPasswordConfirm.sendKeys("1234");
		
		WebElement btnCadastrar = driver.findElement(By.id("btn-cadastrar"));
		btnCadastrar.click();
		
		Thread.sleep(2000);
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		
		assertEquals("Não foi possível concluír o cadastro.", toast.getText());
		assertEquals(inputCPF.getText(), "");
		assertEquals(true, inputNome.getAttribute("class").contains("ng-invalid"));
		
	}
	
	@Test
	public void t2_signupSuccess() throws InterruptedException {
		
		// Screen SignIn
		WebElement btnSignup = driver.findElement(By.className("btn-link-cadastro"));
		btnSignup.click();
		
		assertEquals("http://localhost:4200/sign-up", driver.getCurrentUrl());
		
		// Screen SignUp
		WebElement inputNome = driver.findElement(By.id("input-nome"));
		WebElement inputCPF = driver.findElement(By.xpath("//p-inputmask[@id='input-cpf']/input"));
		WebElement inputTelefone = driver.findElement(By.xpath("//p-inputmask[@id='input-telefone']/input"));
		WebElement inputDataNascimento = driver.findElement(By.xpath("//p-inputmask[@id='input-data-nascimento']/input"));
		WebElement inputEmail = driver.findElement(By.id("input-email"));
		WebElement inputPassword = driver.findElement(By.id("input-password"));
		WebElement inputPasswordConfirm = driver.findElement(By.id("input-password-confirm"));
		
		inputNome.click();
		Thread.sleep(500);
		inputNome.sendKeys("Henrique Severino Yuri Pereira");
		
		inputCPF.click();
		Thread.sleep(500);
		inputCPF.sendKeys("56507602507");
		
		inputTelefone.click();
		Thread.sleep(500);
		inputTelefone.sendKeys("28992087738");
	
		inputDataNascimento.click();
		Thread.sleep(500);
		inputDataNascimento.sendKeys("18061962");
	
		inputEmail.click();
		Thread.sleep(500);
		inputEmail.sendKeys("igor.pimentel.msi@hotmail.com");
	
		inputPassword.click();
		Thread.sleep(500);
		inputPassword.sendKeys("65jd8RFEel#");
	
		inputPasswordConfirm.click();
		Thread.sleep(500);
		inputPasswordConfirm.sendKeys("65jd8RFEel#");
		
		WebElement btnCadastrar = driver.findElement(By.id("btn-cadastrar"));
		btnCadastrar.click();
		
		Thread.sleep(3000);
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		
		assertEquals("Conta criada com sucesso.", toast.getText());
		assertEquals("http://localhost:4200/", driver.getCurrentUrl());
	}
	
	@Test
	public void t3_signinError() throws InterruptedException {
		
		WebElement btnEntrar = driver.findElement(By.xpath("//p-button[@type='submit']/button"));
		WebElement inputEmail = driver.findElement(By.id("input-email"));
		WebElement inputPassword = driver.findElement(By.id("input-password"));
		
		inputEmail.sendKeys("henriqueseverinoyuripereira_@otlokk.com");
		inputPassword.sendKeys("1234");
		
		assertEquals(true, inputEmail.getAttribute("class").contains("ng-valid"));
		assertEquals(true, inputPassword.getAttribute("class").contains("ng-valid"));
		
		btnEntrar.click();
		
		Thread.sleep(2000);
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		
		assertEquals("Usuário Não Encontrado.", toast.getText());
		assertEquals("http://localhost:4200/", driver.getCurrentUrl());
		assertEquals(true, inputEmail.getAttribute("class").contains("ng-invalid"));
		assertEquals(true, inputPassword.getAttribute("class").contains("ng-invalid"));
	
	}
	
	@Test
	public void t4_signinSuccess() throws InterruptedException {
		
		WebElement btnEntrar = driver.findElement(By.xpath("//p-button[@type='submit']/button"));
		WebElement inputEmail = driver.findElement(By.id("input-email"));
		WebElement inputPassword = driver.findElement(By.id("input-password"));
		
		assertEquals(false, btnEntrar.isEnabled());
		
		inputEmail.sendKeys("igor.pimentel.msi@hotmail.com");
		inputPassword.sendKeys("65jd8RFEel#");
		
		assertEquals(true, btnEntrar.isEnabled());
		
		btnEntrar.click();
		
		Thread.sleep(3000);
	
		WebElement titulo = driver.findElement(By.xpath("//div[@class='titulo']/h2"));
		
		assertEquals("Cine Monteiro - Relatório", titulo.getText());
		assertEquals("http://localhost:4200/home-admin", driver.getCurrentUrl());
		
		Thread.sleep(1000);
		
		// Logout
		WebElement linkLogout = driver.findElement(By.xpath("//nav/a[@href='/']"));
		linkLogout.click();
		
		assertEquals("http://localhost:4200/", driver.getCurrentUrl());
		
		Thread.sleep(1000);
	}
	
	@Test
	public void t5_recoverError() throws InterruptedException {
	
		// Screen SignIn
		WebElement btnRecover = driver.findElement(By.className("btn-link-recover"));
		btnRecover.click();
		
		assertEquals("http://localhost:4200/recover", driver.getCurrentUrl());

		// Screen Recover
		WebElement btnEnviar = driver.findElement(By.id("btn-enviar"));
		WebElement inputEmail = driver.findElement(By.id("input-email"));
		
		inputEmail.sendKeys("belijag308@gruppies.com");		
		
		assertEquals(true, inputEmail.getAttribute("class").contains("ng-valid"));
		
		Thread.sleep(1000);
		
		btnEnviar.click();
		
		Thread.sleep(2000);
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		
		assertEquals("Usuário Não Encontrado.", toast.getText());
		assertEquals(true, inputEmail.getAttribute("class").contains("ng-invalid"));
		assertEquals("http://localhost:4200/recover", driver.getCurrentUrl());

	}
	
	@Test
	public void t6_recoverSuccess() throws InterruptedException {
		
		// Screen SignIn
		WebElement btnRecover = driver.findElement(By.className("btn-link-recover"));
		btnRecover.click();
		
		assertEquals("http://localhost:4200/recover", driver.getCurrentUrl());

		// Screen Recover
		WebElement btnEnviar = driver.findElement(By.id("btn-enviar"));
		
		assertEquals(false, btnEnviar.isEnabled());
		
		WebElement inputEmail = driver.findElement(By.id("input-email"));
		
		inputEmail.sendKeys("igor.pimentel.msi@hotmail.com");		
		
		Thread.sleep(1000);
		
		assertEquals(true, btnEnviar.isEnabled());
		
		btnEnviar.click();
		
		Thread.sleep(9000);
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		
		assertEquals("Verifique seu e-mail.", toast.getText());
		assertEquals("http://localhost:4200/", driver.getCurrentUrl());
		
	}

}
