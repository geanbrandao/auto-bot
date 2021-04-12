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
}
/**
 * sempre deixa uma linha vazia ao fim do arquivo, também é uma convenção
 */