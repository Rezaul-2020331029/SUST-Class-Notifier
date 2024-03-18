package com.example.sustclassnotifier.domain.repository

import com.example.sustclassnotifier.data.model.Event
import com.example.sustclassnotifier.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun createEvent (event: Event): Flow<DataState<Event>>

    fun getEventList (courseId: String, whichEvent: String): Flow<List<Event>>

    fun deleteEvent (event: Event): Flow<DataState<Event>>

}