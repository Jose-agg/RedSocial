package com.uniovi.tests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_InvitationView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedSocialTests {
	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox = "D:\\Jose\\Documentos\\Clases\\Sistema Distribuidos e Internet\\Firefox46.win\\FirefoxPortable.exe";
	// static String PathFirefox = "FirefoxPortable\\FirefoxPortable.exe";
	// En MACOSX (Debe ser la versión 46.0 y desactivar las actualizaciones
	// automáticas):
	// static String PathFirefox =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
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
		driver.navigate().to(URL);
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
		driver.quit();
		resetBD();
	}

	@Test
	public void PR01RegVal() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "Rodolfo0", "chiquilicuatre0@gmail.com", "123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "Nuevo usuario registrado.");
	}

	@Test
	public void PR02RegInVal() {
		chargeBD();
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "Hugo", "hugo@hotmail.es", "123", "123");

		// COmprobamos el error de name repetido.
		PO_View.checkElement(driver, "text",
				"Error al registrar usuario, el email ya esta registrado en la aplicacion.");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "Rodolfo2", "chiquilicuatre2@gmail.com", "123456", "xxxxx");
		// COmprobamos el error de Nombre corto .
		PO_View.checkElement(driver, "text", "Error al registrar usuario, las contraseñas no coinciden.");
	}

	@Test
	public void PR03InVal() {
		chargeBD();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "hugo@hotmail.es", "123");
		// COmprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "p", "Hugo");
	}

	@Test
	public void PR03CInVal() {
		chargeBD();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "hugo@hotmail.es", "123");
		// COmprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "p", "Hugo");
	}

	@Test
	public void PR04InInVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "noExisto@noExisto", "user");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Email o password incorrecto");
	}

	@Test
	public void PR05UsrListVal() {
		chargeBD();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "hugo@hotmail.es", "123");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Jose");
		PO_View.checkElement(driver, "text", "Alberto");
		PO_View.checkElement(driver, "text", "Sofia");
	}

	@Test
	public void PR06UsrListInVal() {
		driver.navigate().to(URL + "/usuarios");
		PO_View.checkElement(driver, "text", "Identificación de usuario");
	}

	@Test
	public void PR07BusUsrVal() {
		chargeBD();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "hugo@hotmail.es", "123");
		// Realizamos la busqueda del usuario para que muestre uno que coincida con
		// el
		// Nombre y otro con el mail
		PO_PrivateView.fillFormSearchUser(driver, "a");
		// Comprobaciones
		PO_View.checkElement(driver, "text", "Sofia");
		PO_View.checkElement(driver, "text", "Juan");
	}

	@Test
	public void PR06BusUsrInVal() {
		driver.navigate().to(URL + "/usuarios?busqueda=a");
		PO_View.checkElement(driver, "text", "Identificación de usuario");
	}

	@Test
	public void PR07InvVal() {
		chargeBD();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pelayo@hotmail.es", "123");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "p", "Pelayo");

		PO_InvitationView.clickEnlace(driver, "Sofia");

		PO_View.checkElement(driver, "text", "Invitacion enviada");
	}

	@Test
	public void PR08InvInVal() {
		chargeBD();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pelayo@hotmail.es", "123");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "p", "Pelayo");

		PO_InvitationView.clickEnlace(driver, "Sofia");

		PO_View.checkElement(driver, "text", "Invitacion enviada");
		PO_View.checkElement(driver, "p", "Pelayo");

		PO_InvitationView.clickEnlace(driver, "Sofia");

		PO_View.checkElement(driver, "text", "Invitacion cancelada:");
	}

	@Test
	public void PR09LisInvVal() {
		chargeBD();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pelayo@hotmail.es", "123");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "p", "Pelayo");

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'/invitations')]");
		elementos.get(0).click();

		PO_View.checkElement(driver, "p", "Alberto");

	}

	@Test
	public void PR10LisInvInVal() {
		chargeBD();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pelayo@hotmail.es", "123");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "p", "Pelayo");

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'/invitations')]");
		elementos.get(0).click();

		PO_View.checkElement(driver, "p", "Alberto");

	}

	@Test
	public void PR11AcepInvVal() {
		chargeBD();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pelayo@hotmail.es", "123");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "p", "Pelayo");

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'/invitations')]");
		elementos.get(0).click();

		PO_View.checkElement(driver, "p", "Alberto");

		driver.findElement(By.id("Alberto")).click();

		PO_View.checkElement(driver, "text", "Peticion de amistad aceptada");
	}

	@Test
	public void PR13ListAmiVal() {
		chargeBD();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pelayo@hotmail.es", "123");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "p", "Pelayo");

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'/friends')]");
		elementos.get(0).click();

		PO_View.checkElement(driver, "p", "Hugo");
	}
}
