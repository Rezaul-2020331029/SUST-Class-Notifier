package com.example.sustclassnotifier.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sustclassnotifier.ui.theme.LargeRounded
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.NormalHeight
import com.example.sustclassnotifier.ui.theme.SomeStyle

@Composable
fun ClickableTitleContainer(
    title: String,
    onTitleClick: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace)
            .clickable {
                onTitleClick()
            },
        shape = LargeRounded,
        colors = CardDefaults.elevatedCardColors(Color.Yellow)
    ) {
        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = SomeStyle)
        }
    }
}