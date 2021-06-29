package com.example.autobot.mvp

interface BaseView<T> {

    /**
     * faz sentido ter um presenter nulo? então pode mudar para val
     */
    var presenter: T
}
/**
 * sempre deixa uma linha vazia ao fim do arquivo, também é uma convenção
 */