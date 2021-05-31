package com.pranav.cowinpoller.constants;

public class CowinConstants {

  public static final String COWIN_URL = "https://cdn-api.co-vin.in/api/v2";
  public static final String COWIN_GET_OTP = COWIN_URL + "/auth/public/generateOTP";
  public static final String CONFIRM_OTP = COWIN_URL + "/auth/public/confirmOTP";
  public static final String FIND_SESSION_BY_PIN =
      COWIN_URL + "/appointment/sessions/public/findByPin?pincode=%s&date=%s";
  public static final String COWIN_WEB_URL = "https://www.cowin.gov.in/home";
  public static final String COWIN_UI_LOGIN = "https://selfregistration.cowin.gov.in/";

}
