package com.pranav.cowinpoller.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Sessions {
  
  Long center_id;
  String name;
  String address;
  String state_name;
  String district_name;
  String block_name;
  Long pincode;
  String from;
  String to;
  int lat;
  @JsonAlias({ "long" })
  int longitude;
  String fee_type;
  String session_id;
  String date;
  int available_capacity_dose1;
  int available_capacity_dose2;
  int available_capacity;
  String fee;
  int min_age_limit;
  String vaccine;
  List<String> slots;
  

}
