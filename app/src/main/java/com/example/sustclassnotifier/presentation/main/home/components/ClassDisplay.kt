package com.example.sustclassnotifier.presentation.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.sustclassnotifier.data.model.ClassDetails
import com.example.sustclassnotifier.data.model.User
import com.example.sustclassnotifier.domain.event.HomeUIEvent
import com.example.sustclassnotifier.presentation.main.home.HomeViewModel
import com.example.sustclassnotifier.strings.CLASS_REPRESENTATIVE
import com.example.sustclassnotifier.strings.COLON
import com.example.sustclassnotifier.strings.TEACHER
import com.example.sustclassnotifier.ui.theme.LargeRounded
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.NormalHeight
import com.example.sustclassnotifier.ui.theme.SmallSpace
import com.example.sustclassnotifier.ui.theme.SomeStyle

@Composable
fun ClassDisplay(
    homeViewModel: HomeViewModel,
    classDetails: ClassDetails,
    user: User
) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace),
        shape = LargeRounded
    ) {
        Row(
            modifier = Modifier
                .clip(LargeRounded)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = classDetails.classCourseCode)

            // Start Time
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

            // End Time
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

            Text(text = classDetails.classroom)

            Switch(
                checked = classDetails.isActive,
                onCheckedChange = {activeStatus ->
                    homeViewModel.onHomeEvent(HomeUIEvent.ClassStatusChange(classDetails, activeStatus))
                },
                enabled = user.role == TEACHER || user.role == CLASS_REPRESENTATIVE
            )
        }
    }
}