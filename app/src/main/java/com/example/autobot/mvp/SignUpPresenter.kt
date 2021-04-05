package com.example.autobot.mvp

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.autobot.extensions.*

class SignUpPresenter(private var view: SignUpContract.View?) : SignUpContract.Presenter {

    private var _isValidEmail: Boolean = false
    private var _isValidName: Boolean = false
    private var _isValidNewPassword: Boolean = false
    private var _isValidConfirmPassword: Boolean = false
    private var _isPasswordsMatchs: Boolean = false

    override fun isValid() {
        view?.displaySuccessToast()
    }

    override fun isValidEmail(
        email: String,
        labelError: AppCompatTextView,
        input: AppCompatEditText
    ) {
        val errorMessage = email.isValidEmail()
        _isValidEmail = errorMessage.isEmpty()
        _isValidEmail.handleWithErrorOnInput(labelError, input, errorMessage)
        view?.enableButtonCreate(isEnabled = isEnabled())
    }

    override fun isValidName(
        name: String,
        labelError: AppCompatTextView,
        input: AppCompatEditText
    ) {
        val errorMessage = name.isValidName()
        _isValidName = errorMessage.isEmpty()
        _isValidName.handleWithErrorOnInput(labelError, input, errorMessage)
        view?.enableButtonCreate(isEnabled = isEnabled())
    }

    override fun isValidNewPassword(
        newPassword: String,
        labelErrorNewPassword: AppCompatTextView,
        inputNewPassword: AppCompatEditText,
    ) {
        val errorMessageNewPassword = newPassword.isValidPassword()
        _isValidNewPassword = errorMessageNewPassword.isEmpty()
        _isValidNewPassword.handleWithErrorOnInput(
            labelErrorNewPassword,
            inputNewPassword,
            errorMessageNewPassword
        )
        view?.enableButtonCreate(isEnabled = isEnabled())
    }

    override fun isValidConfirmPassword(
        confirmPassword: String,
        labelErrorConfirmPassword: AppCompatTextView,
        inputConfirmPassword: AppCompatEditText,
    ) {
        val errorMessageConfirmPassword = confirmPassword.isValidPassword()
        _isValidConfirmPassword = errorMessageConfirmPassword.isEmpty()
        _isValidConfirmPassword.handleWithErrorOnInput(
            labelErrorConfirmPassword,
            inputConfirmPassword,
            errorMessageConfirmPassword
        )
        view?.enableButtonCreate(isEnabled = isEnabled())
    }

    override fun isPasswordMatchs(
        newPassword: String,
        confirmPassword: String,
        labelErrorConfirmPassword: AppCompatTextView,
        inputConfirmPassword: AppCompatEditText
    ) {
        // than check if they match
        val errorMessageMatchs = newPassword.isPasswordsMatchs(confirmPassword)
        _isPasswordsMatchs = errorMessageMatchs.isEmpty()
        _isPasswordsMatchs.handleWithErrorOnInput(labelErrorConfirmPassword, inputConfirmPassword, errorMessageMatchs)
        view?.enableButtonCreate(isEnabled = isEnabled())
    }

    override fun onDestroy() {
        view = null
    }

    private fun Boolean.handleWithErrorOnInput(
        labelError: AppCompatTextView,
        input: AppCompatEditText,
        errorMessage: String
    ) {
        if (this) {
            view?.hideErrorOnInput(labelError = labelError, input = input)
        } else {
            view?.showErrorOnInput(
                errorMessage = errorMessage,
                labelError = labelError,
                input = input
            )
        }
    }

    private fun isEnabled(): Boolean {
        return _isValidEmail and _isValidName and _isValidNewPassword and _isValidConfirmPassword and _isPasswordsMatchs
    }
}