package com.example.sustclassnotifier.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.example.sustclassnotifier.core.GetModelFromDocument.getUserFromFirestoreDocument
import com.example.sustclassnotifier.data.model.User
import com.example.sustclassnotifier.domain.repository.UserRepository
import com.example.sustclassnotifier.domain.state.DataState
import com.example.sustclassnotifier.strings.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestoreRef: FirebaseFirestore,
) : UserRepository {

    private val usersCollection = firestoreRef.collection(USERS_COLLECTION)
    override val currentUser: FirebaseUser
        get() = auth.currentUser!!


    override fun getUser(email: String): Flow<DataState<User>> = callbackFlow {
        var response: DataState<User> = DataState.Loading

        val snapshotListener = usersCollection.document(email)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    Log.d(TAG, "getUser: Document: ${value.data}")
                    val user = getUserFromFirestoreDocument(value)
                    Log.d(TAG, "getUser: User: $user")
                    response = DataState.Success(user)
                } else if (error != null) {
                    Log.d(TAG, "getUser: ${error.localizedMessage}")
                    response = DataState.Error(error.localizedMessage!!)
                } else {
                    Log.d(TAG, "getUser: Nothing")
                }

                trySend(response).isSuccess
            }

        awaitClose {
            snapshotListener.remove()
        }
    }


    override fun updateUser(user: User): Flow<DataState<User>> = flow {

        emit(DataState.Loading)
        usersCollection.document(user.email).update(user.toMap()).await()
        emit(DataState.Success(user))
    }.catch {

        Log.d(TAG, "updateUser: ${it.localizedMessage}")
    }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}