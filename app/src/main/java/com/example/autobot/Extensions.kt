package com.example.autobot

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun String.isValidName(): String {
    if (this.trim().isEmpty()) return "Campo obrigatorio"
    val arrayName = this.trim().split(" ")
    if (arrayName.size < 2) return "Necessário nome e sobrenome"
    if (arrayName[0].length <= 2 ) return "Primeiro nome precisa ter mais de 2 letras"
    if (arrayName[1].length <= 2 ) return "Segundo nome precisa ter mais de 2 letras"
    return ""
}

fun View.show() {
    this.visibility = VISIBLE
}

fun View.hide() {
    this.visibility = GONE
}
