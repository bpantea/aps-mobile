package com.aps.mobile.backend

import com.aps.mobile.backend.userApiRoute
import retrofit2.Call
import retrofit2.http.GET

interface AuthService {
    @GET("$userApiRoute/logged-in")
    fun checkIfLoggedIn(): Call<Boolean>
}
