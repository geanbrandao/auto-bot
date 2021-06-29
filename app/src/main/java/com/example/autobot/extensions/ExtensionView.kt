package com.example.autobot.extensions

import android.view.View

/**
 * por que não  não nomear a função como  visible() ?
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

/**
 * por que não nomear a função como gone() ?
 */
fun View.hide() {
    this.visibility = View.GONE
}
/**
 * sempre deixa uma linha vazia ao fim do arquivo, também é uma convenção
 */