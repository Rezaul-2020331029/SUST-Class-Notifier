package com.example.sustclassnotifier.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.example.sustclassnotifier.core.GetModelFromDocument.getClassDetailsFromFirestoreDocument
import com.example.sustclassnotifier.core.GetModelFromDocument.getCourseFromFirestoreDocument
import com.example.sustclassnotifier.core.GetModelFromDocument.getUserFromFirestoreDocument
import com.example.sustclassnotifier.data.model.ClassDetails
import com.example.sustclassnotifier.data.model.Course
import com.example.sustclassnotifier.data.model.User
import com.example.sustclassnotifier.domain.repository.SearchRepository
import com.example.sustclassnotifier.strings.CLASSES_COLLECTION
import com.example.sustclassnotifier.strings.COURSES_COLLECTION
import com.example.sustclassnotifier.strings.TEACHER
import com.example.sustclassnotifier.strings.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore
): SearchRepository {
    override fun getAllTeachers(): Flow<Set<User>> = callbackFlow {

        val snapshotListener = firestoreRef.collection(USERS_COLLECTION)
            .addSnapshotListener { value, error ->
                val teachers = mutableSetOf<User>()
                if (value != null) {
                    value.documents.forEach {
                        val user = getUserFromFirestoreDocument(it)

                        if (user.role == TEACHER) {
                            teachers.add(user)
                        }
                    }
                } else if(error != null) {
                    Log.d(TAG, "getAllTeachers: $error")
                }

                trySend(teachers).isSuccess
            }

        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getAllTeachers: $it")
    }

    
    
    
    
    override fun getAllCourses(): Flow<Set<Course>> = callbackFlow{

        
        val snapshotListener = firestoreRef.collection(COURSES_COLLECTION)
            .addSnapshotListener { value, error ->
                val courses = mutableSetOf<Course>()
                if (value != null) {
                    value.documents.forEach {
                        val course = getCourseFromFirestoreDocument(it)

                        if (!course.pendingStatus) {
                            courses.add(course)
                        }
                    }
                } else if(error != null) {
                    Log.d(TAG, "getAllTeachers: $error")
                }

                trySend(courses).isSuccess
            }
        
        awaitClose { 
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getAllCourses: $it")
    }

    override fun getAllClasses(): Flow<Set<ClassDetails>> = callbackFlow {
        val snapshotListener = firestoreRef.collection(CLASSES_COLLECTION)
            .addSnapshotListener { value, error ->
                val classSet = mutableSetOf<ClassDetails>()
                value?.documents?.forEach {
                    val details = getClassDetailsFromFirestoreDocument(it)
                    classSet.add(details)
                }
                trySend(classSet).isSuccess
            }

        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getAllClasses: ${it.localizedMessage}")
    }


    companion object {
        const val TAG = "SearchRepositoryImpl"
    }
}