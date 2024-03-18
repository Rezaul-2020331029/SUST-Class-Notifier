package com.example.sustclassnotifier.domain.event

sealed class MenuUIEvent {

    data object ProfileButtonClicked: MenuUIEvent()
    data object RoutineButtonClicked: MenuUIEvent()
    data object SignOutButtonClicked: MenuUIEvent()
}