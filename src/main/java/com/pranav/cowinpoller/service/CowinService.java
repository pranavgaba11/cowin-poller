package com.pranav.cowinpoller.service;

import static com.pranav.cowinpoller.constants.CowinConstants.COWIN_UI_LOGIN;
import static com.pranav.cowinpoller.constants.CowinConstants.FIND_SESSION_BY_PIN;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranav.cowinpoller.dto.Sessions;
import com.pranav.cowinpoller.helper.CowinHelper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CowinService {

  @Autowired
  RestService restService;

  @Autowired
  CowinHelper cowinHelper;

  public void contactCowin() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    String phoneNumber = cowinHelper.getPhoneNumber();
    String pincode = cowinHelper.getPincode();
    String date = cowinHelper.getDate();
    int dose = cowinHelper.getDose();
    int vaccine = cowinHelper.getVaccine();
    boolean isSlotAvailable = false;
    String getSessionByPincodeUrl = String.format(FIND_SESSION_BY_PIN, pincode, date);
    List<Sessions> sessionsList = new ArrayList<>();

    while (!isSlotAvailable) {
      ResponseEntity<String> findSessionResponse =
          restService.makeGetRequest(getSessionByPincodeUrl);
      JSONObject jsonObject = new JSONObject(findSessionResponse.getBody().toString());
      JSONArray jsonArray = jsonObject.getJSONArray("sessions");

      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject readFromJsonObject = jsonArray.getJSONObject(i);
        sessionsList.add(objectMapper.readValue(readFromJsonObject.toString(), Sessions.class));
      }

      if (vaccine == 1 || vaccine == 2) {
        isSlotAvailable = sessionsList.stream()
            .filter(sessions -> sessions.getVaccine().equals(cowinHelper.getVaccineType(vaccine)))
            .anyMatch(dose == 1 ? sessions -> sessions.getAvailable_capacity_dose1() > 0
                : sessions -> sessions.getAvailable_capacity_dose2() > 0);
      } else {
        isSlotAvailable = sessionsList.stream()
            .anyMatch(dose == 1 ? sessions -> sessions.getAvailable_capacity_dose1() > 0
                : sessions -> sessions.getAvailable_capacity_dose2() > 0);
      }

      if (isSlotAvailable)
        break;

      log.info("Slot not found, will poll again in 1 min...");
      TimeUnit.MINUTES.sleep(1);
    }

    if (isSlotAvailable) {
      sessionsList.stream()
          .filter(dose == 1 ? sessions -> sessions.getAvailable_capacity_dose1() > 0
              : sessions -> sessions.getAvailable_capacity_dose2() > 0)
          .findAny().ifPresent(System.out::println);
      openCowinWebsite(phoneNumber);
    }
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
