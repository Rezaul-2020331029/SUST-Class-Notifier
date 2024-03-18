package com.example.sustclassnotifier.presentation.main.menu.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.components.LoadingScreen
import com.example.sustclassnotifier.domain.state.DataState
import com.example.sustclassnotifier.presentation.main.menu.profile.components.ProfileContent

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {

    val userState by profileViewModel.userState.collectAsState()

    when (userState) {
        is DataState.Error -> TODO()
        DataState.Loading -> {
          //  LoadingScreen()
        }
        is DataState.Success -> {
            userState.data?.let {
                ProfileContent(profileViewModel = profileViewModel, user = it)
            }
        }
    }

}