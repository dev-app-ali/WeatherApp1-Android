package com.example.weatherapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ui.theme.WeatherAPPTheme


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel ::class.java]
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            WeatherAPPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


                    Box (modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding))
                    {
                        WeatherScreen(weatherViewModel)


                        }

                }
            }
        }
    }
}

