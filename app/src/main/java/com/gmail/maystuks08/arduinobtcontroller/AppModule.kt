package com.gmail.maystuks08.arduinobtcontroller

import android.content.Context
import com.gmail.maystuks08.devices.api.BluetoothController
import com.gmail.maystuks08.devices.impl.BluetoothControllerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object{
        @Provides
        @Singleton
        fun provideAppContext(@ApplicationContext appContext: Context): Context {
            return appContext
        }
    }

    @Binds
    @Singleton
    abstract fun bindBluetoothController(impl: BluetoothControllerImpl): BluetoothController

}