package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.utils.SeleniumUtils;

public class PO_PrivateView extends PO_NavView {

	static public void fillFormSearchUser(WebDriver driver, String user) {
		// Esperamos 2 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 2);
		// Rellenemos el campo de busqueda
		WebElement description = driver.findElement(By.name("searchText"));
		description.clear();
		description.sendKeys(user);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}