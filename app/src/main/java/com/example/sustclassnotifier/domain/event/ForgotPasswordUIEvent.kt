package com.example.sustclassnotifier.domain.event

sealed class ForgotPasswordUIEvent {

    data class EmailChanged(val email: String): ForgotPasswordUIEvent()

    data object ForgotPasswordButtonClick: ForgotPasswordUIEvent()
}