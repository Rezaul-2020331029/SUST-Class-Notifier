package com.example.sustclassnotifier.data.model

import com.example.sustclassnotifier.strings.RESOURCE_COURSE_CODE
import com.example.sustclassnotifier.strings.RESOURCE_DEPARTMENT
import com.example.sustclassnotifier.strings.RESOURCE_LINK
import com.example.sustclassnotifier.strings.RESOURCE_NO
import com.example.sustclassnotifier.strings.RESOURCE_TITLE

data class ResourceLink(
    val resourceDepartment: String = "",
    val resourceCourseCode: String = "",
    val resourceNo: Int = -1,
    val title: String = "",
    val link: String = "",
) {
    fun toMap(): Map<String, Any> = mapOf(
        RESOURCE_DEPARTMENT to resourceDepartment,
        RESOURCE_COURSE_CODE to resourceCourseCode,
        RESOURCE_NO to resourceNo,
        RESOURCE_TITLE to title,
        RESOURCE_LINK to link,
    )
}
