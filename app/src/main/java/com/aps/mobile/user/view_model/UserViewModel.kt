package com.aps.mobile.user.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aps.mobile.core.RetrofitProvider
import com.aps.mobile.core.utils.showLongText
import com.aps.mobile.core.utils.startActivityAsNewTask
import com.aps.mobile.security.domain.LoginUser
import com.aps.mobile.security.domain.LoginUserPersistence
import com.aps.mobile.security.ui.LoginActivity
import com.aps.mobile.ui.MainActivity
import com.aps.mobile.user.backend.UserService
import com.aps.mobile.user.domain.UserDto
import com.aps.mobile.user.domain.UserSavingDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userService = RetrofitProvider.createService(application, UserService::class.java)
    private val loginUserPersistence = LoginUserPersistence(application.applicationContext)

    companion object {
        val authenticatedUser = MutableLiveData<UserDto?>()

        fun updateAuthenticatedUser(user: UserDto?) {
            authenticatedUser.value = user
        }
    }

    fun registerUser(userSavingDto: UserSavingDto) {
        viewModelScope.launch {
            try {
                userService.registerUser(userSavingDto)
                updateAuthenticatedUser(userService.getCurrentUser())
            } catch (ex: HttpException) {
                showLongText(getApplication(), ex.message())
            }
        }
    }

    fun login(email: String, password: String) {
        loginUserPersistence.save(LoginUser(email, password))
        viewModelScope.launch {
            var user: UserDto?
            try {
                user = userService.getCurrentUser()
            } catch (ex: Exception) {
                user = null
            }
            if (user == null) {
                showLongText(getApplication(), "Wrong credentials!")
            } else {
                startActivityAsNewTask(getApplication(), MainActivity::class.java)
            }
            updateAuthenticatedUser(user)
        }
    }

    fun logout() {
        loginUserPersistence.clear()
        updateAuthenticatedUser(null)
        showLongText(getApplication(), "Logged out successfully")
        startActivityAsNewTask(getApplication(), LoginActivity::class.java)
    }
}
