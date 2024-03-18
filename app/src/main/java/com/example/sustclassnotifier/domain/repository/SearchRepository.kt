package com.example.sustclassnotifier.domain.repository


import com.example.sustclassnotifier.data.model.ClassDetails
import com.example.sustclassnotifier.data.model.Course
import com.example.sustclassnotifier.data.model.User
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getAllTeachers(): Flow<Set<User>>
    fun getAllCourses(): Flow<Set<Course>>
    fun getAllClasses(): Flow<Set<ClassDetails>>
}