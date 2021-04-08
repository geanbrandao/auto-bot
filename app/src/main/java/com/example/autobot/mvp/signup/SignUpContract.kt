package com.example.autobot.mvp.signup

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.autobot.mvp.BasePresenter
import com.example.autobot.mvp.BaseView

interface SignUpContract {

    interface View : BaseView<SignUpPresenter> {
        fun displayErrorMessage()
        fun displaySuccessToast()
        fun goToSMSCodeValidationScreen()
        fun enableButtonCreate(isEnabled: Boolean)
        fun showErrorOnInput(
            errorMessage: String,
            labelError: AppCompatTextView,
            input: AppCompatEditText
        )

        fun hideErrorOnInput(labelError: AppCompatTextView, input: AppCompatEditText)
        fun fillPhoneInputWithMask(phoneMasked: String)
    }

    interface Presenter : BasePresenter {
        fun isValid()
        fun isValidName(name: String, labelError: AppCompatTextView, input: AppCompatEditText)
        fun isValidPhone(phone: String, labelError: AppCompatTextView, input: AppCompatEditText)
        fun formatPhoneInput(phone: String)
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