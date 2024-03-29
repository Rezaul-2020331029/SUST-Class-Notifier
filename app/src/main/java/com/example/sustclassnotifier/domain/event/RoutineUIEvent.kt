package com.example.sustclassnotifier.domain.event

sealed class RoutineUIEvent {

    data class WeekDayChanged(val weekDay: String): RoutineUIEvent()
    data class SearchTextChanged(val text: String): RoutineUIEvent()
    data object RoutineBackButtonClicked: RoutineUIEvent()
}