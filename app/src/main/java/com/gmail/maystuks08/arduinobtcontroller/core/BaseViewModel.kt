package com.gmail.maystuks08.arduinobtcontroller.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State : UiState, Event: UiEvent> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }

    abstract fun createInitialState(): State

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected val currentState: State
        get() = uiState.value

    inline fun <reified State> castCurrentState() = uiState.value as State

    abstract fun handleUiEvent(event: Event)

    /**
     * Set new State
     */
    protected fun setState(builder: State.() -> State) {
        val stateValue = builder(currentState)
        _uiState.value = stateValue
    }

    /**
     * Set new Ui Event
     */
    fun setEvent(event: Event) {
        handleUiEvent(event)
    }

}