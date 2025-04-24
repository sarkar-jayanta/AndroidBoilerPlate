package com.androidboilerplate.data.remote

import com.androidboilerplate.data.remote.Repository
import com.androidboilerplate.data.api.ApiInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val projectService: ApiInterface) : Repository {

}