package com.gmail.maystuks08.devices.impl

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.gmail.maystuks08.devices.api.BTDevice
import com.gmail.maystuks08.devices.api.BluetoothController
import com.gmail.maystuks08.devices.api.Command
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@SuppressLint("MissingPermission")
class BluetoothControllerImpl @Inject constructor(private val context: Context) : BluetoothController {

    private val btManager = context.getSystemService(BluetoothManager::class.java)
    private val devices = mutableSetOf<BTDevice>()
    private val devicesFlow = MutableStateFlow(devices)
    val bluetoothDevices: StateFlow<Set<BTDevice>> get() = devicesFlow

    private val actionFoundReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device =
                        intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)!!
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                    devices.add(BTDevice(deviceName, deviceHardwareAddress))
                    devicesFlow.value = devices.toMutableSet()
                }
            }
        }
    }

    override suspend fun discoverDevices(): StateFlow<Set<BTDevice>> {
        Log.e(TAG, "start discover bt devices")
        val bluetoothAdapter =
            btManager.adapter ?: throw RuntimeException("Device doesn't support Bluetooth")
        val pairedDevices = bluetoothAdapter.bondedDevices
        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address
            Log.d(TAG, "found bt device $deviceName $deviceHardwareAddress")
            devices.add(BTDevice(deviceName, deviceHardwareAddress))
        }?.run { devicesFlow.emit(devices.toMutableSet()) }

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(actionFoundReceiver, filter)

        return devicesFlow
    }

    override suspend fun stopDiscoverDevices() {
        runCatching {
            context.unregisterReceiver(actionFoundReceiver)
        }.onFailure {
            Log.e(TAG, "stop discover bt devices error: ${it.message}")
        }
    }

    override suspend fun connect(device: BTDevice) {
    }

    override suspend fun sendCommand(command: Command) {

    }

    override suspend fun disconnect(device: BTDevice) {

    }

    init {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        bluetoothAdapter.cancelDiscovery()
    }


    fun createSocket(bluetoothAdapter: BluetoothAdapter, address: String?): BluetoothSocket? {
        val bluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
        val uuid = bluetoothDevice.uuids[0].uuid
        return bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid)
    }

    companion object {
        const val TAG = "BluetoothControllerImpl"
    }

}