package test.selenium;

import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class SeleniumTest {
	public static void test01() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://test.sj2.qjbian.com/");
		WebElement username = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
		WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
		WebElement login = driver.findElement(By.xpath("//*[@id=\"popAuth\"]"));
		username.sendKeys("111");
		password.sendKeys("222");
		login.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement img = driver.findElement(By.xpath("//*[@id=\"yzm-img\"]"));
		Actions action = new Actions(driver);
		int width = img.getSize().width;
		int height = img.getSize().height;
		action.moveToElement(img).perform();
		action.moveByOffset(RandomUtils.nextInt(0, width), RandomUtils.nextInt(0, height)).perform();
		action.click().perform();
		action.moveToElement(img).perform();
		action.moveByOffset(RandomUtils.nextInt(0, width), RandomUtils.nextInt(0, height)).perform();
		action.click().perform();
	}

	public static void main(String[] args) {
		test01();
	}
}
