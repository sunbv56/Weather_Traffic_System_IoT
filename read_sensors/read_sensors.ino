// Khai báo thư viện cần thiết
#include <ArduinoJson.h>
// Khai báo thư viện SoftwareSerial
#include <SoftwareSerial.h>

#include <MQ135.h>
#include <DHT.h>

#define rainAnalogPin A0
#define rainDigitalPin 2
#define LIGHTSENSORPIN A1
#define PIN_MQ135 A2   // MQ135 Analog Input Pin
#define DHTPIN 4       // DHT Digital Input Pin
#define DHTTYPE DHT11  // DHT11 or DHT22, depends on your sensor

MQ135 mq135_sensor(PIN_MQ135);
DHT dht(DHTPIN, DHTTYPE);
// Tạo một đối tượng JsonDocument có dung lượng 100 byte
StaticJsonDocument<300> doc;

#define RX_PIN 10 // Chân RX của cổng nối tiếp mềm
#define TX_PIN 11 // Chân TX của cổng nối tiếp mềm
// Tạo một đối tượng SoftwareSerial
SoftwareSerial mySerial(RX_PIN, TX_PIN); // Tạo một đối tượng cổng nối tiếp mềm

float temperature,temperatureF, humidity;  // Temp and Humid floats, will be measured by the DHT

void setup() {
  pinMode(rainAnalogPin, INPUT);
  pinMode(rainDigitalPin, INPUT);
  pinMode(LIGHTSENSORPIN, INPUT);
  dht.begin();
  // Khởi tạo giao tiếp nối tiếp với tốc độ baud là 9600
  Serial.begin(9600);
  // Khởi tạo cổng nối tiếp ảo
  mySerial.begin(9600);
}

void loop() {
  //---------------------------RAIN---------------------------
  // read the input on analog pin 0:
  int rainAnalogValue = analogRead(rainAnalogPin);
  Serial.print("Rain analog: ");
  Serial.print(rainAnalogValue);
  // read the input on digital pin 2:
  int rainDigitalValue = digitalRead(rainDigitalPin);
  Serial.print(", Rain digital: ");
  Serial.println(rainDigitalValue);
  //digital output
  Serial.print("Weather: ");
  if (rainDigitalValue == LOW)  //Low means rain detected; In some case vise versa
  {
    Serial.println("Rain Detected");
  } else {
    Serial.println("No Rain Detected");
  }
  //---------------------------TEMT6000---------------------------
  float readLight = analogRead(LIGHTSENSORPIN);  //Read light level
  float square_ratio = readLight / 1023.0;       //Get percent of maximum value (1023)
  square_ratio = pow(square_ratio, 2.0);
  Serial.print("Light analog: ");
  Serial.print(readLight);
  Serial.print(", Percent light: ");
  Serial.println(square_ratio);

  //---------------------------DHT11---------------------------
  humidity = dht.readHumidity(); 
  temperature = dht.readTemperature(false);
  temperatureF = dht.readTemperature(true);
  Serial.print("Temperature (C): (");
  Serial.print(temperature);
  Serial.print(" C, ");
  Serial.print(temperatureF);
  Serial.print(" F), Humidity: ");
  Serial.print(humidity);
  Serial.println("%");
  // Check if any reads failed and exit early (to try again).
  if (isnan(humidity) || isnan(temperature) || isnan(temperatureF)) {
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
  }

  //---------------------------MQ135---------------------------
  float rzero = mq135_sensor.getRZero();
  float correctedRZero = mq135_sensor.getCorrectedRZero(temperature, humidity);
  float resistance = mq135_sensor.getResistance();
  float ppm = mq135_sensor.getPPM();
  float correctedPPM = mq135_sensor.getCorrectedPPM(temperature, humidity);

  Serial.print("MQ135 RZero: ");
  Serial.print(rzero);
  Serial.print(", Corrected RZero: ");
  Serial.print(correctedRZero);
  Serial.print(", Resistance: ");
  Serial.print(resistance);
  Serial.print(", PPM: ");
  Serial.print(ppm);
  Serial.print("ppm");
  Serial.print(", Corrected PPM: ");
  Serial.print(correctedPPM);
  Serial.println("ppm");


  

  // Gán giá trị nhiệt độ và độ ẩm vào đối tượng JsonDocument
  doc["temperature"] = temperature;
  doc["humidity"] = humidity;
  doc["rainDigitalValue"] = rainDigitalValue;
  doc["readLight"] = readLight;
  doc["correctedPPM"] = correctedPPM;
  serializeJson(doc, mySerial);
  mySerial.println();
  serializeJson(doc, Serial);
  Serial.println();
  doc.clear();

  Serial.println("----------------------------------------------------------------\n");
  delay(15000);
  Serial.println("\n----------------------------------------------------------------");
}
