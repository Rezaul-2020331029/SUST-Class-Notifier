package com.example.sustclassnotifier.presentation.main.create_semester.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.domain.event.CreateCourseUIEvent
import com.example.sustclassnotifier.strings.CREATE_ICON
import com.example.sustclassnotifier.strings.CREATE_COURSE
import com.example.sustclassnotifier.strings.GO_BACK_ICON
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.NormalHeight
import com.example.sustclassnotifier.ui.theme.TitleStyle

@Composable
fun CreateCourseTopBar(
    createCourseViewModel: CreateCourseViewModel
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = GO_BACK_ICON,
            modifier = Modifier
                .clickable {
                    createCourseViewModel.onCreateCourse(CreateCourseUIEvent.BackButtonClick)
                }
        )
        Text(text = CREATE_COURSE, style = TitleStyle)

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = CREATE_ICON,
            modifier = Modifier.clickable {
                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CreateClick)
            }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CreateSemesterTopBarPreview() {
    CreateCourseTopBar(hiltViewModel())
}