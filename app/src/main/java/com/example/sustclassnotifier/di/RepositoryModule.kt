package com.example.sustclassnotifier.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.sustclassnotifier.data.repository.AuthenticationRepositoryImpl
import com.example.sustclassnotifier.data.repository.ClassDetailsRepositoryImpl
import com.example.sustclassnotifier.data.repository.CourseRepositoryImpl
import com.example.sustclassnotifier.data.repository.EventRepositoryImpl
import com.example.sustclassnotifier.data.repository.PostRepositoryImpl
import com.example.sustclassnotifier.data.repository.ResourceLinkRepositoryImpl
import com.example.sustclassnotifier.data.repository.UserRepositoryImpl
import com.example.sustclassnotifier.domain.repository.AuthenticationRepository
import com.example.sustclassnotifier.data.repository.SearchRepositoryImpl
import com.example.sustclassnotifier.domain.repository.ClassDetailsRepository
import com.example.sustclassnotifier.domain.repository.CourseRepository
import com.example.sustclassnotifier.domain.repository.EventRepository
import com.example.sustclassnotifier.domain.repository.PostRepository
import com.example.sustclassnotifier.domain.repository.ResourceLinkRepository
import com.example.sustclassnotifier.domain.repository.UserRepository
import com.example.sustclassnotifier.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesAuthenticationRepository(
        auth: FirebaseAuth, firestoreRef: FirebaseFirestore
    ): AuthenticationRepository = AuthenticationRepositoryImpl(
        auth, firestoreRef
    )

    @Provides
    @Singleton
    fun providesUserRepository(
        auth: FirebaseAuth, firestoreRef: FirebaseFirestore
    ): UserRepository = UserRepositoryImpl(
        auth, firestoreRef
    )

    @Provides
    @Singleton
    fun providesSearchRepository (
        firestoreRef: FirebaseFirestore
    ): SearchRepository = SearchRepositoryImpl(firestoreRef)

    @Provides
    @Singleton
    fun providesCourseRepository (
        firestoreRef: FirebaseFirestore
    ): CourseRepository = CourseRepositoryImpl (firestoreRef)

    @Provides
    @Singleton
    fun providesEventRepository (
        firestoreRef: FirebaseFirestore
    ): EventRepository = EventRepositoryImpl(firestoreRef)


    @Provides
    @Singleton
    fun providesClassDetailsRepository (
        firestoreRef: FirebaseFirestore
    ): ClassDetailsRepository = ClassDetailsRepositoryImpl (firestoreRef)

    @Provides
    @Singleton
    fun providesPostRepository (
        firestoreRef: FirebaseFirestore
    ): PostRepository = PostRepositoryImpl(firestoreRef)

    @Provides
    @Singleton
    fun providesResourceLinkRepository(
        firestoreRef: FirebaseFirestore
    ): ResourceLinkRepository = ResourceLinkRepositoryImpl(firestoreRef)
}