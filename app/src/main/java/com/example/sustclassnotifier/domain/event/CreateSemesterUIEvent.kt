package com.example.sustclassnotifier.domain.event

import com.example.sustclassnotifier.data.model.ClassDetails
import com.example.sustclassnotifier.data.model.Course
import com.example.sustclassnotifier.navigation.Screen

sealed class CreateSemesterUIEvent {
    data object CreateSemesterFABClick: CreateSemesterUIEvent()

    data class EditCourseUIEvent(
        val course: Course,
        val classDetailsList: List<ClassDetails>
    ): CreateSemesterUIEvent()

    data class DeleteCourseSwipe(val course: Course) : CreateSemesterUIEvent()

    data class DisplayCourseSwipe(val course: Course, val screen: Screen): CreateSemesterUIEvent()
}