package com.example.sustclassnotifier.presentation.main.menu.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sustclassnotifier.data.model.ClassDetails
import com.example.sustclassnotifier.domain.event.RoutineUIEvent
import com.example.sustclassnotifier.domain.repository.SearchRepository
import com.example.sustclassnotifier.navigation.ClassMateAppRouter
import com.example.sustclassnotifier.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val searchRepo: SearchRepository,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _weekDay = MutableStateFlow("")
    val weekDay = _weekDay.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _classes = MutableStateFlow<Set<ClassDetails>>(emptySet())


    @OptIn(FlowPreview::class)
    val classes = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_classes) { text, classList ->
            if (text.isBlank()) {
                classList.filter {
                    it.doesMatchWeekDay(it.weekDay)
                }
            } else {
                classList.filter {
                    it.doesMatchSearchQuery(query = text, weekDay =  it.weekDay)
                }
            }
        }.onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _classes.value
        )
    init {
        getAllClasses()
    }

    private fun getAllClasses() = viewModelScope.launch {
        searchRepo.getAllClasses().collectLatest {
            _classes.value = it
        }
    }

    fun onRoutineEvent(event: RoutineUIEvent) {
        when (event) {
            RoutineUIEvent.RoutineBackButtonClicked -> {
                ClassMateAppRouter.navigateTo(Screen.MenuScreen)
            }
            is RoutineUIEvent.SearchTextChanged -> {
                _searchText.value = event.text
            }
            is RoutineUIEvent.WeekDayChanged -> {
                _weekDay.value = event.weekDay
            }
        }
    }

    companion object {
        const val TAG = "SearchCourseViewModel"
    }
}