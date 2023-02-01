package com.gmail.maystuks08.arduinobtcontroller.features.light_control

import androidx.lifecycle.viewModelScope
import com.gmail.maystuks08.arduinobtcontroller.core.BaseViewModel
import com.gmail.maystuks08.arduinobtcontroller.core.UiEffect
import com.gmail.maystuks08.arduinobtcontroller.core.UiEvent
import com.gmail.maystuks08.arduinobtcontroller.core.UiState
import com.gmail.maystuks08.devices.api.BTDevice
import com.gmail.maystuks08.devices.api.BluetoothController
import com.gmail.maystuks08.devices.api.Command
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LightControlState : UiState {

    object Init : LightControlState

    data class ShowDeviceList(val devices: List<BTDevice> = emptyList()) : LightControlState

    object Communication : LightControlState

}

sealed interface LightControlEvent : UiEvent {

    object ScanDevices : LightControlEvent
    data class Connect(val device: BTDevice) : LightControlEvent
    data class SendCommand(val command: Command) : LightControlEvent
    data class Disconnect(val device: BTDevice) : LightControlEvent
}

sealed interface LightControlEffect : UiEffect {
    object ShowError : LightControlEffect
}

@HiltViewModel
class LightControlViewModel @Inject constructor(
    private val bluetoothController: BluetoothController
) : BaseViewModel<LightControlState, LightControlEvent>() {

    override fun createInitialState() = LightControlState.Init

    override fun handleUiEvent(event: LightControlEvent) {
        viewModelScope.launch {
            when (event) {
                LightControlEvent.ScanDevices -> scanDevices()
                is LightControlEvent.Connect -> connect(event.device)
                is LightControlEvent.SendCommand -> sendCommand(event.command)
                is LightControlEvent.Disconnect -> disconnect(event.device)
            }
        }
    }

    private suspend fun scanDevices() {
        bluetoothController.discoverDevices()
    }

    private suspend fun connect(device: BTDevice) {
        bluetoothController.connect(device)
    }

    private suspend fun sendCommand(command: Command) {
        bluetoothController.discoverDevices()
    }

    private suspend fun disconnect(device: BTDevice) {
        bluetoothController.disconnect(device)
    }


}