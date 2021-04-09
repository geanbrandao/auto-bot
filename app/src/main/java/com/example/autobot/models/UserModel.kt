package com.example.autobot.models

import java.io.Serializable

data class UserModel(
    val phone: String,
    val name: String,
    val password: String
): Serializable
