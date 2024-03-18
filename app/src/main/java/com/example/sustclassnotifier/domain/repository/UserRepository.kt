package com.example.sustclassnotifier.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.example.sustclassnotifier.data.model.User
import com.example.sustclassnotifier.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val currentUser: FirebaseUser

    fun getUser(email: String): Flow<DataState<User>>

    fun updateUser(user: User): Flow<DataState<User>>
}