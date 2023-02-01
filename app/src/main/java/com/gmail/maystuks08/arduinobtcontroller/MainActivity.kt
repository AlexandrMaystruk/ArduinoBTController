package com.gmail.maystuks08.arduinobtcontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.maystuks08.arduinobtcontroller.features.light_control.LightControlViewModel
import com.gmail.maystuks08.arduinobtcontroller.features.light_control.LightControllerScreen
import com.gmail.maystuks08.arduinobtcontroller.ui.theme.ArduinoBTControllerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArduinoBTControllerTheme {
                val viewModel = hiltViewModel<LightControlViewModel>()
                LightControllerScreen(viewModel)
            }
        }
    }
}