package com.cine.monteiro.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
public class SessaoTest {
	
	@LocalServerPort
	private int port = 8080;
	
	private WebDriver driver;
	
	private static final String PASSWORD_ADMIN = "8aruJ-ve#";
	
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
	public void t1_cadastrarSessaoError() throws InterruptedException {
		
		// Screen SignIn
		driver.findElement(By.id("input-email")).sendKeys("igor.pimentel.msi@hotmail.com");
		driver.findElement(By.id("input-password")).sendKeys(PASSWORD_ADMIN);
		driver.findElement(By.xpath("//p-button[@type='submit']/button")).click();
		
		Thread.sleep(3000);
		
		// Home Admin
		driver.findElement(By.xpath("//nav/a[@href='/sessao']")).click();
		
		Thread.sleep(1000);
		
		assertEquals("http://localhost:4200/sessao", driver.getCurrentUrl());
		
		// Sessões
		WebElement btnNovaSessao = driver.findElement(By.linkText("Nova Sessão"));
		btnNovaSessao.click();
		
		WebElement inputPrecoIngresso = driver.findElement(By.xpath("//p-inputmask[@id='input-preco-ingresso']/input"));
		WebElement inputHoraInicioDeExibicao = driver.findElement(By.xpath("//p-inputmask[@id='input-hora-exibicao']/input"));
		WebElement inputDataInicioDeExibicao = driver.findElement(By.xpath("//p-inputmask[@id='input-inicio-exibicao']/input"));
		WebElement inputDataDoTerminoDeExibicao = driver.findElement(By.xpath("//p-inputmask[@id='input-termino-exibicao']/input"));
		
		driver.findElement(By.xpath("//span[contains(., 'Selecione a sala')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[contains(.,'4 - Sala 03')]")).click();
		
		driver.findElement(By.xpath("//span[contains(., 'Selecione o filme')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[contains(.,'2 - Ataque dos Cães')]")).click();
		
		inputPrecoIngresso.click();
		Thread.sleep(1000);
		inputPrecoIngresso.sendKeys("1500");
		
		inputHoraInicioDeExibicao.click();
		Thread.sleep(1000);
		inputHoraInicioDeExibicao.sendKeys("001000");
		
		inputDataInicioDeExibicao.click();
		Thread.sleep(1000);
		inputDataInicioDeExibicao.sendKeys("15122021");
		
		inputDataDoTerminoDeExibicao.click();
		Thread.sleep(1000);
		inputDataDoTerminoDeExibicao.sendKeys("05112021");
		
		driver.findElement(By.xpath("//p-button[@type='submit']/button")).click();
		
		Thread.sleep(2000);
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		
		assertEquals("Não foi possível cadastrar a sessão.", toast.getText());
		assertTrue(toast.isDisplayed());
		
	}
	
	@Test
	public void t2_cadastrarSessaoSucess() throws InterruptedException {
		
		// Screen SignIn
		driver.findElement(By.id("input-email")).sendKeys("igor.pimentel.msi@hotmail.com");
		driver.findElement(By.id("input-password")).sendKeys(PASSWORD_ADMIN);
		driver.findElement(By.xpath("//p-button[@type='submit']/button")).click();

		Thread.sleep(3000);
		
		// Home Admin
		driver.findElement(By.xpath("//nav/a[@href='/sessao']")).click();
		
		Thread.sleep(1000);
		
		assertEquals("http://localhost:4200/sessao", driver.getCurrentUrl());
		
		// Sessões
		driver.findElement(By.linkText("Nova Sessão")).click();
		
		WebElement inputPrecoIngresso = driver.findElement(By.xpath("//p-inputmask[@id='input-preco-ingresso']/input"));
		WebElement inputHoraInicioDeExibicao = driver.findElement(By.xpath("//p-inputmask[@id='input-hora-exibicao']/input"));
		WebElement inputDataInicioDeExibicao = driver.findElement(By.xpath("//p-inputmask[@id='input-inicio-exibicao']/input"));
		WebElement inputDataDoTerminoDeExibicao = driver.findElement(By.xpath("//p-inputmask[@id='input-termino-exibicao']/input"));
		
		driver.findElement(By.xpath("//span[contains(., 'Selecione a sala')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[contains(.,'4 - Sala 03')]")).click();
		
		driver.findElement(By.xpath("//span[contains(., 'Selecione o filme')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[contains(.,'2 - Ataque dos Cães')]")).click();
		
		inputPrecoIngresso.click();
		Thread.sleep(1000);
		inputPrecoIngresso.sendKeys("2000");
		
		inputHoraInicioDeExibicao.click();
		Thread.sleep(1000);
		inputHoraInicioDeExibicao.sendKeys("190000");
		
		inputDataInicioDeExibicao.click();
		Thread.sleep(1000);
		inputDataInicioDeExibicao.sendKeys("16122021");
		
		inputDataDoTerminoDeExibicao.click();
		Thread.sleep(1000);
		inputDataDoTerminoDeExibicao.sendKeys("05012022");
		
		driver.findElement(By.xpath("//p-button[@type='submit']/button")).click();
		
		Thread.sleep(3000);
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		
		assertEquals("Sessão cadastrada com sucesso.", toast.getText());
		assertTrue(toast.isDisplayed());
		
		assertEquals("Sessões Cadastradas", driver.findElement(By.className("p-card-title")).getText());
		assertNotNull(driver.findElement(By.xpath("//td[contains(.,'ATIVA')]")));
		assertNotNull(driver.findElement(By.xpath("//td[contains(.,'Sala 03')]")));
		assertNotNull(driver.findElement(By.xpath("//td[contains(.,'Ataque dos Cães')]")));
		
	}
	
