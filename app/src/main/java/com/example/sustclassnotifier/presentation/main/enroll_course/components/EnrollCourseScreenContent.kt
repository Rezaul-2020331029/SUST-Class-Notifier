package com.example.sustclassnotifier.presentation.main.enroll_course.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sustclassnotifier.components.CustomSwipeAbleLazyColumn
import com.example.sustclassnotifier.components.TitleContainer
import com.example.sustclassnotifier.data.model.User
import com.example.sustclassnotifier.navigation.TabItem
import com.example.sustclassnotifier.presentation.main.components.ClassMateTabRow
import com.example.sustclassnotifier.presentation.main.enroll_course.EnrollCourseViewModel
import com.example.sustclassnotifier.ui.theme.LargeSpace
import com.example.sustclassnotifier.ui.theme.SmallSpace

@Composable
fun EnrollCourseScreenContent(
    enrollCourseViewModel: EnrollCourseViewModel,
    user: User
) {

    enrollCourseViewModel.getAlreadyEnrolledCourses()
    val alreadyEnrolledCourses by enrollCourseViewModel.alreadyEnrolled.collectAsState()

    Column (
        modifier = Modifier.fillMaxSize()
            .background(Color.Yellow),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClassMateTabRow(tab = TabItem.EnrollCourse)
        Column (modifier = Modifier.weight(.2f)) {
            SearchCourseScreen(user = user)
        }
        Spacer(modifier = Modifier.height(LargeSpace))
        Column (modifier = Modifier.weight(.8f)) {
            TitleContainer(title = "Already Enrolled")
            Spacer(modifier = Modifier.height(SmallSpace))
            CustomSwipeAbleLazyColumn(
                items = alreadyEnrolledCourses,
                key = {
                    "${it.courseDepartment}:${it.courseCode}"
                }
            ) {
                CourseDisplayOnEnrollCourse(course = it, enrollCourseViewModel = enrollCourseViewModel)
            }
        }
    }
}