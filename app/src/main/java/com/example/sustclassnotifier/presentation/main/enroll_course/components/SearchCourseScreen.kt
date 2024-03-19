package com.example.sustclassnotifier.presentation.main.enroll_course.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.data.model.User
import com.example.sustclassnotifier.domain.event.SearchCourseUIEvent
import com.example.sustclassnotifier.domain.event.SearchUIEvent
import com.example.sustclassnotifier.strings.ENROLL_BUTTON
import com.example.sustclassnotifier.strings.SEARCH
import com.example.sustclassnotifier.ui.theme.ExtraSmallSpace
import com.example.sustclassnotifier.ui.theme.MediumRounded
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.NormalHeight
import com.example.sustclassnotifier.ui.theme.SmallSpace
import com.example.sustclassnotifier.ui.theme.TitleStyle

@Composable
fun SearchCourseScreen(
    searchViewModel: SearchCourseViewModel = hiltViewModel(),
    user: User
) {
    searchViewModel.getAllCourses(user)
    val searchText = searchViewModel.searchText.collectAsState()
    val courses = searchViewModel.courses.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize()

    ) {
        TextField(
            value = searchText.value,
            onValueChange = { searchText ->
                searchViewModel.onSearch(SearchUIEvent.SearchTextChanged(searchText))
                Log.d("searc", "Search done")
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = SEARCH)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(MediumSpace))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(
                items = courses.value.toList(),
                key =  {
                    "${it.courseDepartment}:${it.courseCode}"
                }
            ) {
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
                            text = it.courseCode,
                            style = TitleStyle
                        )
                        Spacer(modifier = Modifier.height(ExtraSmallSpace))
                        Text(
                            text = it.courseTitle,
                        )
                        Spacer(modifier = Modifier.height(ExtraSmallSpace))
                        Text(
                            text = it.courseDepartment,
                        )
                    }

                    Button(
                        onClick = {
                            val courseId = "${it.courseDepartment}:${it.courseCode}"
                            searchViewModel.onSearchCourseEvent(
                                SearchCourseUIEvent.EnrollButtonClicked(
                                    courseId,
                                    user.email
                                )
                            )
                        },
                        shape = MediumRounded
                    ) {
                        Text(text = ENROLL_BUTTON)
                    }
                }
            }
        }
    }
}