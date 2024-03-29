package com.example.sustclassnotifier.presentation.auth.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.example.sustclassnotifier.domain.event.SignInUIEvent
import com.example.sustclassnotifier.domain.repository.AuthenticationRepository
import com.example.sustclassnotifier.domain.rules.AuthValidator
import com.example.sustclassnotifier.domain.state.DataState
import com.example.sustclassnotifier.domain.state.SignInUIState
import com.example.sustclassnotifier.navigation.ClassMateAppRouter
import com.example.sustclassnotifier.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
private val auth: FirebaseAuth,
    private val authRepo: AuthenticationRepository
) : ViewModel() {

    private val _signInUIState = MutableStateFlow(SignInUIState())
    val signInUIState = _signInUIState.asStateFlow()

    private val _signInDataState = MutableStateFlow<DataState<Boolean>>(DataState.Success(true))
    val signInDataState = _signInDataState.asStateFlow()

    private val _allValidationPassed = MutableStateFlow(false)
    private val allValidationPassed = _allValidationPassed.asStateFlow()

    fun onEvent(event: SignInUIEvent) {
        when (event) {

            is SignInUIEvent.EmailChanged -> {
                _signInUIState.value = _signInUIState.value.copy(email = event.email)
            }

            is SignInUIEvent.PasswordChanged -> {
                _signInUIState.value = _signInUIState.value.copy(password = event.password)
            }

            SignInUIEvent.SignInButtonClicked ->  {
                signIn()
            }
        }
    }


    private fun signIn() = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "signIn: ${signInUIState.value}")
        validateSignInUIDataWithRules()
//        Log.d(TAG, "signIn: ${allValidationPassed.value}")
        if(allValidationPassed.value) {
            authRepo.signIn(signInUIState.value.email, signInUIState.value.password).collectLatest {
                _signInDataState.value = it
            }
            if(signInDataState.value == DataState.Success(true)) {
                ClassMateAppRouter.navigateTo(Screen.HomeScreen)
            }
        }

    }

    private fun validateSignInUIDataWithRules() {
        val emailResult = AuthValidator.validateEmail(signInUIState.value.email)
        val passwordResult = AuthValidator.validatePassword(signInUIState.value.password)


        _signInUIState.value = _signInUIState.value.copy(
            emailError = emailResult.message,
            passwordError = passwordResult.message
        )


        _allValidationPassed.value = emailResult.message == null && passwordResult.message == null
    }

    companion object {
        const val TAG = "SignInViewModel"
    }
}