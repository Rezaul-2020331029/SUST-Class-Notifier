package com.example.sustclassnotifier.presentation.main.components.display_course

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sustclassnotifier.components.CustomClickableLinks
import com.example.sustclassnotifier.data.model.ResourceLink
import com.example.sustclassnotifier.ui.theme.ExtraSmallSpace
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.NormalHeight

@Composable
fun DisplayResourceLink(resourceLink: ResourceLink) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(horizontal = ExtraSmallSpace),
            contentAlignment = Alignment.Center
        ) {
            CustomClickableLinks(text = resourceLink.title, annotation = resourceLink.link)
        }
    }
}