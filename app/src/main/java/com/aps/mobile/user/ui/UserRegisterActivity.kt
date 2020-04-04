package com.aps.mobile.user.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aps.mobile.R
import com.aps.mobile.core.utils.showLongText
import com.aps.mobile.user.domain.UserSavingDto
import com.aps.mobile.user.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_user_register.*

class UserRegisterActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    fun register(view: View) {
        val user = UserSavingDto()
        user.email = emailText.text.toString()
        user.firstName = firstNameText.text.toString()
        user.lastName = lastNameText.text.toString()
        user.password = passwordText.text.toString()
        user.matchingPassword = matchingPasswordText.text.toString()
        userViewModel.registerUser(user)
    }
}
