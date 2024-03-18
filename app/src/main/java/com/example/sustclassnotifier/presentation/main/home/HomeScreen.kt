package com.example.sustclassnotifier.presentation.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sustclassnotifier.components.LoadingScreen
import com.example.sustclassnotifier.domain.state.DataState
import com.example.sustclassnotifier.presentation.main.home.components.HomeScreenContent

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    val userState by homeViewModel.userState.collectAsState()

    when (userState) {
        is DataState.Error -> TODO()
        DataState.Loading -> {
          //  LoadingScreen()
        }
        is DataState.Success -> {
            userState.data?.let { user ->
                homeViewModel.getCourseList(user.courses)
                homeViewModel.getRequestedCourseList(user.requestedCourses)
                homeViewModel.getClassDetails(user.courses)
                homeViewModel.getPosts()
                HomeScreenContent(homeViewModel = homeViewModel, user = user)
            }
        }
    }

}