package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_ConversationView {

	public static void fillForm(WebDriver driver, String textp) {
		WebElement text = driver.findElement(By.name("message"));
		text.click();
		text.clear();
		text.sendKeys(textp);
		By boton = By.className("btn");
		driver.findElement(boton).click();

	}
}
