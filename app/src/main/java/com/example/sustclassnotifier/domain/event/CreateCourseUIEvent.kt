package com.example.sustclassnotifier.domain.event

import com.example.sustclassnotifier.data.model.ClassDetails

sealed class CreateCourseUIEvent {

    data class CourseCodeChanged(val courseCode: String): CreateCourseUIEvent()
    data class CourseTitleChanged(val courseTitle: String): CreateCourseUIEvent()
    data class CourseCreditChanged(val courseCredit: String): CreateCourseUIEvent()
    data class CourseSemesterChanged(val courseSemester: String): CreateCourseUIEvent()

    data object BackButtonClick: CreateCourseUIEvent()
    data object CreateClassButtonClick: CreateCourseUIEvent()

    data object SearchTeacherButtonClick : CreateCourseUIEvent()

    data class SearchUIRequestButtonClick(
        val courseTeacherEmail: String
    ): CreateCourseUIEvent()

    data class ClassDetailsDeleteSwipe(val classDetails: ClassDetails): CreateCourseUIEvent()

    data object CreateClick : CreateCourseUIEvent()
}