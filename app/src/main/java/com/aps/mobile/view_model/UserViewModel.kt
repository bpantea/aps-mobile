package com.aps.mobile.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.aps.mobile.backend.UserService
import com.aps.mobile.utils.RetrofitProvider

class UserViewModel (application: Application) : AndroidViewModel(application) {

    private val userService = RetrofitProvider.createService(application, UserService::class.java)

    companion object {

    }

    fun getFullUser() {

    }
}