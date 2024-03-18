package com.example.sustclassnotifier.domain.event

sealed class PostUIEvent {

    data class CourseCodeChanged(val courseCode: String) : PostUIEvent()
    data class DescriptionChanged(val description: String) : PostUIEvent()
    data object DiscardButtonClicked : PostUIEvent()
    data class PostButtonClicked(
        val timestamp: Long,
        val creator: String,
        val firstName: String,
        val lastName: String,
    ) : PostUIEvent()
}