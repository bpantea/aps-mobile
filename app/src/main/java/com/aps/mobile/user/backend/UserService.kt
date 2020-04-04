package com.aps.mobile.user.backend

import com.aps.mobile.core.userApiRoute
import com.aps.mobile.user.domain.UserDto
import com.aps.mobile.user.domain.UserSavingDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("$userApiRoute/register")
    suspend fun registerUser(@Body user: UserSavingDto): Void

    @GET("$userApiRoute/current-user")
    suspend fun getCurrentUser(): UserDto?
}
