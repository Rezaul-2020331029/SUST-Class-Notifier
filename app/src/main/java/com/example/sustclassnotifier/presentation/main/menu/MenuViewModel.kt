package com.example.sustclassnotifier.presentation.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sustclassnotifier.domain.event.MenuUIEvent
import com.example.sustclassnotifier.domain.repository.AuthenticationRepository
import com.example.sustclassnotifier.navigation.ClassMateAppRouter
import com.example.sustclassnotifier.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val authRepo: AuthenticationRepository
) : ViewModel() {

    fun onMenuEvent(event: MenuUIEvent) {

        when (event) {
            MenuUIEvent.ProfileButtonClicked -> {
                ClassMateAppRouter.navigateTo(Screen.ProfileScreen)
            }
            MenuUIEvent.RoutineButtonClicked -> {
                ClassMateAppRouter.navigateTo(Screen.RoutineScreen)
            }
            MenuUIEvent.SignOutButtonClicked -> {
                signOut()
            }
        }
    }

    private fun signOut() = viewModelScope.launch {
        authRepo.signOut().collectLatest {

        }
    }
}