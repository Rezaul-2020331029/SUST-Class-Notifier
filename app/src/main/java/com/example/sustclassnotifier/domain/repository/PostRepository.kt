package com.example.sustclassnotifier.domain.repository

import com.example.sustclassnotifier.data.model.Post
import com.example.sustclassnotifier.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun createPost(post: Post): Flow<DataState<Post>>
    fun getPosts(): Flow<List<Post>>
    fun deletePost(post: Post): Flow<DataState<Post>>

}