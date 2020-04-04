package com.aps.mobile.model

data class User(
    var id: Long,
    var username: String,
    var email: String?,
    var role: UserRole
)
