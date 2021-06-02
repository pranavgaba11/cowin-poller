package com.pranav.cowinpoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.pranav.cowinpoller.service.CowinService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CowinBooking implements ApplicationListener<ApplicationReadyEvent> {

  @Autowired
  CowinService cowinService;

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    log.info("Cowin Poller Started UP");
    try {
      cowinService.contactCowin();
    } catch (Exception e) {
      log.error("Some Exception occured while finding your slot");
      e.printStackTrace();
    }
  }
}
