package com.example.sustclassnotifier.presentation.main.components.display_course

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.example.sustclassnotifier.components.CustomClickableText
import com.example.sustclassnotifier.components.CustomDialog
import com.example.sustclassnotifier.components.CustomOutlinedField
import com.example.sustclassnotifier.domain.event.CreateResourceUIEvent
import com.example.sustclassnotifier.strings.CANCEL_BUTTON
import com.example.sustclassnotifier.strings.CREATE_BUTTON
import com.example.sustclassnotifier.strings.LINK_LABEL
import com.example.sustclassnotifier.strings.TITLE_LABEL
import com.example.sustclassnotifier.ui.theme.ClickableTextStyle
import com.example.sustclassnotifier.ui.theme.ExtraLargeSpace
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.SmallSpace

@Composable
fun CreateResourceLink(
    courseDetailsDisplayViewModel: CourseDetailsDisplayViewModel,
) {

    val createResourceLinkUIState by courseDetailsDisplayViewModel.resourceUIState.collectAsState()

    CustomDialog(
        onDismissRequest = {
            courseDetailsDisplayViewModel.onCreateResourceLinkEvent(CreateResourceUIEvent.CancelButtonClicked)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = ExtraLargeSpace),
            verticalArrangement = Arrangement.spacedBy(SmallSpace)
        ) {

            // Title
            CustomOutlinedField(
                labelValue = TITLE_LABEL,
                onValueChange = { title ->
                    courseDetailsDisplayViewModel.onCreateResourceLinkEvent(
                        CreateResourceUIEvent.TitleChanged(
                            title
                        )
                    )
                }, errorMessage = createResourceLinkUIState.titleError
            )

            // Link
            CustomOutlinedField(
                labelValue = LINK_LABEL,
                onValueChange = { link ->
                    courseDetailsDisplayViewModel.onCreateResourceLinkEvent(
                        CreateResourceUIEvent.LinkChanged(
                            link
                        )
                    )
                }, errorMessage = createResourceLinkUIState.linkError
            )


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
                        courseDetailsDisplayViewModel.onCreateResourceLinkEvent(CreateResourceUIEvent.CancelButtonClicked)
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
                        courseDetailsDisplayViewModel.onCreateResourceLinkEvent(CreateResourceUIEvent.CreateButtonClicked)
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