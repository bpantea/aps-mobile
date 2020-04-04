package com.aps.mobile

import android.app.Application
import com.aps.mobile.security.AuthenticationInit


class ApsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val authentication = AuthenticationInit(this)
        authentication.authenticationHandling()
    }
}
