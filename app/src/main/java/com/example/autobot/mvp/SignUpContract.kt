package com.example.autobot.mvp

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView

interface SignUpContract {

    interface View : BaseView<SignUpPresenter> {
        fun displayErrorMessage()
        fun displaySuccessToast()
        fun goToHomeFragment()
        fun enableButtonCreate(isEnabled: Boolean)
        fun showErrorOnInput(
            errorMessage: String,
            labelError: AppCompatTextView,
            input: AppCompatEditText
        )

        fun hideErrorOnInput(labelError: AppCompatTextView, input: AppCompatEditText)
    }

    interface Presenter : BasePresenter {
        fun isValid()
        fun isValidName(name: String, labelError: AppCompatTextView, input: AppCompatEditText)
        fun isValidEmail(email: String, labelError: AppCompatTextView, input: AppCompatEditText)
        fun isValidNewPassword(
            newPassword: String,
            labelErrorNewPassword: AppCompatTextView,
            inputNewPassword: AppCompatEditText,
        )
        fun isValidConfirmPassword(
            confirmPassword: String,
            labelErrorConfirmPassword: AppCompatTextView,
            inputConfirmPassword: AppCompatEditText,
        )
        fun isPasswordMatchs(
            newPassword: String,
            confirmPassword: String,
            labelErrorConfirmPassword: AppCompatTextView,
            inputConfirmPassword: AppCompatEditText
        )
    }
}