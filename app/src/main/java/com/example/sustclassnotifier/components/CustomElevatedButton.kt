package com.example.sustclassnotifier.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sustclassnotifier.ui.theme.ButtonStyle
import com.example.sustclassnotifier.ui.theme.MediumRounded

@Composable
fun CustomElevatedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = ButtonDefaults.elevatedButtonColors().containerColor,
    shape: Shape = MediumRounded,
    enabled: Boolean = true,
) {

    ElevatedButton(
        onClick = onClick,
        shape = shape,
        modifier = modifier.sizeIn(minWidth = 96.dp, minHeight = 48.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(Color.Yellow)
    ) {
        Text(
            text = text, style = ButtonStyle,
            textAlign = TextAlign.Center,
            color = Color.Black,


        )
    }
}