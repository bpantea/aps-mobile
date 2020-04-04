package com.aps.mobile.security

import android.content.Context
import com.aps.mobile.core.RetrofitProvider
import com.aps.mobile.core.utils.startActivityAsNewTask
import com.aps.mobile.security.backend.AuthService
import com.aps.mobile.security.domain.LoginUserPersistence
import com.aps.mobile.security.ui.LoginActivity
import com.aps.mobile.ui.MainActivity
import com.aps.mobile.user.domain.UserDto
import com.aps.mobile.user.view_model.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationInitialization(val context: Context) {
    fun authenticationHandling() {
        val authService =
            RetrofitProvider.createService(context, AuthService::class.java)
        val loginUserPersistence = LoginUserPersistence(context)

        authService.getCurrentUser().enqueue(object : Callback<UserDto?> {
            override fun onFailure(call: Call<UserDto?>, t: Throwable?) {
                throw IllegalStateException("This is an open endpoint, should not fail.")
            }

            override fun onResponse(call: Call<UserDto?>, response: Response<UserDto?>) {
                if (response.code() == 401) {
                    loginUserPersistence.clear()
                }
                UserViewModel.updateAuthenticatedUser(response.body())
                goToRightActivity(response.body())
            }
        })
    }

    private fun goToRightActivity(user: UserDto?) {
        val activity: Class<*> = if (user != null) {
            MainActivity::class.java
        } else {
            LoginActivity::class.java
        }
        startActivityAsNewTask(context, activity)
    }
}
