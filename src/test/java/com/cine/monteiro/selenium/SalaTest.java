package com.cine.monteiro.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SalaTest {

	private WebDriver driver;
	private long TIMEOUT_IN_SECONDS = 2;

	@Before
	public void init() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		new WebDriverWait(driver, TIMEOUT_IN_SECONDS);

		driver.get("http://localhost:4200/");
		delay();

		WebElement inputEmail = driver.findElement(By.id("input-email"));
		inputEmail.sendKeys("sicrano@gmail.com");
		delay();

		WebElement inputSenha = driver.findElement(By.id("input-password"));
		inputSenha.sendKeys("@Cadastro1");
		delay();

		WebElement btnEntrar = driver
				.findElement(By.xpath("/html/body/app-root/app-sign-in/div/p-card/div/div/div/form/p-button/button"));
		btnEntrar.click();
		delay();

		WebElement linkSala = driver.findElement(By.xpath("//a[@href='/sala']"));
		linkSala.click();
		delay();

		WebElement spanNovaSala = driver.findElement(By.xpath("//*[@id=\"p-tabpanel-1-label\"]/span[2]"));
		spanNovaSala.click();
		delay();
	}

	@Test
	public void teste_01_cadastarSalaError() throws Exception {

		WebElement inputNome = driver.findElement(By.id("input-nome"));
		inputNome.sendKeys("Sala 01");
		delay();

		// quantidade de assentos negativos
		WebElement inputAssentos = driver.findElement(By.id("input-assentos"));
		inputAssentos.sendKeys("-1");
		delay();

		WebElement btnCadastrar = driver
				.findElement(By.xpath("//*[@id=\"p-tabpanel-1\"]/div/p-card/div/div/div/form/div/p-button[1]"));
		btnCadastrar.click();
		delay();

		// mensagem erro 01
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		assertEquals("Sala não cadastrada.", toast.getText());
		
		// limpar os campos
		WebElement btnLimpar = driver
				.findElement(By.xpath("//*[@id=\"p-tabpanel-1\"]/div/p-card/div/div/div/form/div/p-button[2]"));
		btnLimpar.click();
		delay();

		inputNome.sendKeys("Sala 01");
		delay();

		// quantidade de assentos maior que permitido
		inputAssentos.sendKeys("41");
		delay();

		btnCadastrar.click();
		delay();
		
		// mesagem de erro 02
		WebElement toast2 = driver.findElement(By.xpath("/html/body/app-root/app-sala/p-toast"));
		assertEquals("Error!\nSala não cadastrada.",toast2.getText());
		
		btnLimpar.click();
		delay();

		inputNome.sendKeys("Sala 01");
		delay();

		// quantidade de assentos menor que a permitida
		inputAssentos.sendKeys("14");
		delay();

		btnCadastrar.click();
		delay();
		
		// mesagem de erro 03
		WebElement toast3 = driver.findElement(By.xpath("/html/body/app-root/app-sala/p-toast"));
		assertEquals(toast3.getText(),"Error!\nSala não cadastrada.");
	}

	@Test
	public void teste_02_cadastarSalaSuccess() throws Exception {

		WebElement inputNome = driver.findElement(By.id("input-nome"));
		inputNome.sendKeys("Sala 01");
		delay();

		WebElement inputAssentos = driver.findElement(By.id("input-assentos"));
		inputAssentos.sendKeys("40");
		delay();

		WebElement btnCadastrar = driver
				.findElement(By.xpath("//*[@id=\"p-tabpanel-1\"]/div/p-card/div/div/div/form/div/p-button[1]"));
		btnCadastrar.click();
		delay();

		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		assertEquals("Sala cadastrada com sucesso.", toast.getText());

	}

	private void delay() throws Exception {
		Thread.sleep(2000);
	}

	@After
	public void finish() {
		driver.quit();
	}
}
