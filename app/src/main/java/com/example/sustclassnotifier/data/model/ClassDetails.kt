package com.example.sustclassnotifier.data.model

import com.example.sustclassnotifier.core.Constants.WEEK_DAYS
import com.example.sustclassnotifier.strings.ACTIVE_STATUS
import com.example.sustclassnotifier.strings.CLASSROOM
import com.example.sustclassnotifier.strings.CLASS_COURSE_CODE
import com.example.sustclassnotifier.strings.CLASS_DEPARTMENT
import com.example.sustclassnotifier.strings.CLASS_NO
import com.example.sustclassnotifier.strings.END_HOUR
import com.example.sustclassnotifier.strings.END_MINUTE
import com.example.sustclassnotifier.strings.END_SHIFT
import com.example.sustclassnotifier.strings.SECTION
import com.example.sustclassnotifier.strings.START_HOUR
import com.example.sustclassnotifier.strings.START_MINUTE
import com.example.sustclassnotifier.strings.START_SHIFT
import com.example.sustclassnotifier.strings.WEEKDAY

data class ClassDetails(
    val classDepartment: String = "",
    val classCourseCode: String = "",
    val classNo: Int = -1,
    val weekDay: String = WEEK_DAYS[0],
    val classroom: String = "",
    val section: String = "",
    val startHour: Int = -1,
    val startMinute: Int = -1,
    val startShift: String = "",
    val endHour: Int = -1,
    val endMinute: Int = -1,
    val endShift: String = "",
    val isActive: Boolean = true
) {
    fun toMap(): Map<String, Any> = mapOf(
        CLASS_DEPARTMENT to classDepartment,
        CLASS_COURSE_CODE to classCourseCode,
        CLASS_NO to classNo,
        WEEKDAY to weekDay,
        CLASSROOM to classroom,
        SECTION to section,
        START_HOUR to startHour,
        START_MINUTE to startMinute,
        START_SHIFT to startShift,
        END_HOUR to endHour,
        END_MINUTE to endMinute,
        END_SHIFT to endShift,
        ACTIVE_STATUS to isActive
    )

    fun doesMatchSearchQuery(query: String, weekDay: String): Boolean {
        val matchingCombinations = listOf(
            "${classroom.first()}${classroom.last()}",
            classroom,
        )

        return matchingCombinations.any { it.contains(query, ignoreCase = true) || query.contains(it, ignoreCase = true) } && weekDay == this.weekDay
    }

    fun doesMatchWeekDay(weekDay: String): Boolean {
        return weekDay == this.weekDay
    }
}
