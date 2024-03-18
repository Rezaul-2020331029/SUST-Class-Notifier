package com.example.sustclassnotifier.presentation.auth.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.components.CustomClickableText
import com.example.sustclassnotifier.components.CustomElevatedButton
import com.example.sustclassnotifier.components.CustomOutlinedField
import com.example.sustclassnotifier.components.CustomPasswordField
import com.example.sustclassnotifier.components.ErrorScreen
import com.example.sustclassnotifier.components.LoadingScreen
import com.example.sustclassnotifier.components.Logo
import com.example.sustclassnotifier.domain.event.SignInUIEvent
import com.example.sustclassnotifier.domain.state.DataState
import com.example.sustclassnotifier.navigation.ClassMateAppRouter
import com.example.sustclassnotifier.navigation.Screen
import com.example.sustclassnotifier.strings.EMAIL_LABEL
import com.example.sustclassnotifier.strings.FORGOT_PASSWORD_BUTTON
import com.example.sustclassnotifier.strings.NEW_IN_CLASSMATE_HARDCODED
import com.example.sustclassnotifier.strings.PASSWORD_LABEL
import com.example.sustclassnotifier.strings.SIGN_IN_BUTTON
import com.example.sustclassnotifier.strings.SIGN_UP_BUTTON

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel()
) {

    val signInState = signInViewModel.signInUIState.collectAsState()
    val signInDataState = signInViewModel.signInDataState.collectAsState()

    when(signInDataState.value) {
        is DataState.Error ->  {
            ErrorScreen(error = signInDataState.value.error!!)
        }
        DataState.Loading ->  {
            LoadingScreen()
        }
        is DataState.Success ->  {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Logo()

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomOutlinedField(
                        labelValue = "Enter Email",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = { email ->
                            signInViewModel.onEvent(
                                SignInUIEvent.EmailChanged(email)
                            )
                        },
                        errorMessage = signInState.value.emailError
                    )
                    CustomPasswordField(labelValue = "Enter Password", onPasswordChange = { password ->
                        signInViewModel.onEvent(SignInUIEvent.PasswordChanged(password))
                    }, errorMessage = signInState.value.passwordError)

                    CustomElevatedButton(text = SIGN_IN_BUTTON,
                        contentColor = ButtonDefaults.elevatedButtonColors(Color.Yellow).contentColor ,
                        onClick = {
                        signInViewModel.onEvent(SignInUIEvent.SignInButtonClicked)
                    })
//                    CustomClickableText(text = FORGOT_PASSWORD_BUTTON, onClick = {
//                        ClassMateAppRouter.navigateTo(Screen.ForgotPasswordScreen)
//                    })
                }


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    CustomElevatedButton(
                        text = SIGN_UP_BUTTON,
                        onClick = {
                            ClassMateAppRouter.navigateTo(Screen.SignUpScreen)
                        }
                    )
                }
            }
        }
    }
}