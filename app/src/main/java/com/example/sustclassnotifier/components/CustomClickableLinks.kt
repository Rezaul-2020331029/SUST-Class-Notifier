package com.example.sustclassnotifier.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.sustclassnotifier.ui.theme.ClickableLinkStyle

@Composable
fun CustomClickableLinks(
    text: String,
    annotation: String,
    style: SpanStyle = ClickableLinkStyle
) {

    val context = LocalContext.current

    val annotatedText = buildAnnotatedString {
        withStyle(
            style = style
        ) {
            pushStringAnnotation(
                tag = text,
                annotation = annotation)
            append(text)
        }
    }

    ClickableText(
        text = annotatedText,
        onClick = {  offSet ->
                  annotatedText.getStringAnnotations(offSet, offSet)
                      .firstOrNull()?.let { annotation ->
                          if (annotation.tag == text) {
                              Uri.parse(annotation.item).also {uri ->
                                  context.startActivity(
                                      Intent(Intent.ACTION_VIEW, uri)
                                  )
                              }
                          }
                      }
        },
    )
}