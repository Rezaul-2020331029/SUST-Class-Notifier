package com.example.sustclassnotifier.presentation.main.create_semester


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sustclassnotifier.components.ErrorScreen
import com.example.sustclassnotifier.components.LoadingScreen
import com.example.sustclassnotifier.domain.state.DataState
import com.example.sustclassnotifier.presentation.main.create_semester.components.CreateSemesterContent


@Composable
fun CreateSemesterScreen(
    createSemesterViewModel: CreateSemesterViewModel = viewModel()
) {
    val userState by createSemesterViewModel.userState.collectAsState()
    when (userState) {

        is DataState.Error -> ErrorScreen(error = userState.error.toString())

        DataState.Loading -> LoadingScreen()

        is DataState.Success ->  {
            userState.data?.let {
                CreateSemesterContent(
                    createSemesterViewModel = createSemesterViewModel,
                )
            }
        }


    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CreateSemesterScreenPreview() {
    CreateSemesterScreen()
}