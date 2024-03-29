package com.example.sustclassnotifier.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import com.example.sustclassnotifier.ui.theme.MediumRounded
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.ExtraExtraSmallSpace
import com.example.sustclassnotifier.ui.theme.ZeroSpace

@Composable
fun CustomTextFieldWithOutLabel(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    value: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions(),
    singeLine: Boolean = true,
    maxLines: Int = 1,
    shape: Shape = MediumRounded,
    readOnly: Boolean = false,
    startPadding: Dp = MediumSpace,
    endPadding: Dp = MediumSpace,
    topPadding: Dp = ZeroSpace,
    bottomPadding: Dp = ZeroSpace,
    errorMessage: String? = null
) {
    val text = remember { mutableStateOf("") }

    val isValueChanged = remember { mutableStateOf(false) }
    if (!isValueChanged.value) {
        text.value = value
        onValueChange(value)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = startPadding, top = topPadding, end = endPadding, bottom = bottomPadding
            ),
        verticalArrangement = Arrangement.spacedBy(ExtraExtraSmallSpace)
    ) {
        TextField(
            value = text.value.ifBlank {
                if (isValueChanged.value && !readOnly) {
                    text.value
                } else {
                    value
                }
            },
            onValueChange = {
                isValueChanged.value = true
                text.value = it
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singeLine,
            maxLines = maxLines,
            shape = shape,
            readOnly = readOnly,
            isError = errorMessage != null,
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}