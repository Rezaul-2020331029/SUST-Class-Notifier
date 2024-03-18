package com.example.sustclassnotifier.data.model

import com.example.sustclassnotifier.strings.POST_COURSE_CODE
import com.example.sustclassnotifier.strings.POST_CREATOR
import com.example.sustclassnotifier.strings.POST_CREATOR_FIRST_NAME
import com.example.sustclassnotifier.strings.POST_CREATOR_LAST_NAME
import com.example.sustclassnotifier.strings.POST_DESCRIPTION
import com.example.sustclassnotifier.strings.POST_TIMESTAMP

data class Post(
    val creator: String = "",
    val timestamp: Long = 0L,
    val firstName: String = "",
    val lastName: String = "",
    val courseCode: String = "",
    val description: String = ""
) {
    fun toMap(): Map<String, Any> = mapOf(
        POST_CREATOR to creator,
        POST_TIMESTAMP to timestamp,
        POST_CREATOR_FIRST_NAME to firstName,
        POST_CREATOR_LAST_NAME to lastName,
        POST_COURSE_CODE to courseCode,
        POST_DESCRIPTION to description
    )
}
