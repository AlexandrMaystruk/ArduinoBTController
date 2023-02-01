package com.gmail.maystuks08.devices.api

import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {

    suspend fun discoverDevices(): StateFlow<Set<BTDevice>>

    suspend fun stopDiscoverDevices()

    suspend fun connect(device: BTDevice)

    suspend fun sendCommand(command: Command)

    suspend fun disconnect(device: BTDevice)

}

