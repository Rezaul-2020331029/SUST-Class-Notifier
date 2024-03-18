package com.example.sustclassnotifier.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.example.sustclassnotifier.core.Constants.EVENTS
import com.example.sustclassnotifier.core.GetModelFromDocument.getEventFromFirestoreDocument
import com.example.sustclassnotifier.data.model.Event
import com.example.sustclassnotifier.domain.repository.EventRepository
import com.example.sustclassnotifier.domain.state.DataState
import com.example.sustclassnotifier.strings.ASSIGNMENTS_COLLECTION
import com.example.sustclassnotifier.strings.TERM_TESTS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore,
) : EventRepository {

    private val termTestCollection = firestoreRef.collection(TERM_TESTS_COLLECTION)
    private val assignmentCollection = firestoreRef.collection(ASSIGNMENTS_COLLECTION)
    override fun createEvent(event: Event): Flow<DataState<Event>> = flow {

        emit(DataState.Loading)

        val eventId = "${event.department}:${event.courseCode}:${event.eventNo}"

        if (event.type == EVENTS[0]) {
            termTestCollection.document(eventId).set(event.toMap()).await()
        } else {
            assignmentCollection.document(eventId).set(event.toMap()).await()
        }

        emit(DataState.Success(event))
    }.catch {
        Log.d(TAG, "createTermTest: ${it.localizedMessage}")
    }

    override fun getEventList(courseId: String, whichEvent: String): Flow<List<Event>> =
        callbackFlow {

            val collectionRef = if (whichEvent == EVENTS[0]) {
                termTestCollection
            } else {
                assignmentCollection
            }

            val snapshotListener = collectionRef.addSnapshotListener { value, error ->
                val eventList = mutableListOf<Event>()
                value?.documents?.forEach {
                    if (it.id.contains(courseId)) {
                        val event = getEventFromFirestoreDocument(it)
                        eventList.add(event)
                    }
                }

                trySend(eventList).isSuccess
            }

            awaitClose {
                snapshotListener.remove()
            }

        }.catch {
            Log.d(TAG, "getEventList: ${it.localizedMessage}")
        }

    override fun deleteEvent(event: Event): Flow<DataState<Event>> = flow<DataState<Event>> {
        emit(DataState.Loading)

        val eventId = "${event.department}:${event.courseCode}:${event.eventNo}"

        if (event.type == EVENTS[0]) {
            termTestCollection.document(eventId).delete().await()
        } else {
            assignmentCollection.document(eventId).delete().await()
        }
        
        emit(DataState.Success(event))
        
    }.catch {
        Log.d(TAG, "deleteEvent: ${it.localizedMessage}")
    }


    companion object {
        const val TAG = "EventRepositoryImpl"
    }

}