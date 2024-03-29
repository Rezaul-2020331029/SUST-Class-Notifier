package com.example.sustclassnotifier.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.example.sustclassnotifier.strings.HIDE_PASSWORD_ICON
import com.example.sustclassnotifier.strings.SHOW_PASSWORD_ICON
import com.example.sustclassnotifier.ui.theme.ExtraExtraSmallSpace
import com.example.sustclassnotifier.ui.theme.MediumRounded
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.ZeroSpace

@Composable
fun CustomPasswordField(
    labelValue: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    shape: Shape = MediumRounded,
    readOnly: Boolean = false,
    startPadding: Dp = MediumSpace,
    endPadding: Dp = MediumSpace,
    topPadding: Dp = ZeroSpace,
    bottomPadding: Dp = ZeroSpace,
    errorMessage: String? = null
) {

    val localFocusManager = LocalFocusManager.current
    val password = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start =  startPadding, top = topPadding, end = endPadding, bottom = bottomPadding
            ),
        verticalArrangement = Arrangement.spacedBy(ExtraExtraSmallSpace)
    ) {
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                onPasswordChange(it)
            },
            label = { Text(text = labelValue) },
            trailingIcon = {
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisibility.value) HIDE_PASSWORD_ICON else SHOW_PASSWORD_ICON,
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            singleLine = true,
            maxLines = 1,
            keyboardActions = KeyboardActions {
                localFocusManager.clearFocus()
            },
            shape = shape,
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            readOnly = readOnly,
            isError = errorMessage != null
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