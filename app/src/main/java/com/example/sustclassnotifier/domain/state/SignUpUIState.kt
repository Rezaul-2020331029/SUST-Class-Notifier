package com.example.sustclassnotifier.domain.state

import com.example.sustclassnotifier.core.Constants.ROLES

data class SignUpUIState (
    val firstName: String = "",
    val lastName: String = "",
    val role: String = ROLES[0],
    val department: String = "",
    val email: String = "",
    val password: String = "",

    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val departmentError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
)