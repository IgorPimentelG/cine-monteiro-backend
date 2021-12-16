package com.cine.monteiro.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FilmeTest {

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
		inputEmail.sendKeys("fulano@gmail.com");
		delay();
		
		WebElement inputSenha = driver.findElement(By.id("input-password"));
		inputSenha.sendKeys("@Cadastro1");
		delay();
		
		WebElement btnEntrar = driver
				.findElement(By.xpath("/html/body/app-root/app-sign-in/div/p-card/div/div/div/form/p-button/button"));
		btnEntrar.click();
		delay();
	}
	
	@Test
	public void A_teste_cadastrar_generoSuccess() throws Exception {

		WebElement linkFilme = driver.findElement(By.xpath("//a[@href='/filme']"));
		linkFilme.click();
		delay();
		
		WebElement spanNovoGenero = driver.findElement(By.xpath("//*[@id=\"p-tabpanel-3-label\"]/span[2]"));
		spanNovoGenero.click();
		delay();
		
		WebElement inputNome = driver.findElement(By.id("input-nome"));
		inputNome.sendKeys("Ação");
		delay();
		
		WebElement inputDescricao = driver.findElement(By.id("input-descricao"));
		inputDescricao.sendKeys("Lançamento");
		delay();
		
		WebElement btnCadastrar = driver.findElement(By.xpath("//*[@id=\"p-tabpanel-3\"]/div/p-card/div/div/div/form/div/p-button[1]"));
		
		btnCadastrar.click();
		delay();
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		assertEquals("Novo gênero cadastrado com sucesso.", toast.getText());
		delay();
	}
	
	@Test
	public void B_cadastrar_filmeError() throws Exception {
		
		WebElement linkFilme = driver.findElement(By.xpath("//a[@href='/filme']"));
		linkFilme.click();
		delay();

		WebElement spanNovoFilme = driver.findElement(By.xpath("//*[@id=\"p-tabpanel-1-label\"]/span[2]"));
		spanNovoFilme.click();
		delay();
		
		WebElement inputTitulo = driver.findElement(By.id("input-titulo"));
		inputTitulo.sendKeys("Homem Aranha");
		delay();
		
		WebElement inputSinopse = driver.findElement(By.id("input-sinopse"));
		inputSinopse.sendKeys("História de Peter Parker");
		delay();
		
		//Duração do filme menor que 15 minutos
		WebElement inputDuracao = driver.findElement(By.id("input-duracao"));
		inputDuracao.sendKeys("14");
		delay();
		
		WebElement selectGenero = driver.findElement(By.xpath("//p-dropdown[1]/div/span"));
		selectGenero.click();
		delay();
		
		//sempre altere o id
		WebElement selectGeneroItem = driver.findElement(By.xpath("//span[contains(.,'12 - Ação')]"));
		selectGeneroItem.click();
		delay();
		
		
		WebElement selectClassificacao = driver.findElement(By.xpath("//p-dropdown[2]/div/span"));
		selectClassificacao.click();
		delay();
		
		WebElement selectClassificacaoItem = driver.findElement(By.xpath("//span[contains(.,'MAIOR OU IGUAL 16')]"));
		selectClassificacaoItem.click();
		delay();
		
		WebElement selectLegenda = driver.findElement(By.xpath("//p-dropdown[3]/div/span"));
		selectLegenda.click();
		delay();
		
		WebElement selectLegendaItem = driver.findElement(By.xpath("//span[contains(.,'PORTUGUÊS')]"));
		selectLegendaItem.click();
		delay();
		
		WebElement selectProjecao = driver.findElement(By.xpath("//p-dropdown[4]/div/span"));
		selectProjecao.click();
		delay();
		
		WebElement selectProjecaoItem = driver.findElement(By.xpath("//span[contains(.,'PROJEÇÃO 3D')]"));
		selectProjecaoItem.click();
		delay();
		
		WebElement btnCadastrar = driver.findElement(By.xpath("//*[@id=\"p-tabpanel-1\"]/div/p-card/div/div/div/form/div/p-button[1]"));
		btnCadastrar.click();
		delay();
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		assertEquals("Não foi possível cadastrar o novo filme.", toast.getText());
		delay();
	}
	
	@Test
	public void C_teste_cadastrar_filmeSuccess() throws Exception {
		
		WebElement linkFilme = driver.findElement(By.xpath("//a[@href='/filme']"));
		linkFilme.click();
		delay();

		WebElement spanNovoFilme = driver.findElement(By.xpath("//*[@id=\"p-tabpanel-1-label\"]/span[2]"));
		spanNovoFilme.click();
		delay();
		
		WebElement inputTitulo = driver.findElement(By.id("input-titulo"));
		inputTitulo.sendKeys("Homem Aranha");
		delay();
		
		WebElement inputSinopse = driver.findElement(By.id("input-sinopse"));
		inputSinopse.sendKeys("História de Peter Parker");
		delay();
		
		//duração igual a 15 minutos
		WebElement inputDuracao = driver.findElement(By.id("input-duracao"));
		inputDuracao.sendKeys("15");
		delay();
		
		WebElement selectGenero = driver.findElement(By.xpath("//p-dropdown[1]/div/span"));
		selectGenero.click();
		delay();
		
		//sempre altere o id
		WebElement selectGeneroItem = driver.findElement(By.xpath("//span[contains(.,'12 - Ação')]"));
		selectGeneroItem.click();
		delay();
		
		WebElement selectClassificacao = driver.findElement(By.xpath("//p-dropdown[2]/div/span"));
		selectClassificacao.click();
		delay();
		
		WebElement selectClassificacaoItem = driver.findElement(By.xpath("//span[contains(.,'MAIOR OU IGUAL 16')]"));
		selectClassificacaoItem.click();
		delay();
		
		WebElement selectLegenda = driver.findElement(By.xpath("//p-dropdown[3]/div/span"));
		selectLegenda.click();
		delay();
		
		WebElement selectLegendaItem = driver.findElement(By.xpath("//span[contains(.,'PORTUGUÊS')]"));
		selectLegendaItem.click();
		delay();
		
		WebElement selectProjecao = driver.findElement(By.xpath("//p-dropdown[4]/div/span"));
		selectProjecao.click();
		delay();
		
		WebElement selectProjecaoItem = driver.findElement(By.xpath("//span[contains(.,'PROJEÇÃO 3D')]"));
		selectProjecaoItem.click();
		delay();
		
		WebElement btnCadastrar = driver.findElement(By.xpath("//*[@id=\"p-tabpanel-1\"]/div/p-card/div/div/div/form/div/p-button[1]"));
		btnCadastrar.click();
		delay();
		
		WebElement toast = driver.findElement(By.className("p-toast-detail"));
		assertEquals("Novo filme cadastrado com sucesso.", toast.getText());
		delay();
	}
	
	private void delay() throws Exception {
		Thread.sleep(2000);
	}
	
	@After
	public void finish() {
		driver.quit();
	}
}
