package com.example.sustclassnotifier.core

import com.example.sustclassnotifier.strings.CLASS_REPRESENTATIVE
import com.example.sustclassnotifier.strings.FIRST_YEAR_FIRST_SEMESTER
import com.example.sustclassnotifier.strings.FIRST_YEAR_SECOND_SEMESTER
import com.example.sustclassnotifier.strings.FOURTH_YEAR_FIRST_SEMESTER
import com.example.sustclassnotifier.strings.FOURTH_YEAR_SECOND_SEMESTER
import com.example.sustclassnotifier.strings.FRIDAY
import com.example.sustclassnotifier.strings.MONDAY
import com.example.sustclassnotifier.strings.SATURDAY
import com.example.sustclassnotifier.strings.SECOND_YEAR_FIRST_SEMESTER
import com.example.sustclassnotifier.strings.SECOND_YEAR_SECOND_SEMESTER
import com.example.sustclassnotifier.strings.STUDENT
import com.example.sustclassnotifier.strings.SUNDAY
import com.example.sustclassnotifier.strings.TEACHER
import com.example.sustclassnotifier.strings.THIRD_YEAR_FIRST_SEMESTER
import com.example.sustclassnotifier.strings.THIRD_YEAR_SECOND_SEMESTER
import com.example.sustclassnotifier.strings.THURSDAY
import com.example.sustclassnotifier.strings.TUESDAY
import com.example.sustclassnotifier.strings.WEDNESDAY

object Constants {

    val ROLES = listOf(TEACHER, CLASS_REPRESENTATIVE, STUDENT)
    val CHANGEABLE_ROLES = listOf(CLASS_REPRESENTATIVE, STUDENT)
    val WEEK_DAYS = listOf(
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    )

    val SEMESTERS = listOf(
        FIRST_YEAR_FIRST_SEMESTER,
        FIRST_YEAR_SECOND_SEMESTER,
        SECOND_YEAR_FIRST_SEMESTER,
        SECOND_YEAR_SECOND_SEMESTER,
        THIRD_YEAR_FIRST_SEMESTER,
        THIRD_YEAR_SECOND_SEMESTER,
        FOURTH_YEAR_FIRST_SEMESTER,
        FOURTH_YEAR_SECOND_SEMESTER
    )

    val EVENTS = listOf("Term Test", "Assignment")
}