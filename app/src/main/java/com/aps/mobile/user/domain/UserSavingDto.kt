package com.aps.mobile.user.domain

data class UserSavingDto(
    var id: Long? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,
    var matchingPassword: String? = null
)
