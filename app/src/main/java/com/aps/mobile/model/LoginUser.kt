package com.aps.mobile.model

import android.content.Context
import android.content.SharedPreferences
import com.aps.mobile.R

data class LoginUser(
    var username: String,
    var password: String
)

const val usernamePreferencesKey = "username"
const val passwordPreferencesKey = "password"

class LoginUserPersistence(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    fun fetch(): LoginUser {
        val username = sharedPreferences.getString(usernamePreferencesKey, "")
        val password = sharedPreferences.getString(passwordPreferencesKey, "")
        return LoginUser(username!!, password!!)
    }

    fun save(user: LoginUser) {
        val editor = sharedPreferences.edit()
        editor.putString(usernamePreferencesKey, user.username)
        editor.putString(passwordPreferencesKey, user.password)
        editor.apply()
    }

    fun clear() {
        save(LoginUser("", ""))
    }
}
