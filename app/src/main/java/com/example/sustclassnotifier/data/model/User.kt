package com.example.sustclassnotifier.data.model

import com.example.sustclassnotifier.strings.BLOOD_GROUP
import com.example.sustclassnotifier.strings.COURSES
import com.example.sustclassnotifier.strings.DEPARTMENT
import com.example.sustclassnotifier.strings.EMAIL
import com.example.sustclassnotifier.strings.FIRST_NAME
import com.example.sustclassnotifier.strings.LAST_NAME
import com.example.sustclassnotifier.strings.PHONE_NO
import com.example.sustclassnotifier.strings.REQUESTED_COURSES
import com.example.sustclassnotifier.strings.ROLE
import com.example.sustclassnotifier.strings.SESSION

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val role: String = "",
    val department: String = "",
    val session: String = "",
    val bloodGroup: String = "",
    val phoneNo: String = "",
    val email: String = "",
    val courses: List<String> = emptyList(),
    val requestedCourses: List<String> = emptyList()
) {
    fun toMap(): Map<String, Any> =  mapOf(
        FIRST_NAME to firstName,
        LAST_NAME to lastName,
        ROLE to role,
        DEPARTMENT to department,
        BLOOD_GROUP to bloodGroup,
        SESSION to session,
        PHONE_NO to phoneNo,
        EMAIL to email,
        COURSES to courses,
        REQUESTED_COURSES to requestedCourses
    )

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$firstName$lastName",
            firstName,
            lastName,
            "$firstName $lastName",
            "${firstName.first()}${lastName.first()}",
            "${firstName.first()} ${lastName.first()}"
        )

        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
