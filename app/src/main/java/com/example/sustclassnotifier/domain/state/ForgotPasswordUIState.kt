package com.example.sustclassnotifier.domain.state

data class ForgotPasswordUIState (
    val email: String = "",

    val emailError: String? = null
)