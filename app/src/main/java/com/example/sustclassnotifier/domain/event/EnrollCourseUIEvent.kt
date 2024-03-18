package com.example.sustclassnotifier.domain.event

import com.example.sustclassnotifier.data.model.Course
import com.example.sustclassnotifier.navigation.Screen

sealed class EnrollCourseUIEvent {
    data class DisplayCourseSwipe(val course: Course, val screen: Screen): EnrollCourseUIEvent()

    data class LeaveCourseSwipe(val courseId: String): EnrollCourseUIEvent()
}