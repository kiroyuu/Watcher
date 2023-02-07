/*
 * This is a arduino program which measures environmental data
 * and sends a measured average of these measurements to server.
 *
 * Joel Helkala
 *
 */
#include <SPI.h>
#include <WiFiNINA.h>
#include <ArduinoHttpClient.h>
#include <ArduinoJson.h>
#include "arduino_secrets.h"

#include <Wire.h>
#include <Adafruit_Sensor.h> 
#include <Adafruit_BME280.h>
#include "Adafruit_TSL2591.h"

#include "measurements.h"
#include <iostream>
#include <vector>
#include <chrono>

// Initialize sensors
Adafruit_BME280 bme; // BME sensor which has pressure, humidity and temperature
Adafruit_TSL2591 tsl = Adafruit_TSL2591(2591); // Luminosity sensor 

///////please enter your sensitive data in the Secret tab/arduino_secrets.h
char ssid[] = SECRET_SSID;        // your network SSID (name)
char pass[] = SECRET_PASS;    // your network password (use for WPA, or use as key for WEP)

int status = WL_IDLE_STATUS;
boolean api_success = false;

// server address:
char server[] = "84.249.42.194";
IPAddress server_ip(84,249,42,194);
int port = 8080;

// Initialize the WiFi client library
WiFiClient client;
HttpClient http = HttpClient(client, server, port);

//using namespace std::literals::chrono_literals;
typedef std::chrono::high_resolution_clock Time;

struct Timer {
  std::chrono::time_point<std::chrono::high_resolution_clock> lastConnectionTime, lastMeasurement;
  const std::chrono::milliseconds postingInterval = std::chrono::milliseconds(1000 * 60 * 10); // 10 minutes
  const std::chrono::milliseconds measurementInterval = std::chrono::milliseconds(1000 * 10);
  int counter = 0;

  Timer() {
    lastConnectionTime = std::chrono::high_resolution_clock::now();
    lastMeasurement = std::chrono::high_resolution_clock::now();
  }
  bool timeToPost() {
    /*std::chrono::duration<float> duration = std::chrono::high_resolution_clock::now() - lastConnectionTime;
    float durationMs = duration.count();
    Serial.println(durationMs);
    if (durationMs > postingInterval.count()) return true;
    return false;*/
    if (counter >= 10) return true;
    return false;
  }
  bool timeToMeasure() {
    std::chrono::duration<float> duration = Time::now() - lastMeasurement;
    
    float durationMs = duration.count() * 1000.0f;
    if (durationMs > measurementInterval.count()) return true;
    return false;
  }
  void resetConnectionTime() {
    //lastConnectionTime = std::chrono::high_resolution_clock::now();
    counter = 0;
  }
  void resetMeasurementTime() {
    lastMeasurement = std::chrono::high_resolution_clock::now();
  }
};

struct Data {
  int temp, humid, lumi;
};

void initPins() {
  bme.begin(0x76);
  tsl.begin();
  tsl.setGain(TSL2591_GAIN_MED);
  tsl.setTiming(TSL2591_INTEGRATIONTIME_300MS);
}

Watcher::Measurements measurements;
Timer timer;

void setup() {
  //Initialize serial and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  initPins(); 
  // check for the WiFi module:
  if (WiFi.status() == WL_NO_MODULE) {
    Serial.println("Communication with WiFi module failed!");
    // don't continue
    while (true);
  }

  // attempt to connect to WiFi network:
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    status = WiFi.begin(ssid, pass);

    // wait 10 seconds for connection:
    delay(10000);
  }
  // you're connected now, so print out the status:
  printWifiStatus();
  timer.resetMeasurementTime();
  timer.resetConnectionTime();
}


// MAIN LOOP
void loop() {
  // if there's incoming data from the net connection.
  // send it out the serial port.  This is for debugging
  // purposes only:
  while (client.available()) {
    char c = client.read();
    Serial.write(c);
  }

  // Take measurements and add them to list  
  double temp = bme.readTemperature();
  double humidity = bme.readHumidity();
  double luminosity = tsl.getLuminosity(TSL2591_VISIBLE);
  Watcher::measurement_t meas = {temp, humidity, luminosity};
  Serial.print("Temperature: ");
  Serial.println(meas.temperature);

  measurements.addMeasurement(meas);
  timer.counter++;
  
  
  if (timer.timeToPost()) {
    httpRequest();
  }

  delay(10000); // sleep for 10s to save power
}

void httpRequest() {
  // close any connection before send a new request.
  // This will free the socket on the NINA module
  Serial.println("making POST request");
  Watcher::measurement_t average = measurements.getAverage();
  measurements.clear();
  
  String contentType = "application/x-www-form-urlencoded";
  String postData ="temperature=";
         postData += average.temperature;
         postData += "&humidity=";
         postData +=  average.humidity;
         postData += "&luminosity=";
         postData += average.luminosity;
         postData += "&parent_id=";
         postData += 1;
  
  http.post("/api/v1/nodeData", contentType, postData);

  // read the status code and body of the response
  int statusCode = http.responseStatusCode();
  String response = http.responseBody();

  Serial.print("Status code: ");
  Serial.println(statusCode);
  Serial.print("Response: ");
  Serial.println(response);
  
  // note the time that the connection was made:
  timer.resetConnectionTime();
}

// Tulostetaan wifin tiedot
void printWifiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your board's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
}
