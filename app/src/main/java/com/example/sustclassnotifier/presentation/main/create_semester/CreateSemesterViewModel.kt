package com.example.sustclassnotifier.presentation.main.create_semester

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sustclassnotifier.data.model.Course
import com.example.sustclassnotifier.data.model.User
import com.example.sustclassnotifier.domain.event.CreateSemesterUIEvent
import com.example.sustclassnotifier.domain.repository.CourseRepository
import com.example.sustclassnotifier.domain.repository.UserRepository
import com.example.sustclassnotifier.domain.state.DataState
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
class CreateSemesterViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val courseRepo: CourseRepository,
) : ViewModel() {

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    val userState = _userState.asStateFlow()


    private val _createdCourses = MutableStateFlow<List<Course>>(listOf())
    val cratedCourses = _createdCourses.asStateFlow()

    private val _pendingCourses = MutableStateFlow<List<Course>>(listOf())
    val pendingCourses = _pendingCourses.asStateFlow()

    private val _courses = MutableStateFlow<List<Course>>(listOf())
    val courses = _courses.asStateFlow()

    fun onCreateSemesterEvent(event: CreateSemesterUIEvent) {

        when (event) {
            CreateSemesterUIEvent.CreateSemesterFABClick -> {
                ClassMateAppRouter.navigateTo(Screen.CreateCourse)
            }

            is CreateSemesterUIEvent.EditCourseUIEvent -> {

            }

            is CreateSemesterUIEvent.DeleteCourseSwipe -> {
                deleteCourse(event.course)
            }

            is CreateSemesterUIEvent.DisplayCourseSwipe -> {
                ClassMateAppRouter.navigateTo(Screen.CourseDetailsDisplay(event.course, event.screen))
            }

            else -> {}
        }

    }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUser(userRepo.currentUser.email!!)
        }
    }

    private fun getUser(email: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.getUser(email).collect {
            _userState.value = it
        }
    }

    fun getCreatedCourses() = viewModelScope.launch {
        userState.value.data?.let { user ->
            courseRepo.getCreatedCourses(user.courses, user.email).collectLatest {
                _courses.value = it
            }
        }
    }

    fun getPendingCourses() = viewModelScope.launch {
        userState.value.data?.let {
            courseRepo.getPendingCourseList(it.courses).collectLatest {
                _pendingCourses.value = it
            }
        }
    }

    private fun deleteCourse(course: Course) = viewModelScope.launch(Dispatchers.IO) {
        courseRepo
            .deleteCourse(course)
            .collectLatest {

            }
    }



    private fun validateAllStates() {

    }

    companion object {
        private const val TAG = "CreateSemesterViewModel"
    }
}