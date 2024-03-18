package com.example.sustclassnotifier.presentation.main.components.display_course

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.sustclassnotifier.R
import com.example.sustclassnotifier.data.model.Course
import com.example.sustclassnotifier.ui.theme.SixtyHeight
import com.example.sustclassnotifier.ui.theme.TitleStyle

@Composable
fun BlackBoardContent(course: Course) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
       // Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)

        Column (modifier = Modifier.fillMaxWidth().padding(start = SixtyHeight)) {
            BlackboardRow(title = "Department", text = course.courseDepartment)
            BlackboardRow(title = "Semester", text = course.courseSemester)
            BlackboardRow(title = "Code", text = course.courseCode)
            BlackboardRow(title = "Title", text = course.courseTitle)
            BlackboardRow(title = "Credit", text = course.courseCredit.toString())
            BlackboardRow(title = "Teacher", text = course.courseTeacher)
            BlackboardRow(title = "Enrolled Students", text = course.enrolledStudents.size.toString())
        }
    }
}


@Composable
private fun BlackboardRow(title: String, text: String) {
    Row (modifier = Modifier.fillMaxWidth()){
        Text(text = "$title: ", style = TitleStyle, color = Color.Black)
        Text(text = text, color = Color.Black)
    }
}