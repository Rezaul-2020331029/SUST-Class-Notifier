package com.example.sustclassnotifier.presentation.main.menu.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.components.CustomDropDownMenu
import com.example.sustclassnotifier.core.Constants.WEEK_DAYS
import com.example.sustclassnotifier.domain.event.RoutineUIEvent
import com.example.sustclassnotifier.presentation.main.menu.routine.components.RoutineTopBar
import com.example.sustclassnotifier.strings.COLON
import com.example.sustclassnotifier.strings.SEARCH
import com.example.sustclassnotifier.ui.theme.LargeRounded
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.NormalHeight
import com.example.sustclassnotifier.ui.theme.SmallSpace
import com.example.sustclassnotifier.ui.theme.SomeStyle

@Composable
fun RoutineScreen(
    routineViewModel: RoutineViewModel = hiltViewModel(),
) {

    val searchText = routineViewModel.searchText.collectAsState()
    val classes = routineViewModel.classes.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        RoutineTopBar(onBackIconClick = {
            routineViewModel.onRoutineEvent(RoutineUIEvent.RoutineBackButtonClicked)
        })
        Spacer(modifier = Modifier.height(MediumSpace))
        CustomDropDownMenu(
            itemList = WEEK_DAYS,
            onItemChange = { weekDay ->
                routineViewModel.onRoutineEvent(RoutineUIEvent.WeekDayChanged(weekDay))
            }
        )
        Spacer(modifier = Modifier.height(SmallSpace))
        TextField(
            value = searchText.value,
            onValueChange = { searchText ->
                routineViewModel.onRoutineEvent(RoutineUIEvent.SearchTextChanged(searchText))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = SEARCH) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(MediumSpace))
        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(
                items = classes.value.toList(),
                key = {
                    "${it.classDepartment}:${it.classCourseCode}:${it.classNo}"
                }
            ) { classDetails ->
                Row(
                    modifier = Modifier
                        .clip(LargeRounded)
                        .fillMaxWidth()
                        .height(NormalHeight),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = classDetails.classDepartment, style = SomeStyle)
                    Text(text = classDetails.classCourseCode, style = SomeStyle)
                    Text(text = classDetails.section, style = SomeStyle)
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = classDetails.startHour.toString(), style = SomeStyle)
                        Text(text = COLON, style = SomeStyle)
                        Text(text = classDetails.startMinute.toString(), style = SomeStyle)
                        Spacer(modifier = Modifier.width(SmallSpace))
                        Text(text = classDetails.startShift, style = SomeStyle)
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = classDetails.endHour.toString(), style = SomeStyle)
                        Text(text = COLON, style = SomeStyle)
                        Text(text = classDetails.endMinute.toString(), style = SomeStyle)
                        Spacer(modifier = Modifier.width(SmallSpace))
                        Text(text = classDetails.endShift, style = SomeStyle)
                    }
                }

            }
        }
    }
}