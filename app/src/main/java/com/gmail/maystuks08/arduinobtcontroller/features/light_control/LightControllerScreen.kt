package com.gmail.maystuks08.arduinobtcontroller.features.light_control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LightControllerScreen(viewModel: LightControlViewModel) {
    val viewState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Control light") },
                elevation = AppBarDefaults.TopAppBarElevation
            )
        },
        backgroundColor = MaterialTheme.colors.background,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            when (viewState.value) {
                LightControlState.Init -> {
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = { viewModel.setEvent(LightControlEvent.ScanDevices) }) {
                        Text(text = "Find bluetooth devices ")
                    }
                }
                LightControlState.Communication -> {

                }
                is LightControlState.ShowDeviceList -> {

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LightControllerScreenPreview() {

}