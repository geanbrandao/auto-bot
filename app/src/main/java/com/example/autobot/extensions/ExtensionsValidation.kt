package com.example.autobot.extensions

import com.example.autobot.constants.Constants
import com.example.autobot.constants.Constants.ERROR_MESSAGE_FIRST_NAME
import com.example.autobot.constants.Constants.ERROR_MESSAGE_INVALID_PHONE
import com.example.autobot.constants.Constants.ERROR_MESSAGE_NEED_TWO_NAMES
import com.example.autobot.constants.Constants.ERROR_MESSAGE_PASSWORDS_DO_NOT_MATCH
import com.example.autobot.constants.Constants.ERROR_MESSAGE_PASSWORD_MIN_LENGHT
import com.example.autobot.constants.Constants.ERROR_MESSAGE_REQUIRED_FIELD
import com.example.autobot.constants.Constants.ERROR_MESSAGE_SECOND_NAME
import com.example.autobot.constants.Constants.ERROR_MESSAGE_SMS_CODE_LENGHT
import com.example.autobot.constants.Constants.MASK_PHONE_NUMBER
import com.example.autobot.constants.Constants.NO_ERROR_MESSAGE
import com.example.autobot.utils.AESEncyption
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import timber.log.Timber

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
    if (this.length < MASK_PHONE_NUMBER.length) { //55981393424
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
    if (this.trim().isEmpty() || confirmPassword.trim()
            .isEmpty()
    ) return ERROR_MESSAGE_REQUIRED_FIELD
    if (this != confirmPassword) return ERROR_MESSAGE_PASSWORDS_DO_NOT_MATCH
    return NO_ERROR_MESSAGE
}

fun String.isValidSMSCode(): String {
    if (this.trim().isEmpty()) return ERROR_MESSAGE_REQUIRED_FIELD
    if (this.length < 6) return ERROR_MESSAGE_SMS_CODE_LENGHT
    return NO_ERROR_MESSAGE
}

fun createJwtForNewUser(phone: String, password: String): String {
    val jwt = Jwts.builder()
        .claim(Constants.JWT_FIELD_PHONE, phone)
        .claim(Constants.JWT_FIELD_PASSWORD, password)
        .signWith(SignatureAlgorithm.HS256, AESEncyption.encrypt(Constants.JWT_SECRET_KEY))
        .compact()

    Timber.d("DEBUG3 - JWT $jwt")
    return jwt
}
