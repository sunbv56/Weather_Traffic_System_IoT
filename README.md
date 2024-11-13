
---

# IoT Project

This repository hosts an IoT project integrating multiple sensors, an ESP8266 microcontroller, and an Android application for real-time monitoring and data analysis. The project includes a Jupyter notebook for data processing, Arduino code for sensor data collection and ESP8266 communication, and an Android app for user interface.

## Project Structure

```
├── Final_IoT.ipynb                  # Jupyter notebook for data analysis
├── read_sensors/
│   └── read_sensors.ino             # Arduino code for sensor data collection
├── esp/
│   └── esp.ino                      # Arduino code for ESP8266 communication
├── MyApplication3/                  # Android Studio project for the mobile application
└── data/
    └── train.csv                    # Training dataset
```

### 1. `Final_IoT.ipynb`

This Jupyter notebook is used for data processing and analysis. It loads, cleans, and visualizes sensor data, enabling model building to predict or analyze sensor behaviors based on the historical data stored in `train.csv`.

### 2. `read_sensors/read_sensors.ino` (Arduino Code)

This Arduino script collects data from connected sensors. Designed to run on Arduino-compatible boards, it reads sensor values, processes the data, and prepares it for transmission. Key functionalities include:

- **Sensor Initialization**: Initializes the required sensors on startup.
- **Data Collection**: Reads values from each sensor and stores them for transmission.
- **Error Handling**: Ensures reliable data collection with error-handling mechanisms.

### 3. `esp/esp.ino` (ESP8266 Code)

This script enables Wi-Fi communication on an ESP8266 module. It connects to a specified Wi-Fi network, receives sensor data from `read_sensors.ino`, and can transmit this data to a cloud server or a local storage system. Core features include:

- **Wi-Fi Connection**: Connects to a Wi-Fi network for internet access.
- **Data Transmission**: Sends collected data to a server.
- **Offline Storage**: Retains data locally if Wi-Fi is unavailable, sending it once the connection is restored.

### 4. `MyApplication3` (Android Studio Project)

The Android application allows real-time monitoring of sensor data, displaying it in an intuitive dashboard. Users can view historical data, set custom alerts, and configure notifications. Key features:

- **Real-Time Data Display**: Displays live sensor data.
- **Data Visualization**: Provides charts and historical data views.
- **User Alerts**: Allows users to set threshold alerts for various sensor values.

### 5. `data/train.csv`

This CSV file contains pre-collected sensor data for model training in `Final_IoT.ipynb`. It serves as the training set for developing predictive models, enabling insights such as anomaly detection and behavior forecasting.

## Getting Started

### Prerequisites

- [Arduino IDE](https://www.arduino.cc/en/software) for uploading code to Arduino and ESP8266.
- [Android Studio](https://developer.android.com/studio) for running the Android app.
- Python and Jupyter Notebook for data processing.
- Required Python libraries listed in `requirements.txt`.

### Installation

1. **Arduino Code**:
   - Connect your Arduino and upload `read_sensors.ino` to initialize sensor readings.
   - Connect your ESP8266 and upload `esp.ino` to establish Wi-Fi communication.

2. **Android Application**:
   - Open the `MyApplication3` project in Android Studio, connect your Android device, and run the application to start receiving data.

3. **Data Processing**:
   - Ensure `train.csv` is located in the `data/` directory.
   - Open `Final_IoT.ipynb` in Jupyter Notebook for data analysis.

## Usage

- **Sensor Collection**: Deploy `read_sensors.ino` on the Arduino board to start reading sensor data.
- **ESP8266 Communication**: Use `esp.ino` to transmit data from the Arduino board to the application or a server.
- **Android Application**: Monitor real-time data, view historical trends, and set alerts on your mobile device.
- **Data Analysis**: Run `Final_IoT.ipynb` for insights and to build predictive models based on collected data.

## Video Demonstration

Watch the video demonstration for this project on YouTube: [IoT Project Video](https://www.youtube.com/watch?v=fEdR2XuSrMw&ab_channel=Henry)

## Contributing

We welcome contributions! Feel free to open issues and submit pull requests for bug fixes, feature enhancements, or optimizations.

---
