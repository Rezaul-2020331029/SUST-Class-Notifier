package com.example.sustclassnotifier.presentation.main.components.display_course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.example.sustclassnotifier.components.CustomClickableText
import com.example.sustclassnotifier.components.CustomDatePicker
import com.example.sustclassnotifier.components.CustomDialog
import com.example.sustclassnotifier.components.CustomOutlinedField
import com.example.sustclassnotifier.components.CustomTimePicker
import com.example.sustclassnotifier.domain.event.CreateAssignmentUIEvent
import com.example.sustclassnotifier.strings.CANCEL_BUTTON
import com.example.sustclassnotifier.strings.CLASSROOM_LABEL
import com.example.sustclassnotifier.strings.CREATE_BUTTON
import com.example.sustclassnotifier.ui.theme.ClickableTextStyle
import com.example.sustclassnotifier.ui.theme.ExtraLargeSpace
import com.example.sustclassnotifier.ui.theme.ExtraSmallSpace
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.SmallSpace

@Composable
fun CreateAssignment(
    courseDetailsDisplayViewModel: CourseDetailsDisplayViewModel,
) {

    val createAssignmentUIState by courseDetailsDisplayViewModel.assignmentUIState.collectAsState()

    CustomDialog(
        onDismissRequest = {
            courseDetailsDisplayViewModel.onCreateAssignmentEvent(CreateAssignmentUIEvent.CancelButtonClick)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = ExtraLargeSpace),
            verticalArrangement = Arrangement.spacedBy(SmallSpace)
        ) {

            // Classroom
            CustomOutlinedField(
                labelValue = CLASSROOM_LABEL,
                onValueChange = { classroom ->
                    courseDetailsDisplayViewModel.onCreateAssignmentEvent(
                        CreateAssignmentUIEvent.ClassroomChanged(
                            classroom
                        )
                    )
                }, errorMessage = createAssignmentUIState.classroomError
            )

            // Date Picker
            CustomDatePicker(
                onDayChange = { day ->
                    courseDetailsDisplayViewModel.onCreateAssignmentEvent(
                        CreateAssignmentUIEvent.DayChanged(day)
                    )
                },
                onMonthChange = { month ->
                    courseDetailsDisplayViewModel.onCreateAssignmentEvent(
                        CreateAssignmentUIEvent.MonthChanged(month)
                    )
                },
                onYearChange = { year ->
                    courseDetailsDisplayViewModel.onCreateAssignmentEvent(
                        CreateAssignmentUIEvent.YearChanged(year)
                    )

                }
            )

            // Time Picker
            CustomTimePicker(
                onHourChange = { hour ->
                    courseDetailsDisplayViewModel.onCreateAssignmentEvent(
                        CreateAssignmentUIEvent.HourChanged(hour)
                    )
                },
                onMinuteChange = { minute ->
                    courseDetailsDisplayViewModel.onCreateAssignmentEvent(
                        CreateAssignmentUIEvent.MinuteChanged(minute)
                    )
                },
                onShiftClick = { shift ->
                    courseDetailsDisplayViewModel.onCreateAssignmentEvent(
                        CreateAssignmentUIEvent.ShiftChanged(shift)
                    )
                }
            )

            if (createAssignmentUIState.dateError != null) {
                Spacer(modifier = Modifier.height(ExtraSmallSpace))
                Text(
                    text = createAssignmentUIState.dateError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = MediumSpace)
                )
            } else if (createAssignmentUIState.timeError != null) {
                Spacer(modifier = Modifier.height(ExtraSmallSpace))
                Text(
                    text = createAssignmentUIState.timeError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = MediumSpace)
                )
            }

            Spacer(modifier = Modifier.height(ExtraLargeSpace))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumSpace),
                horizontalArrangement = Arrangement.End
            ) {
                CustomClickableText(
                    text = CANCEL_BUTTON,
                    onClick = {
                        courseDetailsDisplayViewModel.onCreateAssignmentEvent(CreateAssignmentUIEvent.CancelButtonClick)
                    },
                    style = ClickableTextStyle.copy(
                        textDecoration = TextDecoration.None,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(SmallSpace))
                CustomClickableText(
                    text = CREATE_BUTTON,
                    onClick = {
                        courseDetailsDisplayViewModel.onCreateAssignmentEvent(CreateAssignmentUIEvent.CreateButtonClick)
                    },
                    style = ClickableTextStyle.copy(
                        textDecoration = TextDecoration.None,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}