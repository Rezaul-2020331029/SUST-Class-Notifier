package com.example.sustclassnotifier.presentation.main.menu.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import com.example.sustclassnotifier.components.CustomTextFieldWithOutLabel
import com.example.sustclassnotifier.ui.theme.ExtraSmallSpace
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.TitleStyle
import com.example.sustclassnotifier.ui.theme.ZeroSpace

@Composable
fun UserInfo(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    readOnly: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MediumSpace),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$title:", style = TitleStyle, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(ExtraSmallSpace))
        CustomTextFieldWithOutLabel(
            value = value,
            onValueChange = { value ->
                onValueChange(value)
            },
            errorMessage = errorMessage,
            readOnly = readOnly,
            startPadding = ZeroSpace,
            endPadding = ZeroSpace,
            shape = RectangleShape,
            modifier = Modifier.weight(3.25f),
            keyboardOptions = keyboardOptions
        )
    }
}