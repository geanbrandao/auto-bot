package com.example.autobot.mvp

import androidx.appcompat.widget.AppCompatTextView

interface SignUpContract {

    interface View : BaseView<SignUpPresenter> {
        fun displayErrorMessage()
        fun displaySuccessToast()
        fun goToHomeFragment()
        fun enableButtonCreate(isEnabled: Boolean)
        fun showErrorOnInput(errorMessage: String, labelError: AppCompatTextView)
        fun hideErrorOnInput(labelError: AppCompatTextView)
    }

    interface Presenter : BasePresenter {
        fun isValid()
        fun isValidName(name: String, labelError: AppCompatTextView)
    }
}