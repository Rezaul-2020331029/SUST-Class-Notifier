package com.example.sustclassnotifier.presentation.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sustclassnotifier.ui.theme.MaximumHeight
import com.example.sustclassnotifier.ui.theme.SmallSpace

@Composable
fun <T> FixedHeightLazyColumn  (
    items: List<T>,
    content: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = Modifier
        .fillMaxWidth()
        .height(MaximumHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SmallSpace)
    ) {
        items(
            items = items
        ) {
            content(it)
        }
    }
}