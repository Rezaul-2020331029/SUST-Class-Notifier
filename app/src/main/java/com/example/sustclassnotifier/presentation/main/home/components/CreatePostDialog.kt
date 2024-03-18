package com.example.sustclassnotifier.presentation.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sustclassnotifier.components.CustomClickableText
import com.example.sustclassnotifier.components.CustomDialog
import com.example.sustclassnotifier.components.CustomLargeTextField
import com.example.sustclassnotifier.components.CustomTextField
import com.example.sustclassnotifier.data.model.User
import com.example.sustclassnotifier.domain.event.PostUIEvent
import com.example.sustclassnotifier.presentation.main.home.HomeViewModel
import com.example.sustclassnotifier.strings.COURSE_CODE_LABEL
import com.example.sustclassnotifier.strings.DISCARD_BUTTON
import com.example.sustclassnotifier.strings.POST_BUTTON
import com.example.sustclassnotifier.ui.theme.ExtraLargeSpace
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.SmallSpace
import com.example.sustclassnotifier.ui.theme.ZeroSpace

@Composable
fun CreatePostDialog(
    homeViewModel: HomeViewModel,
    user: User,
) {

    val createPostUIState by homeViewModel.createPostUIState.collectAsState()

    CustomDialog(onDismissRequest = { homeViewModel.onPostEvent(PostUIEvent.DiscardButtonClicked) }) {
        Column {
            CustomTextField(labelValue = COURSE_CODE_LABEL, onValueChange = { courseCode ->
                homeViewModel.onPostEvent(PostUIEvent.CourseCodeChanged(courseCode))
            }, startPadding = ZeroSpace, endPadding = ZeroSpace, errorMessage = createPostUIState.courseCodeError)
            Spacer(modifier = Modifier.height(SmallSpace))
            CustomLargeTextField(
                placeholderValue = "What's on your mind?",
                onValueChange = { description ->
                    homeViewModel.onPostEvent(PostUIEvent.DescriptionChanged(description))
                },
                errorMessage = createPostUIState.descriptionError
            )

            Spacer(modifier = Modifier.height(ExtraLargeSpace))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumSpace),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomClickableText(text = DISCARD_BUTTON, onClick = {
                    homeViewModel.onPostEvent(PostUIEvent.DiscardButtonClicked)
                })
                Spacer(modifier = Modifier.width(SmallSpace))
                CustomClickableText(text = POST_BUTTON, onClick = {
                    homeViewModel.onPostEvent(
                        PostUIEvent.PostButtonClicked(
                            timestamp = System.currentTimeMillis(),
                            creator = user.email,
                            firstName = user.firstName,
                            lastName = user.lastName
                        )
                    )
                })
            }
            Spacer(modifier = Modifier.height(ExtraLargeSpace))
        }
    }
}