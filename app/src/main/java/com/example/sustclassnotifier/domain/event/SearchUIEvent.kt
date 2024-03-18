package com.example.sustclassnotifier.domain.event

sealed class SearchUIEvent {
    data class SearchTextChanged(val searchText: String): SearchUIEvent()
}