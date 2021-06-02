# cowin-poller
Poll Cowin APIs to check if the vaccine is available for a given date.
The application will ask for some basic details in the starting like:
- Phone number
- Pincode
- Date for which you want the appointment
- Dose information (Dose 1 or Dose 2)
- Vaccine Type (COVISHIELD or COVAXIN or ANY)

After we enter these details the application will keep polling until the slot is found for the specified date. Once slot is found, it will AUTOMATICALLY open the Cowin Website in chrome browser and enter your phone number that you entered in the starting. You will recieve the OTP and you can take it up from there to book your slot after the login.

# How To Use
- Install Java 11 [(if not installed)](https://jdk.java.net/archive/)  *(For detailed step by step guide to install java, go to Java Installation Section)*
- Go to the runnables directory and download the files - `chromedriver.exe` and `cowinpoller.jar`. Place them in the same directory.
- Open cmd/terminal in the directory where you have placed the above files.
- Use the following command to run the application - `java -jar cowinpoller.jar`

# How to build
- Install Gradle [(if not installed)](https://gradle.org/next-steps/?version=7.0.2&format=all) 
- Run the command - `gradle clean build`

# Java Installation
- Go to OpenJDK Archive [download page](https://jdk.java.net/archive/)
- Scroll down a little bit to find the version 11.0.2. OpenJDK is distributed in only zip or tar.gz file.
- Extract the downloaded zip file to a directory
- Set the path of JAVA_HOME path variable to this directory

