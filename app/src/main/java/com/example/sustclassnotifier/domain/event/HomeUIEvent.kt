package com.example.sustclassnotifier.domain.event

import com.example.sustclassnotifier.data.model.ClassDetails
import com.example.sustclassnotifier.data.model.Course
import com.example.sustclassnotifier.data.model.Post
import com.example.sustclassnotifier.navigation.Screen

sealed class HomeUIEvent {

    data class ClassStatusChange(val classDetails: ClassDetails, val activeStatus: Boolean): HomeUIEvent()
    data class AcceptCourseRequest(val course: Course): HomeUIEvent()

    data class DeleteCourseRequest(val course: Course): HomeUIEvent()

    data class DisplayCourse(val course: Course, val screen: Screen): HomeUIEvent()

    data object PostButtonClicked : HomeUIEvent()

    data class DeletePostSwipe(val post: Post): HomeUIEvent()
}