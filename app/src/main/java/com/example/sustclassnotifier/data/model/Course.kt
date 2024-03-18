package com.example.sustclassnotifier.data.model

import com.example.sustclassnotifier.core.Constants.SEMESTERS
import com.example.sustclassnotifier.strings.COURSE_CLASSES
import com.example.sustclassnotifier.strings.COURSE_CODE
import com.example.sustclassnotifier.strings.COURSE_CREATOR
import com.example.sustclassnotifier.strings.COURSE_CREDIT
import com.example.sustclassnotifier.strings.COURSE_DEPARTMENT
import com.example.sustclassnotifier.strings.COURSE_SEMESTER
import com.example.sustclassnotifier.strings.COURSE_TEACHER
import com.example.sustclassnotifier.strings.COURSE_TITLE
import com.example.sustclassnotifier.strings.ENROLLED_STUDENTS
import com.example.sustclassnotifier.strings.PENDING_STATUS

data class Course(
    val courseCreator: String = "",
    val courseDepartment: String = "",
    val courseSemester: String = SEMESTERS[0],
    val courseCode: String = "",
    val courseTitle: String = "",
    val courseCredit: Double = 0.0,
    val courseTeacher: String = "",
    val pendingStatus: Boolean = true,
    val courseClasses: List<String> = emptyList(),
    val enrolledStudents: List<String> = emptyList(),
) {
    fun toMap(): Map<String, Any> = mapOf(
        COURSE_CREATOR to courseCreator,
        COURSE_DEPARTMENT to courseDepartment,
        COURSE_SEMESTER to courseSemester,
        COURSE_CODE to courseCode,
        COURSE_TITLE to courseTitle,
        COURSE_CREDIT to courseCredit,
        COURSE_TEACHER to courseTeacher,
        PENDING_STATUS to pendingStatus,
        COURSE_CLASSES to courseClasses,
        ENROLLED_STUDENTS to enrolledStudents
    )

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$courseCode$courseDepartment$courseTitle",
            courseCode,
            courseDepartment,
            courseTitle

        )

        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
