package com.example.sustclassnotifier.domain.event

import com.example.sustclassnotifier.data.model.ClassDetails
import com.example.sustclassnotifier.data.model.Event
import com.example.sustclassnotifier.navigation.Screen

sealed class CourseDetailsDisplayUIEvent {
    data class CourseDetailsDisplayTopBarBackButtonClicked(val screen: Screen): CourseDetailsDisplayUIEvent()

    data object ClassTitleClicked: CourseDetailsDisplayUIEvent()
    data class ClassDeleteSwipe(val classDetails: ClassDetails): CourseDetailsDisplayUIEvent()

    data object TermTestTitleClicked : CourseDetailsDisplayUIEvent()
    data class EventDeleteSwipe(val event: Event): CourseDetailsDisplayUIEvent()

    data object AssignmentTitleClicked: CourseDetailsDisplayUIEvent()

    data object ResourceTitleClicked: CourseDetailsDisplayUIEvent()
}