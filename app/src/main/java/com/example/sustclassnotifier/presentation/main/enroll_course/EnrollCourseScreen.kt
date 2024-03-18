package com.example.sustclassnotifier.presentation.main.enroll_course

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.components.LoadingScreen
import com.example.sustclassnotifier.domain.state.DataState
import com.example.sustclassnotifier.presentation.main.enroll_course.components.EnrollCourseScreenContent

@Composable
fun EnrollCourseScreen(
    enrollCourseViewModel: EnrollCourseViewModel = hiltViewModel(),
) {

    val userState by enrollCourseViewModel.userState.collectAsState()

    when (userState) {
        is DataState.Error -> TODO()
        DataState.Loading -> {
            LoadingScreen()
        }

        is DataState.Success -> {
            userState.data?.let {
                EnrollCourseScreenContent(enrollCourseViewModel, user = it)
            }
        }
    }
}