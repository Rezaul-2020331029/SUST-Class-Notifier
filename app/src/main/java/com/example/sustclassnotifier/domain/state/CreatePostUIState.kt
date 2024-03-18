package com.example.sustclassnotifier.domain.state

data class CreatePostUIState(
    val courseCode: String = "",
    val description: String = "",

    val courseCodeError: String? = null,
    val descriptionError: String? = null,
)
