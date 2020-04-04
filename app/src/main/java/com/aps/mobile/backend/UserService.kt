package com.aps.mobile.backend

import com.aps.mobile.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("$userApiRoute")
    suspend fun getFullUser(): User
}
