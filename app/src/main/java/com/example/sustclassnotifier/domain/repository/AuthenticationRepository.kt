package com.example.sustclassnotifier.domain.repository

import com.example.sustclassnotifier.data.model.User
import com.example.sustclassnotifier.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun signIn(email: String, password: String): Flow<DataState<Boolean>>
    fun signUp(email: String, password: String, user: User): Flow<DataState<Boolean>>
    fun signOut(): Flow<DataState<Boolean>>

    fun resetPassword(email: String): Flow<DataState<Boolean>>
}