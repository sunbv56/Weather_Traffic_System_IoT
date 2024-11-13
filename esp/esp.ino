// Khai báo các thư viện cần thiết
#include <ArduinoJson.h>
#include <SoftwareSerial.h>
#include <ESP8266WiFi.h>

String apiKey = "G7A742OZT1ZEB73O";  //  Enter your Write API key from ThingSpeak

const char *ssid = "5k/1gb-laptop";  // replace with your wifi ssid and wpa2 key
const char *pass = "12345679";
const char *server = "api.thingspeak.com";

WiFiClient client;
// Tạo một đối tượng JsonDocument có dung lượng 100 byte
StaticJsonDocument<300> doc;

#define RX_PIN 4  // Chân RX của EspSoftwareSerial
#define TX_PIN 5  // Chân TX của EspSoftwareSerial  
// Tạo một đối tượng SoftwareSerial
SoftwareSerial mySerial(RX_PIN, TX_PIN);  // RX, TX

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);

  // Khởi tạo giao tiếp nối tiếp với tốc độ baud là 9600
  Serial.begin(9600);
  // Khởi tạo cổng nối tiếp ảo
  mySerial.begin(9600);
  //Wifi
  Serial.println("Connecting to ");
  Serial.println(ssid);


  WiFi.begin(ssid, pass);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
}

void loop() {
  digitalWrite(LED_BUILTIN, HIGH);
  // Kiểm tra xem có dữ liệu nào sẵn sàng trên cổng nối tiếp ảo hay không
  if (mySerial.available()) {
    // Đọc một dòng từ cổng nối tiếp ảo
    String line = mySerial.readStringUntil('\n');

    // Chuyển đổi chuỗi JSON thành đối tượng JsonDocument
    DeserializationError error = deserializeJson(doc, line);

    // Kiểm tra xem có lỗi nào trong quá trình chuyển đổi hay không
    if (error) {
      Serial.print("deserializeJson() failed: ");
      Serial.println(error.c_str());
      return;
    }

    // Lấy giá trị nhiệt độ và độ ẩm từ đối tượng JsonDocument
    float temperature = doc["temperature"];
    float humidity = doc["humidity"];
    float rainDigitalValue = doc["rainDigitalValue"];
    float readLight = doc["readLight"];
    float correctedPPM = doc["correctedPPM"];

    if (client.connect(server, 80))  //   "184.106.153.149" or api.thingspeak.com
    {

      String postStr = apiKey;
      postStr += "&field1=";
      postStr += String(temperature);
      postStr += "&field2=";
      postStr += String(humidity);
      postStr += "&field3=";
      postStr += String(rainDigitalValue);
      postStr += "&field4=";
      postStr += String(readLight);
      postStr += "&field5=";
      postStr += String(correctedPPM);

      client.print("POST /update HTTP/1.1\n");
      client.print("Host: api.thingspeak.com\n");
      client.print("Connection: close\n");
      client.print("X-THINGSPEAKAPIKEY: " + apiKey + "\n");
      client.print("Content-Type: application/x-www-form-urlencoded\n");
      client.print("Content-Length: ");
      client.print(postStr.length());
      client.print("\n\n");
      client.print(postStr);

      // In các giá trị ra màn hình nối tiếp
      Serial.print("Temperature: ");
      Serial.print(temperature);
      Serial.println(" C");
      Serial.print("Humidity: ");
      Serial.print(humidity);
      Serial.println(" %");
      Serial.print("Rain digital: ");
      Serial.print(rainDigitalValue);
      Serial.println(" ppm");
      Serial.print("Light analog: ");
      Serial.println(readLight);
      Serial.print("Corrected PPM: ");
      Serial.print(correctedPPM);
      Serial.println("\nSend to Thingspeak.\n");
      digitalWrite(LED_BUILTIN, LOW);
      delay(2000);
      digitalWrite(LED_BUILTIN, HIGH);
    }
    client.stop();

    Serial.println("Waiting...");
  }
}
