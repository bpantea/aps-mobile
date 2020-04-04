package com.aps.mobile.security.backend

import com.aps.mobile.core.userApiRoute
import com.aps.mobile.user.domain.UserDto
import retrofit2.Call
import retrofit2.http.GET

interface AuthService {
    @GET("$userApiRoute/current-user")
    fun getCurrentUser(): Call<UserDto?>
}
