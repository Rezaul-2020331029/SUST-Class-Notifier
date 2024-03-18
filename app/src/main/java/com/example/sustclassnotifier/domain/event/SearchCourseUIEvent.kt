package com.example.sustclassnotifier.domain.event

sealed class SearchCourseUIEvent {

    data class EnrollButtonClicked(val courseId: String, val email: String): SearchCourseUIEvent()
}