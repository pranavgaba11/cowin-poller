package com.pranav.cowinpoller;

import static com.pranav.cowinpoller.constants.CowinConstants.FIND_SESSION_BY_PIN;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranav.cowinpoller.dto.Sessions;
import com.pranav.cowinpoller.service.CowinService;
import com.pranav.cowinpoller.service.RestService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CowinBooking implements ApplicationListener<ApplicationReadyEvent> {
  @Autowired
  RestService restService;

  @Autowired
  CowinService cowinService;

  @Autowired
  Scanner scanner;

  public void contactCowin() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    String phoneNumber = cowinService.getPhoneNumber();
    log.info("Enter your pincode : ");
    String pincode = scanner.nextLine();
    log.info("Enter date in FORMAT : DD-MM-YYYY");
    String date = scanner.nextLine();
    log.info("Enter Dose Type (Enter 1 for Dose 1 and 2 for Dose 2) : ");
    int dose = scanner.nextInt();
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
      if(dose==2)
        isSlotAvailable =
          sessionsList.stream().anyMatch(session -> session.getAvailable_capacity_dose2() > 0);
      else {
        isSlotAvailable =
            sessionsList.stream().anyMatch(session -> session.getAvailable_capacity_dose1() > 0);
      }
      if (isSlotAvailable)
        break;

      log.info("Slot not found, will poll again in 1 min...");
      TimeUnit.MINUTES.sleep(1);
    }
    if (isSlotAvailable) {
      sessionsList.stream().filter(sessions -> sessions.getAvailable_capacity_dose1() > 0).findAny()
          .ifPresent(System.out::println);
      cowinService.openCowinWebsite(phoneNumber);
    }
  }

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    log.info("Cowin Poller Started UP");
    try {
      contactCowin();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
