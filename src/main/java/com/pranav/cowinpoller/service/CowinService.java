package com.pranav.cowinpoller.service;

import static com.pranav.cowinpoller.constants.CowinConstants.CONFIRM_OTP;
import static com.pranav.cowinpoller.constants.CowinConstants.COWIN_UI_LOGIN;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pranav.cowinpoller.helper.CowinHelper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CowinService {

  @Autowired
  RestService restService;

  @Autowired
  Scanner scanner;

  public String getPhoneNumber() {
    log.info("Enter your phone number...");
    String phoneNumber = scanner.nextLine();
    return phoneNumber;
  }

  public String confirmCowinOtp(String txnId) throws JSONException, NoSuchAlgorithmException {
    JSONObject json = new JSONObject();
    log.info("Enter OTP....");
    String OTP = scanner.nextLine();
    json.clear();
    json.put("otp", CowinHelper.toHexString(OTP));
    json.put("txnId", txnId);
    scanner.close();
    return restService.makePostRequest(CONFIRM_OTP, json.toString());
  }

  public void openCowinWebsite(String phoneNumber) {
    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    try {
      WebDriver driver = new ChromeDriver();
      driver.manage().window().maximize();
      driver.get(COWIN_UI_LOGIN);
      WebElement l = driver.findElement(By.id("mat-input-0"));
      Thread.sleep(5000);
      l.sendKeys(phoneNumber);
      WebElement l2 = driver.findElement(By.className("login-btn"));
      l2.click();
    } catch (Exception e) {
      log.error(
          "There was an error opening your browser. Slots are available. Please manually go to the url: "
              + COWIN_UI_LOGIN);
    }
  }
}
