package com.example.sustclassnotifier.presentation.auth.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.components.CustomDropDownMenu
import com.example.sustclassnotifier.components.CustomElevatedButton
import com.example.sustclassnotifier.components.CustomOutlinedField
import com.example.sustclassnotifier.components.CustomPasswordField
import com.example.sustclassnotifier.components.Logo
import com.example.sustclassnotifier.core.Constants.ROLES
import com.example.sustclassnotifier.domain.event.SignUpUIEvent
import com.example.sustclassnotifier.navigation.ClassMateAppRouter
import com.example.sustclassnotifier.navigation.Screen
import com.example.sustclassnotifier.navigation.SystemBackButtonHandler
import com.example.sustclassnotifier.strings.ALREADY_A_USER_HARDCODED
import com.example.sustclassnotifier.strings.COURSE_DEPARTMENT_LABEL
import com.example.sustclassnotifier.strings.EMAIL_LABEL
import com.example.sustclassnotifier.strings.FIRST_NAME_LABEL
import com.example.sustclassnotifier.strings.LAST_NAME_LABEL
import com.example.sustclassnotifier.strings.PASSWORD_LABEL
import com.example.sustclassnotifier.strings.ROLE_HARDCODED
import com.example.sustclassnotifier.strings.SIGN_IN_BUTTON
import com.example.sustclassnotifier.strings.SIGN_UP_BUTTON
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.ZeroSpace

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {

    val signUpState = signUpViewModel.signUpUIState.collectAsState()
    val signUpInProgress = signUpViewModel.signUpInProgress.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Logo()

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                CustomOutlinedField(
                    labelValue = FIRST_NAME_LABEL,
                    onValueChange = { firstName ->
                        signUpViewModel.onEvent(SignUpUIEvent.FirstNameChanged(firstName))
                    },
                    errorMessage = signUpState.value.firstNameError,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = ZeroSpace)
                )
                CustomOutlinedField(
                    labelValue = LAST_NAME_LABEL,
                    onValueChange = { lastName ->
                        signUpViewModel.onEvent(SignUpUIEvent.LastNameChanged(lastName))
                    },
                    errorMessage = signUpState.value.lastNameError,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = ZeroSpace)
                )
            }


            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = ROLE_HARDCODED, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = MediumSpace)
                )
                CustomDropDownMenu(
                    itemList = ROLES,
                    onItemChange = { role ->
                        signUpViewModel.onEvent(SignUpUIEvent.RoleChanged(role))
                    }
                )
            }

            CustomOutlinedField(
                labelValue = COURSE_DEPARTMENT_LABEL,
                onValueChange = {department ->
                    signUpViewModel.onEvent(SignUpUIEvent.DepartmentChanged(department))
                },
                errorMessage = signUpState.value.departmentError
            )
            CustomOutlinedField(
                labelValue = EMAIL_LABEL,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { email ->
                    signUpViewModel.onEvent(SignUpUIEvent.EmailChanged(email))
                },
                errorMessage = signUpState.value.emailError
            )
            CustomPasswordField(labelValue = PASSWORD_LABEL, onPasswordChange = { password ->
                signUpViewModel.onEvent(SignUpUIEvent.PasswordChanged(password))
            }, errorMessage = signUpState.value.passwordError)

            CustomElevatedButton(text = SIGN_UP_BUTTON, onClick = {
                signUpViewModel.onEvent(SignUpUIEvent.SignUpButtonClicked)
            })
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            CustomElevatedButton(text = SIGN_IN_BUTTON, onClick = {
                ClassMateAppRouter.navigateTo(Screen.SignInScreen)
            })

        }
    }


    if (signUpInProgress.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }


    SystemBackButtonHandler {
        ClassMateAppRouter.navigateTo(Screen.SignInScreen)
    }
}