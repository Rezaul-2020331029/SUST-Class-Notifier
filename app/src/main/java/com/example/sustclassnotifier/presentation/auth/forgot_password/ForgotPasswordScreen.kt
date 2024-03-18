package com.example.sustclassnotifier.presentation.auth.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.components.CustomElevatedButton
import com.example.sustclassnotifier.components.CustomOutlinedField
import com.example.sustclassnotifier.components.Logo
import com.example.sustclassnotifier.domain.event.ForgotPasswordUIEvent
import com.example.sustclassnotifier.navigation.ClassMateAppRouter
import com.example.sustclassnotifier.navigation.Screen
import com.example.sustclassnotifier.navigation.SystemBackButtonHandler
import com.example.sustclassnotifier.strings.EMAIL_LABEL
import com.example.sustclassnotifier.strings.RESET_PASSWORD_BUTTON
import com.example.sustclassnotifier.ui.theme.ExtraExtraLargeSpace

@Composable
fun ForgotPasswordScreen(
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()
) {

    val forgotPasswordUIState = forgotPasswordViewModel.forgotPasswordUIState.collectAsState()

    val localFocusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo()
        Spacer(modifier = Modifier.height(ExtraExtraLargeSpace))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomOutlinedField(
                labelValue = EMAIL_LABEL,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                onValueChange = { email ->
                    forgotPasswordViewModel.onEvent(ForgotPasswordUIEvent.EmailChanged(email))
                },
                keyboardActions = KeyboardActions {
                    localFocusManager.clearFocus()
                },
                errorMessage = forgotPasswordUIState.value.emailError
            )
            CustomElevatedButton(text = RESET_PASSWORD_BUTTON, onClick = {
                forgotPasswordViewModel.onEvent(ForgotPasswordUIEvent.ForgotPasswordButtonClick)
            })
        }
    }


    SystemBackButtonHandler {
        ClassMateAppRouter.navigateTo(Screen.SignInScreen)
    }
}