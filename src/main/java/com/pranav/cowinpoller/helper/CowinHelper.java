package com.pranav.cowinpoller.helper;

import static com.pranav.cowinpoller.constants.CowinConstants.CONFIRM_OTP;
import static com.pranav.cowinpoller.constants.CowinConstants.COVAXIN;
import static com.pranav.cowinpoller.constants.CowinConstants.COVISHIELD;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.pranav.cowinpoller.service.RestService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CowinHelper {

  @Autowired
  Scanner scanner;

  @Autowired
  RestService restService;

  public String getPhoneNumber() {
    log.info("Enter your phone number...");
    String phoneNumber = scanner.nextLine();
    return phoneNumber;
  }

  public String getPincode() {
    log.info("Enter your pincode : ");
    String pincode = scanner.nextLine();
    return pincode;
  }

  public String getDate() {
    log.info("Enter date in FORMAT : DD-MM-YYYY");
    String date = scanner.nextLine();
    return date;
  }

  public int getDose() {
    log.info("Enter Dose Type (Enter 1 for Dose 1 and 2 for Dose 2) : ");
    int dose = scanner.nextInt();
    return dose;
  }

  public int getVaccine() {
    log.info("Enter Vaccine choice (1 - COVISHIELD, 2 - COVAXIN, 3 - ANY) : ");
    int vaccine = scanner.nextInt();
    return vaccine;
  }

  public String getVaccineType(int vaccineInput) {
    return vaccineInput == 1 ? COVISHIELD : COVAXIN;
  }

  public String confirmCowinOtp(String txnId) throws JSONException, NoSuchAlgorithmException {
    JSONObject json = new JSONObject();
    log.info("Enter OTP....");
    String OTP = scanner.nextLine();
    json.clear();
    json.put("otp", CowinHelper.toHexString(OTP));
    json.put("txnId", txnId);
    return restService.makePostRequest(CONFIRM_OTP, json.toString());
  }

  public static String toHexString(String input) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
    BigInteger number = new BigInteger(1, hash);
    StringBuilder hexString = new StringBuilder(number.toString(16));
    while (hexString.length() < 32) {
      hexString.insert(0, '0');
    }
    return hexString.toString();
  }

}
