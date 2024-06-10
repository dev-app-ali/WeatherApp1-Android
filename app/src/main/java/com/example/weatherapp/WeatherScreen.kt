package com.example.weatherapp


import android.annotation.SuppressLint
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.WeatherModel
import okhttp3.internal.wait
import java.lang.annotation.Native
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.math.roundToInt


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    var city by remember {
        mutableStateOf("")
    }
    val scrollState = rememberScrollState()
    val weatherResult = viewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                placeholder = { Text("Search for Location") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedPlaceholderColor = Color.DarkGray,
                    unfocusedPlaceholderColor = Color.DarkGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequester),
                shape = RoundedCornerShape(80.dp),
                leadingIcon = {

                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = "Search icon",
                        tint = Color.White
                    )


                },
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.getData(city)
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }) {

                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search icon",
                            tint = Color.White
                        )
                    }

                },
                maxLines = 1
            )

            when (val result = weatherResult.value) {
                is NetworkResponse.Success -> {
                    WeatherDetial(data = result.data)
                }

                is NetworkResponse.Loading -> {


                    LinearProgressIndicator()

                }

                is NetworkResponse.Error -> {
                    Text(text = result.message)
                }

                null -> {}
            }

        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherDetial(data: WeatherModel) {


    val tempCelsius: Int = try {
        data.current.temp_c.toFloat().roundToInt()
    } catch (e: NumberFormatException) {
        0
    }


    val windSpeed: Int = try {
        data.current.wind_kph.toFloat().roundToInt()
    } catch (e: NumberFormatException) {
        0
    }


    val uvIndex: Int = try {
        data.current.uv.toFloat().roundToInt()
    } catch (e: NumberFormatException) {
        0
    }
    val feelsLike: Int = try {
        data.current.feelslike_c.toFloat().roundToInt()
    } catch (e: NumberFormatException) {
        0
    }
    val visibility: Int = try {
        data.current.vis_km.toFloat().roundToInt()
    } catch (e: NumberFormatException) {
        0
    }
    val gusts: Int = try {
        data.current.gust_kph.toFloat().roundToInt()
    } catch (e: NumberFormatException) {
        0
    }
    val heatIndex: Int = try {
        data.current.heatindex_c.toFloat().roundToInt()
    } catch (e: NumberFormatException) {
        0
    }
    val pressure: Int = try {
        data.current.pressure_mb.toFloat().roundToInt()
    } catch (e: NumberFormatException) {
        0
    }
    val precipitation: Int = try {
        data.current.precip_mm.toFloat().roundToInt()
    } catch (e: NumberFormatException) {
        0
    }





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {

            Image(
                imageVector = Icons.Rounded.LocationOn,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = data.location.name,
                fontSize = 34.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = data.location.country,
                fontSize = 20.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold
            )


        }


        Row(


        ) {
            Row(
                modifier = Modifier.padding(start = 20.dp)
            ) {

                Text(
                    text = "$tempCelsius",
                    fontSize = 120.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Text(
                    text = "°C",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                AsyncImage(
                    modifier = Modifier.size(200.dp),
                    model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
                    contentDescription = null
                )
            }


        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Local Time: ", fontWeight = FontWeight.SemiBold, color = Color.White)
            Text(
                text = data.location.localtime, fontSize = 15.sp,
                fontWeight = FontWeight.Bold, style = TextStyle(
                    textDecoration = TextDecoration.Underline
                )
            )

        }
        
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = data.current.condition.text,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )


        }

        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .padding(horizontal = 15.dp),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Blue.copy(alpha = .2f)),
            border = BorderStroke(4.dp, color = Color.Blue.copy(alpha = .1f))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherCardDetail(key = "Humidity", value = "${data.current.humidity}%")
                    WeatherCardDetail(key = "Wind Speed", value = "$windSpeed kmph")

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherCardDetail(key = "Feels Like", value = "${feelsLike} °C")
                    WeatherCardDetail(key = "Visibility", value = "${visibility} km")

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherCardDetail(key = "UV Index", value = "$uvIndex")
                    WeatherCardDetail(
                        key = " Precipitation", value = "${precipitation} mm"
                    )

                }

            }

        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp)
                    .weight(1f), border = BorderStroke(4.dp, color = Color.Blue.copy(alpha = .1f)),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue.copy(alpha = .2f))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(text = "Gusts", fontWeight = FontWeight.SemiBold, color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "$gusts kmph",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp)
                    .weight(1f), border = BorderStroke(4.dp, color = Color.Blue.copy(alpha = .1f)),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue.copy(alpha = .2f))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(text = "Heat Index", fontWeight = FontWeight.SemiBold, color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = " $heatIndex °C",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp)
                    .weight(1f), border = BorderStroke(4.dp, color = Color.Blue.copy(alpha = .1f)),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue.copy(alpha = .2f))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Wind Degree",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "${data.current.wind_degree}° ",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp)
                    .weight(1f), border = BorderStroke(4.dp, color = Color.Blue.copy(alpha = .1f)),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue.copy(alpha = .2f))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(text = "Pressure", fontWeight = FontWeight.SemiBold, color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = " $pressure hPa",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Last Updated:  ", fontWeight = FontWeight.SemiBold, color = Color.White)
            Text(
                text = data.current.last_updated, fontSize = 15.sp,
                fontWeight = FontWeight.Bold, style = TextStyle(
                    textDecoration = TextDecoration.Underline
                )
            )

        }
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Project for learning",
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(
                text = "code.app.ali@gmail.com", fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }


}

@Composable
fun WeatherCardDetail(key: String, value: String) {
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.White)
        Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }

}
