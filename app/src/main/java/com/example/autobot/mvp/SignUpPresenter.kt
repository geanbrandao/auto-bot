package com.example.autobot.mvp

class SignUpPresenter(private var view: SignUpContract.View?) : SignUpContract.Presenter {

    override fun isValid() {
        view?.displaySuccessToast()
    }

    override fun onDestroy() {
        view = null
    }
}