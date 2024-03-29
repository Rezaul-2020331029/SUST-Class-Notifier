package com.example.sustclassnotifier.presentation.main.create_semester.components.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.domain.event.CreateCourseUIEvent
import com.example.sustclassnotifier.domain.event.SearchUIEvent
import com.example.sustclassnotifier.presentation.main.create_semester.components.CreateCourseViewModel
import com.example.sustclassnotifier.strings.REQUEST_BUTTON
import com.example.sustclassnotifier.strings.SEARCH
import com.example.sustclassnotifier.ui.theme.ExtraSmallSpace
import com.example.sustclassnotifier.ui.theme.MediumRounded
import com.example.sustclassnotifier.ui.theme.NormalHeight
import com.example.sustclassnotifier.ui.theme.SmallSpace
import com.example.sustclassnotifier.ui.theme.TitleStyle

@Composable
fun SearchTeacherScreen(
    searchTeacherViewModel: SearchTeacherViewModel = hiltViewModel(),
    createCourseViewModel: CreateCourseViewModel = hiltViewModel(),
) {
    val searchText = searchTeacherViewModel.searchText.collectAsState()
    val users = searchTeacherViewModel.users.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = searchText.value,
            onValueChange = { teacherEmail ->
                searchTeacherViewModel.onSearch(SearchUIEvent.SearchTextChanged(teacherEmail))
                            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = SEARCH) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            users.value.forEach {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(NormalHeight)
                            .padding(horizontal = SmallSpace)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "${it.firstName} ${it.lastName}",
                                style = TitleStyle
                            )
                            Spacer(modifier = Modifier.height(ExtraSmallSpace))
                            Text(
                                text = it.department
                            )
                        }

                        Button(
                            onClick = {
                                createCourseViewModel.onCreateCourse(
                                    CreateCourseUIEvent.SearchUIRequestButtonClick(courseTeacherEmail = it.email)
                                )
                            },
                            shape = MediumRounded
                        ) {
                            Text(text = REQUEST_BUTTON)
                        }
                    }
                }
            }
        }
    }
}