package com.example.autobot.mvp.signin

class SigninPresenter(private var view: SigninContract.View?) : SigninContract.Presenter {

    override fun onDestroy() {
        view = null
    }
}