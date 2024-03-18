package com.example.sustclassnotifier.domain.repository

import com.example.sustclassnotifier.data.model.ResourceLink
import com.example.sustclassnotifier.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface ResourceLinkRepository {

    fun createResource(resourceLink: ResourceLink): Flow<DataState<ResourceLink>>
    fun getResources(courseId: String): Flow<List<ResourceLink>>
    fun deleteResource(resourceLink: ResourceLink): Flow<DataState<ResourceLink>>

}