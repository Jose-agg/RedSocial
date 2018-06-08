package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PO_InvitationView extends PO_View {

	static public void clickEnlace(WebDriver driver, String nombreUsuario) {
		By enlace = By.id(nombreUsuario);
		driver.findElement(enlace).click();
	}

}
