package com.example.sustclassnotifier.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.example.sustclassnotifier.core.GetModelFromDocument.getClassDetailsFromFirestoreDocument
import com.example.sustclassnotifier.data.model.ClassDetails
import com.example.sustclassnotifier.domain.repository.ClassDetailsRepository
import com.example.sustclassnotifier.domain.state.DataState
import com.example.sustclassnotifier.strings.ACTIVE_STATUS
import com.example.sustclassnotifier.strings.CLASSES_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClassDetailsRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore
): ClassDetailsRepository{

    private val classesCollection = firestoreRef.collection(CLASSES_COLLECTION)

    override fun createClass(classDetails: ClassDetails): Flow<DataState<ClassDetails>> = flow{
        emit(DataState.Loading)
        val classId = "${classDetails.classDepartment}:${classDetails.classCourseCode}:${classDetails.classNo}"
        classesCollection.document(classId).set(classDetails.toMap()).await()
        emit(DataState.Success(classDetails))
    }.catch {
        Log.d(TAG, "createClass: ${it.message}")
    }

    override fun deleteClass(classDetails: ClassDetails): Flow<DataState<ClassDetails>> = flow{
        emit(DataState.Loading)
        val classId = "${classDetails.classDepartment}:${classDetails.classCourseCode}:${classDetails.classNo}"
        classesCollection.document(classId).delete().await()
        emit(DataState.Success(classDetails))
    }.catch {
        Log.d(TAG, "deleteClass: ${it.localizedMessage}")
    }

    override fun getClassesForSingleCourse(courseId: String): Flow<List<ClassDetails>> = callbackFlow {

        val snapshotListener = classesCollection.addSnapshotListener { value, error ->
            val classList = mutableListOf<ClassDetails>()
            value?.documents?.forEach {
                if (it.exists() && it != null && it.id.contains(courseId)) {
                    val details = getClassDetailsFromFirestoreDocument(it)
                    classList.add(details)
                }
            }

            trySend(classList).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getClasses: ${it.localizedMessage}")
    }

    override fun getClassesForMultipleCourse(courseIds: List<String>): Flow<List<ClassDetails>> = callbackFlow {
        val snapshotListener = classesCollection.addSnapshotListener { value, error ->
            val classList = mutableListOf<ClassDetails>()
            courseIds.forEach {courseId ->
                value?.documents?.forEach {
                    if (it.exists() && it != null && it.id.contains(courseId)) {
                        val details = getClassDetailsFromFirestoreDocument(it)
                        classList.add(details)
                    }
                }
            }
            trySend(classList).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getClassesForMultipleCourse: ${it.localizedMessage}")
    }

    override fun changeActiveStatus(
        classDetails: ClassDetails,
        status: Boolean,
    ): Flow<DataState<ClassDetails>> = flow {
        emit(DataState.Loading)
        val classId = "${classDetails.classDepartment}:${classDetails.classCourseCode}:${classDetails.classNo}"
        classesCollection.document(classId).update(ACTIVE_STATUS, status).await()
        emit(DataState.Success(classDetails))
    }.catch {
        emit(DataState.Error("Unable to update class status"))
        Log.d(TAG, "changeActiveStatus: ${it.localizedMessage}")
    }

    companion object {
        const val TAG = "ClassDetailsRepositoryImpl"
    }
}