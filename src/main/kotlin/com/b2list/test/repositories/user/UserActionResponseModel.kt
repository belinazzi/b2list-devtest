package com.b2list.test.repositories.user

data class UserActionResponseModel(
    val error: Boolean,
    val items: MutableList<String>
)
