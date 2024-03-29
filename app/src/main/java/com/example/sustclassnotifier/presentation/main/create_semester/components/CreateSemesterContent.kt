package com.example.sustclassnotifier.presentation.main.create_semester.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.sustclassnotifier.components.CustomSwipeAbleLazyColumn
import com.example.sustclassnotifier.components.TwoTitleContainer
import com.example.sustclassnotifier.domain.event.CreateSemesterUIEvent
import com.example.sustclassnotifier.navigation.TabItem
import com.example.sustclassnotifier.presentation.main.components.ClassMateTabRow
import com.example.sustclassnotifier.presentation.main.create_semester.CreateSemesterViewModel
import com.example.sustclassnotifier.strings.ADD_ICON
import com.example.sustclassnotifier.strings.CLASS_REPRESENTATIVE
import com.example.sustclassnotifier.strings.CREATED_COURSES_TITLE
import com.example.sustclassnotifier.strings.PENDING_COURSES_TITLE
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.SmallSpace

@Composable
fun CreateSemesterContent(
    createSemesterViewModel: CreateSemesterViewModel,
) {

    createSemesterViewModel.getCreatedCourses()
    createSemesterViewModel.getPendingCourses()

    val user by createSemesterViewModel.userState.collectAsState()
    val createdCourses by createSemesterViewModel.cratedCourses.collectAsState()
    val pendingCourses by createSemesterViewModel.pendingCourses.collectAsState()

    val courses = rememberSaveable { mutableStateOf(createdCourses) }

    Scaffold(
        topBar = { ClassMateTabRow(tab = TabItem.CreateSemester) },
        floatingActionButton = {
            if (user.data?.role == CLASS_REPRESENTATIVE) {
                FloatingActionButton(
                    onClick = {
                        createSemesterViewModel.onCreateSemesterEvent(CreateSemesterUIEvent.CreateSemesterFABClick)
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = ADD_ICON,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        ,
        modifier = Modifier.background(color = Color.Cyan)

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(color = Color.Cyan)
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() + MediumSpace),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SmallSpace)
        ) {
            // TODO: Add Routine
            // TODO: Add Already Created Courses
            TwoTitleContainer(
                leftTitle = CREATED_COURSES_TITLE,
                rightTitle = PENDING_COURSES_TITLE,
                leftClick = {
                    createSemesterViewModel.getCreatedCourses()
                    courses.value = createdCourses
                            },
                rightClick = {
                    createSemesterViewModel.getPendingCourses()
                    courses.value = pendingCourses }
            )

            CustomSwipeAbleLazyColumn(
                items = courses.value,
                key = {
                    "${it.courseDepartment}:${it.courseCode}"
                },
                modifier = Modifier.fillMaxHeight()
            ) {
//            courses.value.forEach {
                DisplayCourse(course = it, createSemesterViewModel = createSemesterViewModel)
//            }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateCourseContentPreview() {
//    CreateSemesterContent(user = User(), createSemesterViewModel = null)
}