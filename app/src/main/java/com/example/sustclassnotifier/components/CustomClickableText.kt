package com.example.sustclassnotifier.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.sustclassnotifier.ui.theme.ClickableTextStyle

@Composable
fun CustomClickableText(
    text: String,
    onClick: (Int) -> Unit,
    style: SpanStyle = ClickableTextStyle
) {

    val annotatedText = buildAnnotatedString {
        withStyle(
            style = style
        ) {
            pushStringAnnotation(text, text)
            append(text)
        }
    }

    ClickableText(
        text = annotatedText,
        onClick = { onClick(it) },
    )
}