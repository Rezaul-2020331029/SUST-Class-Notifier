package com.example.sustclassnotifier.presentation.main.create_semester.components.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sustclassnotifier.components.CustomDropDownMenu
import com.example.sustclassnotifier.components.CustomElevatedButton
import com.example.sustclassnotifier.components.CustomOutlinedField
import com.example.sustclassnotifier.components.TitleContainer
import com.example.sustclassnotifier.core.Constants.SEMESTERS
import com.example.sustclassnotifier.domain.event.CreateCourseUIEvent
import com.example.sustclassnotifier.presentation.main.create_semester.components.CreateCourseTopBar
import com.example.sustclassnotifier.presentation.main.create_semester.components.CreateCourseViewModel
import com.example.sustclassnotifier.presentation.main.create_semester.components.DisplayClassDetails
import com.example.sustclassnotifier.strings.COURSE_CODE_LABEL
import com.example.sustclassnotifier.strings.COURSE_CREDIT_LABEL
import com.example.sustclassnotifier.strings.COURSE_TITLE_LABEL
import com.example.sustclassnotifier.strings.CREATED_CLASSES_TITLE
import com.example.sustclassnotifier.strings.CREATE_CLASS_BUTTON
import com.example.sustclassnotifier.strings.SEARCH_COURSE_TEACHER_BUTTON
import com.example.sustclassnotifier.strings.SEMESTER_HARD_CODED
import com.example.sustclassnotifier.ui.theme.ExtraExtraLargeSpace
import com.example.sustclassnotifier.ui.theme.LargeSpace
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.SmallSpace

@Composable
fun CreateCourse(
    createCourseViewModel: CreateCourseViewModel = viewModel(),
) {

    val createCourseUIState by createCourseViewModel.createCourseUIState.collectAsState()
    val createClassDialogState by createCourseViewModel.createCourseDialogState.collectAsState()
    val classDetailsList by createCourseViewModel.createClassDetailsDataList.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CreateCourseTopBar(createCourseViewModel)

        CustomOutlinedField(
            value = createCourseUIState.courseCode,
            labelValue = COURSE_CODE_LABEL,
            onValueChange = { courseCode ->
                createCourseViewModel.onCreateCourse(
                    CreateCourseUIEvent.CourseCodeChanged(
                        courseCode
                    )
                )
            },
            errorMessage = createCourseUIState.courseCodeError
        )
        CustomOutlinedField(
            value = createCourseUIState.courseTitle,
            labelValue = COURSE_TITLE_LABEL,
            onValueChange = { courseTitle ->
                createCourseViewModel.onCreateCourse(
                    CreateCourseUIEvent.CourseTitleChanged(
                        courseTitle
                    )
                )
            },
            errorMessage = createCourseUIState.courseTitleError
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomOutlinedField(
                value = createCourseUIState.courseCredit.toString(),
                labelValue = COURSE_CREDIT_LABEL,
                onValueChange = { courseCredit ->
                    createCourseViewModel.onCreateCourse(
                        CreateCourseUIEvent.CourseCreditChanged(
                            courseCredit
                        )
                    )
                },
                errorMessage = createCourseUIState.courseCreditError,
                modifier = Modifier
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = SEMESTER_HARD_CODED, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MediumSpace)
            )
            CustomDropDownMenu(
                itemList = SEMESTERS,
                onItemChange = { semester ->
                    createCourseViewModel.onCreateCourse(
                        CreateCourseUIEvent.CourseSemesterChanged(
                            semester
                        )
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(MediumSpace))

        // Search Teacher Button
        CustomElevatedButton(
            text = if (createCourseUIState.courseTeacherEmailError == null) SEARCH_COURSE_TEACHER_BUTTON else createCourseUIState.courseTeacherEmailError!!,
            onClick = {
                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.SearchTeacherButtonClick)
            },
            contentColor = if (createCourseUIState.courseTeacherEmailError == null) ButtonDefaults.elevatedButtonColors().contentColor else MaterialTheme.colorScheme.error,
        )

        Spacer(modifier = Modifier.height(ExtraExtraLargeSpace))

        CustomElevatedButton(
            text = CREATE_CLASS_BUTTON,
            onClick = {
                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CreateClassButtonClick)
            }
        )

        if (createClassDialogState) {
            CreateClass(createCourseViewModel)
        }

        Spacer(modifier = Modifier.height(LargeSpace))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(SmallSpace),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TitleContainer(title = CREATED_CLASSES_TITLE)

            if (createCourseUIState.createClassError != null) {
                Text(
                    text = createCourseUIState.createClassError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            
            classDetailsList.forEach { 
                DisplayClassDetails(classDetails = it, createCourseViewModel = createCourseViewModel)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateCoursePreview() {
    CreateCourse()
}