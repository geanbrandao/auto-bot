package com.example.autobot.constants

import java.util.regex.Pattern

object Constants {
    val EMAIL_REGEX: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    const val MASK_PHONE_NUMBER = "(##) #####-####"
    const val NO_ERROR_MESSAGE = ""
    const val ERROR_MESSAGE_REQUIRED_FIELD = "Campo obrigatorio"
    const val ERROR_MESSAGE_NEED_TWO_NAMES = "Necessário nome e sobrenome"
    const val ERROR_MESSAGE_FIRST_NAME = "Primeiro nome precisa ter mais de 2 letras"
    const val ERROR_MESSAGE_SECOND_NAME = "Segundo nome precisa ter mais de 2 letras"

    const val ERROR_MESSAGE_INVALID_PHONE = "Número de celular inválido"

    const val ERROR_MESSAGE_PASSWORD_MIN_LENGHT = "A senha precisa ter no minimo 6 caracters"
    const val ERROR_MESSAGE_PASSWORDS_DO_NOT_MATCH = "Senhas não são iguais"

    const val ERROR_MESSAGE_SMS_CODE_LENGHT = "O código sms precisa ter 6 números"
}