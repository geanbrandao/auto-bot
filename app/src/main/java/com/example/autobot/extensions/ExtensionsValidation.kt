package com.example.autobot.extensions

import com.example.autobot.constants.Constants.MASK_PHONE_NUMBER
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_FIRST_NAME
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_INVALID_PHONE
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

fun String.isValidPhone(): String {
    if (this.trim().isEmpty()) return ERROR_MESSAGE_REQUIRED_FIELD
    if (this.length < MASK_PHONE_NUMBER.length ) { //55981393424
        return ERROR_MESSAGE_INVALID_PHONE
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