	@Test
	public void t3_visualizacaoClienteSucess() throws InterruptedException {
		
		// Screen SignIn
		driver.findElement(By.className("btn-link-cadastro")).click();
	
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
		inputNome.sendKeys("Manoel Renato Farias");
		
		inputCPF.click();
		Thread.sleep(500);
		inputCPF.sendKeys("45857506069");
		
		inputTelefone.click();
		Thread.sleep(500);
		inputTelefone.sendKeys("61997903928");
	
		inputDataNascimento.click();
		Thread.sleep(500);
		inputDataNascimento.sendKeys("24041965");
	
		inputEmail.click();
		Thread.sleep(500);
		inputEmail.sendKeys("igorpimentel46@gmail.com");
	
		inputPassword.click();
		Thread.sleep(500);
		inputPassword.sendKeys("65jd8RFEel#");
	
		inputPasswordConfirm.click();
		Thread.sleep(500);
		inputPasswordConfirm.sendKeys("65jd8RFEel#");
		
		driver.findElement(By.id("btn-cadastrar")).click();
	
		Thread.sleep(3000);
		
		// Screen SignIn
		driver.findElement(By.id("input-email")).sendKeys("igorpimentel46@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("65jd8RFEel#");
		
		driver.findElement(By.xpath("//p-button[@type='submit']/button")).click();
		
		Thread.sleep(3000);
		
		assertEquals("http://localhost:4200/home-client", driver.getCurrentUrl());
		assertNotNull(driver.findElement(By.xpath("//h2[contains(., 'Cine Monteiro - Confira Nossa Programação')]")));
		assertNotNull(driver.findElement(By.xpath("//h4[contains(., 'Ataque dos Cães')]")));
		assertNotNull(driver.findElement(By.className("btn-comprar-ingresso")));
	}
	
	@Test
	public void t4_desativarSessao() throws InterruptedException {
		
		// Screen SignIn
		driver.findElement(By.id("input-email")).sendKeys("igor.pimentel.msi@hotmail.com");
		driver.findElement(By.id("input-password")).sendKeys(PASSWORD_ADMIN);
		driver.findElement(By.xpath("//p-button[@type='submit']/button")).click();

		Thread.sleep(3000);
		
		// Home Admin
		driver.findElement(By.xpath("//nav/a[@href='/sessao']")).click();
		
		Thread.sleep(1000);
		
		assertEquals("http://localhost:4200/sessao", driver.getCurrentUrl());
		
		// Sessões
		driver.findElement(By.linkText("Desativar")).click();
		
		driver.findElement(By.xpath("//span[contains(., 'Selecione a sessão')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[contains(.,'ID: 6 | SALA: Sala 03 | FILME: Ataque dos Cães')]")).click();
			
		driver.findElement(By.className("btn-desativar")).click();
		
		Thread.sleep(3000);
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		
		assertEquals("Sessão desativada com sucesso.", toast.getText());
		assertTrue(toast.isDisplayed());
		assertNotNull(driver.findElement(By.xpath("//td[contains(.,'DESATIVADA')]")));
		assertNotNull(driver.findElement(By.xpath("//td[contains(.,'Sala 03')]")));
		assertNotNull(driver.findElement(By.xpath("//td[contains(.,'Ataque dos Cães')]")));
	}

	

}
