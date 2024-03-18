package com.example.sustclassnotifier.domain.state

import com.example.sustclassnotifier.core.Constants.SEMESTERS

data class CreateCourseUIState (
    val courseCode: String = "",
    val courseTitle: String = "",
    val courseCredit: Double = 0.0,
    val courseSemester: String = SEMESTERS[0],
    val courseTeacherEmail: String = "",

    val courseCodeError: String? = null,
    val courseTitleError: String? = null,
    val courseCreditError: String? = null,
    val courseTeacherEmailError: String? = null,
    val createClassError: String? = null
)