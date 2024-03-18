package com.example.sustclassnotifier.domain.event

sealed class SignInUIEvent {
    data class EmailChanged(val email: String): SignInUIEvent()
    data class PasswordChanged(val password: String): SignInUIEvent()

    data object SignInButtonClicked: SignInUIEvent()
}