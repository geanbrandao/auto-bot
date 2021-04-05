package com.example.autobot.extensions

import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_FIRST_NAME
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_INVALID_EMAIL
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_NEED_TWO_NAMES
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_PASSWORDS_DO_NOT_MATCH
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_PASSWORD_MIN_LENGHT
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_REQUIRED_FIELD
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_SECOND_NAME
import com.example.autobot.ui.signup.SignupFragment.Companion.NO_ERROR_MESSAGE

fun String.isValidName(): String {
    if (this.trim().isEmpty()) return ERROR_MESSAGE_REQUIRED_FIELD
    val arrayName = this.trim().split(" ")
    if (arrayName.size < 2) return ERROR_MESSAGE_NEED_TWO_NAMES
    if (arrayName[0].length <= 2) return ERROR_MESSAGE_FIRST_NAME
    if (arrayName[1].length <= 2) return ERROR_MESSAGE_SECOND_NAME
    return NO_ERROR_MESSAGE
}

fun String.isValidEmail(): String {
    if (this.trim().isEmpty()) return ERROR_MESSAGE_REQUIRED_FIELD
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
        return ERROR_MESSAGE_INVALID_EMAIL
    }
    return NO_ERROR_MESSAGE
}

fun String.isValidPassword(): String {
    if (this.trim().isEmpty()) return ERROR_MESSAGE_REQUIRED_FIELD
    if (this.length < 6) return ERROR_MESSAGE_PASSWORD_MIN_LENGHT
    return NO_ERROR_MESSAGE
}

fun String.isPasswordsMatchs(confirmPassword: String): String {
    if (this.trim().isEmpty() || confirmPassword.trim().isEmpty()) return ERROR_MESSAGE_REQUIRED_FIELD
    if (this != confirmPassword) return ERROR_MESSAGE_PASSWORDS_DO_NOT_MATCH
    return NO_ERROR_MESSAGE
}