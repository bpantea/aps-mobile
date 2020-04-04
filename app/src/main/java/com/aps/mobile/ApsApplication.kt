package com.aps.mobile

import android.app.Application
import com.aps.mobile.security.AuthenticationInitialization
import com.aps.mobile.core.RetrofitProvider

class ApsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitProvider.createInstance(this)
        AuthenticationInitialization(this).authenticationHandling()
    }
}
