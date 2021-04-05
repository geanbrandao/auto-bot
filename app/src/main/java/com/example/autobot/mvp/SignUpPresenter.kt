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
        view?.enableButtonCreate(isEnabled = isEnabled())
        if (_isValidEmail) {
            view?.hideErrorOnInput(labelError = labelError, input = input)
        } else {
            view?.showErrorOnInput(
                errorMessage = errorMessage,
                labelError = labelError,
                input = input
            )
        }
    }

    override fun isValidName(
        name: String,
        labelError: AppCompatTextView,
        input: AppCompatEditText
    ) {
        val errorMessage = name.isValidName()
        _isValidName = errorMessage.isEmpty()
        view?.enableButtonCreate(isEnabled = isEnabled())
        if (_isValidName) {
            view?.hideErrorOnInput(labelError = labelError, input = input)
        } else {
            view?.showErrorOnInput(
                errorMessage = errorMessage,
                labelError = labelError,
                input = input
            )
        }
    }

    override fun isPasswordsValid(
        newPassword: String,
        confirmPassword: String,
        labelErrorNewPassword: AppCompatTextView,
        labelErrorConfirmPassword: AppCompatTextView,
        inputNewPassword: AppCompatEditText,
        inputConfirmPassword: AppCompatEditText
    ) {
        val errorMessageNewPassword = newPassword.isValidPassword()
        _isValidNewPassword = errorMessageNewPassword.isEmpty()
        val errorMessageConfirmPassword = confirmPassword.isValidPassword()
        _isValidConfirmPassword = errorMessageConfirmPassword.isEmpty()
        // first check if both passwors are ok
        // caso um dos dois estejam invalidos faz SÓ essa parte do codigo
        // pq não precisa dar match entre os dois
        if (!_isValidNewPassword or !_isValidConfirmPassword) {
            if (_isValidNewPassword) {
                view?.hideErrorOnInput(labelError = labelErrorNewPassword, input = inputNewPassword)
            } else {
                view?.showErrorOnInput(
                    errorMessage = errorMessageNewPassword,
                    labelError = labelErrorNewPassword,
                    input = inputNewPassword
                )
                return
            }
            if (_isValidConfirmPassword) {
                view?.hideErrorOnInput(
                    labelError = labelErrorConfirmPassword,
                    input = inputConfirmPassword
                )
            } else {
                view?.showErrorOnInput(
                    errorMessage = errorMessageConfirmPassword,
                    labelError = labelErrorConfirmPassword,
                    input = inputConfirmPassword
                )
                return
            }
            return
        }
        // than check if they match
        val errorMessageMatchs = newPassword.isPasswordsMatchs(confirmPassword)
        _isPasswordsMatchs = errorMessageMatchs.isEmpty()
        if (_isPasswordsMatchs) {
            view?.hideErrorOnInput(
                labelError = labelErrorConfirmPassword,
                input = inputConfirmPassword
            )
        } else {
            view?.showErrorOnInput(
                errorMessage = errorMessageMatchs,
                labelError = labelErrorConfirmPassword,
                input = inputConfirmPassword
            )
        }
        view?.enableButtonCreate(isEnabled = isEnabled())
    }

    override fun onDestroy() {
        view = null
    }

    private fun isEnabled(): Boolean {
        return _isValidEmail and _isValidName and _isValidNewPassword and _isValidConfirmPassword and _isPasswordsMatchs
    }
}