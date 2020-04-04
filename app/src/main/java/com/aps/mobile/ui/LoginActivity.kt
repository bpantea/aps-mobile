package com.aps.mobile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.aps.mobile.R
import com.aps.mobile.model.LoginUser
import com.aps.mobile.model.LoginUserPersistence
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var loginUserPersistence: LoginUserPersistence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginUserPersistence = LoginUserPersistence(this)
    }

    fun login(view: View) {
        val username = usernameText.text.toString()
        val password = passwordText.text.toString()
        loginUserPersistence.save(LoginUser(username, password))
    }

    fun logout(view: View) {
        loginUserPersistence.clear()
        Toast.makeText(this, "Logout succeeded ", Toast.LENGTH_SHORT).show()
    }
}
