package com.gopal.notorebygopal.API

import com.gopal.notorebygopal.models.UserRequest
import com.gopal.notorebygopal.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    @POST("/users/signup")
    suspend fun signup(@Body userRequest : UserRequest) : Response<UserResponse>

    @POST("/users/signin")
    suspend fun signin(@Body userRequest : UserRequest) : Response<UserResponse>
}