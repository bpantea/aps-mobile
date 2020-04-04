package com.aps.mobile.user.domain

data class UserDto(
    var id: Long,
    var username: String,
    var email: String?,
    var role: UserRole
)
