# cowin-poller
Poll Cowin APIs to check if the vaccine is available for a given date.
The application will ask for some basic details in the starting like:
* Phone number
* Pincode
* Date for which you want the appointment
* Dose information (Dose 1 or Dose 2)

After we enter these details the application will keep polling until the slot is found for the specified date. Once slot is found, it will AUTOMATICALLY open the Cowin Website in chrome browser and enter your phone number that you entered in the starting. You will recieve the OTP and you can take it up from there to book your slot after the login.
