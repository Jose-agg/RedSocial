package com.uniovi.tests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_ConversationView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_View;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteRedSocialTest {
	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox = "FirefoxPortable\\FirefoxPortable.exe";

	// En MACOSX (Debe ser la versión 46.0 y desactivar las actualizaciones
	// automáticas):
	// static String PathFirefox =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String CLI_URL = "http://localhost:8081/cliente.html";
	static String URL = "http://localhost:8081";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versión 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	private static void resetBD() {

		URL url;
		HttpURLConnection con = null;
		try {
			url = new URL(URL + "/borrarBBDD");

			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				con.disconnect();
		}
	}

	private static void chargeBD() {

		URL url;
		HttpURLConnection con = null;
		try {
			url = new URL(URL + "/rellenarBBDD");

			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				con.disconnect();
		}
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(CLI_URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
		resetBD();
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		resetBD();
		driver.quit();
	}

	@Test
	public void PR01CInVal() {
		chargeBD();
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "hugo@hotmail.es", "123");
		// COmprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "h2", "Usuarios");
	}

	@Test
	public void PR02CInInVal() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "noExisto@noExisto", "user");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Usuario no encontrado");
	}

	@Test
	public void PR03CListAmiVal() {
		resetBD();
		chargeBD();
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "hugo@hotmail.es", "123");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Jose");
		PO_View.checkElement(driver, "text", "Rosa");
		PO_View.checkElement(driver, "text", "Pelayo");
	}

	@Test
	public void PR04CListAmiFil() {
		resetBD();
		chargeBD();
		PO_LoginView.fillForm(driver, "hugo@hotmail.es", "123");
		// Realizamos la busqueda del usuario para que muestre uno que coincida con
		// el
		// Nombre y otro con el mail
		PO_PrivateView.fillFormSearchUserCli(driver, "Jose");
		// Comprobaciones
		PO_View.checkElement(driver, "text", "Jose");
	}

	@Test
	public void PR05CListMenVal() {
		resetBD();
		chargeBD();
		PO_LoginView.fillForm(driver, "jose@hotmail.es", "123");

		PO_View.checkElement(driver, "h2", "Usuarios");

		driver.findElement(By.id("conversacion-hugo@hotmail.es")).click();

		PO_View.checkElement(driver, "th", "Emisor");

		PO_ConversationView.fillForm(driver, "Hola Hugo");
		PO_ConversationView.fillForm(driver, "Que tal estas?");
		PO_ConversationView.fillForm(driver, "Ya hablamos, adios");

		PO_View.checkElement(driver, "p", "Hola Hugo");
		PO_View.checkElement(driver, "p", "Que tal estas?");
		PO_View.checkElement(driver, "p", "Ya hablamos, adios");
	}

	@Test
	public void PR06CCrearMenVal() {
		resetBD();
		chargeBD();
		PO_LoginView.fillForm(driver, "jose@hotmail.es", "123");

		PO_View.checkElement(driver, "h2", "Usuarios");

		driver.findElement(By.id("conversacion-hugo@hotmail.es")).click();

		PO_View.checkElement(driver, "th", "Emisor");

		PO_ConversationView.fillForm(driver, "Esta seria la ultima prueba, espero que salga bien");

		PO_View.checkElement(driver, "th", "Emisor");

		PO_View.checkElement(driver, "p", "Esta seria la ultima prueba, espero que salga bien");

	}

}
