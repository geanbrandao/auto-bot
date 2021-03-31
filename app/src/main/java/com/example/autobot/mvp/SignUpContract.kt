package com.example.autobot.mvp

interface SignUpContract {

    interface View : BaseView<SignUpPresenter> {
        fun displayErrorMessage()
        fun displaySuccessToast()
        fun goToHomeFragment()
    }

    interface Presenter : BasePresenter {
        fun isValid()
    }
}