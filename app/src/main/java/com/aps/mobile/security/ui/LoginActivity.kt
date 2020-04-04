package com.aps.mobile.security.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aps.mobile.R
import com.aps.mobile.user.ui.UserRegisterActivity
import com.aps.mobile.user.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    fun login(view: View) {
        val username = usernameText.text.toString()
        val password = passwordText.text.toString()
        userViewModel.login(username, password)
    }

    fun goToRegister(view: View) {
        val intent = Intent(this, UserRegisterActivity::class.java)
        startActivity(intent)
    }
}
