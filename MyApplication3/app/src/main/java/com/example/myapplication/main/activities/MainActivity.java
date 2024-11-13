package com.example.myapplication.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.main.models.WeatherData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;


import com.example.myapplication.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tempText = findViewById(R.id.temp);
        TextView humText = findViewById(R.id.hum);
        TextView airText = findViewById(R.id.air);
        TextView weatherNowText = findViewById(R.id.weatherNow);
        TextView weatherPredText = findViewById(R.id.weatherPred);
        TextView trafficPredText = findViewById(R.id.trafficPred);
        TextView trafficNowText = findViewById(R.id.trafficNow);
        databaseReference = FirebaseDatabase.getInstance().getReference("");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    WeatherData weatherData = snapshot.getValue(WeatherData.class);

                    if (weatherData != null) {
                        // Access data using getters from the model class
                        double rainPred = weatherData.getRain_pred();
                        int weatherCode = weatherData.getWeather_code();
                        double los = weatherData.getLos();
                        double temp = weatherData.getTemp();
                        double hum = weatherData.getHum();
                        int rain = weatherData.getRain();
                        double light = weatherData.getLight();
                        double ppm = weatherData.getPpm();
                        if (ppm>1000){
                            trafficNowText.setText("Đường đang nhiều xe");
                        }
                        else{
                            trafficNowText.setText("Không có kẹt xe");
                        }
                        if(rain==0){
                            weatherNowText.setText("Khô ráo");
                        }
                        else{
                            weatherNowText.setText("Trời đang có mưa");
                        }
                        tempText.setText(evaluateTemp(temp));
                        humText.setText(evaluateHum(hum));
                        airText.setText(evaluateAir(ppm));
                        weatherPredText.setText(evaluateRainPred(rainPred));
                        trafficPredText.setText(evaluateTrafficPred(los));
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FirebaseData", "Failed to read value.", databaseError.toException());
            }
        });

        Button buttonShowChart = findViewById(R.id.btnShowChart);
        buttonShowChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowChart.class);
                startActivity(intent);
            }
        });

    }
    public static String evaluateTemp(double temperature) {
        String result;
        if (temperature >= 37.0) {
            result="Rất nóng";
        } else if (temperature >= 32.0) {
            result= "Nóng";
        } else if (temperature >= 27.0) {
            result="Ấm";
        } else if (temperature >= 22.0) {
            result="Dễ chịu";
        } else if (temperature >= 12.0) {
            result= "Lạnh";
        } else {
            result= "Rất lạnh";
        }
        return "Nhiệt độ: "+result + " (" +temperature+"\u2103)";
    }
    public static String evaluateHum(double humidity) {
        String result="";
        if (humidity >= 70.0) {
            result = "Rất ẩm";
        } else if (humidity >= 60.0) {
            result = "Tương đối ẩm";
        } else if (humidity >= 50.0) {
            result = "Ổn định";
        } else if (humidity >= 40.0) {
            result = "Khá khô";
        } else {
            result = "Rất khô";
        }
        return "Độ ẩm: " + result + " (" + humidity + "%)";
    }
    public static String evaluateAir(double ppm) {
        String result="";
        if (ppm >= 1000.0) {
            result = "Rất xấu";
        } else if (ppm >= 500.0) {
            result = "Xấu";
        } else if (ppm >= 200.0) {
            result = "Tạm ổn";
        } else if (ppm >= 100.0) {
            result = "Tốt";
        } else {
            result = "Rất tốt";
        }
        return "Chất lượng không khí: " + result + " (" + ppm + " ppm)";
    }
    public static String evaluateRainPred(double rainfall) {
        String result="";

        if (rainfall <=0) {
            result = "Khô ráo";
        }
        else if (rainfall < 3.0/12) {
            result = "Mưa nhỏ";
        } else if (rainfall < 25.0/12) {
            result = "Mưa vừa";
        } else if (rainfall < 50/12) {
            result = "Mưa to";
        } else {
            result = "Mưa rất to";
        }

        return result;
    }
    public static String evaluateTrafficPred(double los){
        String result="";
        long round = (long)Math.floor(los);
        if (round == 0){
            result = "Rất tốt, đường thông thoáng.";
        }
        else if (round==1){
            result = "Tốt, tắc nghẽn ít.";
        }
        else if (round==2){
            result = "Ổn định, có một số ô nghẽn, tốc độ giảm nhẹ.";
        }else if (round==3){
            result = "Tắc nghẽn, tốc độ giảm đáng kể";
        }else if (round==4){
            result = "Kém, tắc nghẽn nhiều, tốc độ rất thấp";
        }else if (round==5){
            result = "Rất kém, tắc nghẽn nhiều, tốc độ rất thấp";
        }
        return result;
    }






}
