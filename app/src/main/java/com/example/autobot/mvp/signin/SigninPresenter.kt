package com.example.autobot.mvp.signin

import com.example.autobot.constants.Constants.MASK_PHONE_NUMBER
import com.example.autobot.extensions.isValidPassword
import com.example.autobot.extensions.isValidPhone
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SigninPresenter(private var view: SigninContract.View?) : SigninContract.Presenter {

    private var _isValidPhone: Boolean = false
    private var _isValidPassword: Boolean = false

    override fun isValid() {
        view?.displaySuccessToast()
    }

    override fun isValidPhone(
        phone: String,
    ) {
        val errorMessage = phone.isValidPhone()
        _isValidPhone = errorMessage.isEmpty()
        view?.enableButtonLogin(isEnabled = isEnabled())
    }

    override fun formatPhoneInput(phone: String) {
        val cleanPhoneNumber = phone.filter { it.isDigit() }
        var mask = MASK_PHONE_NUMBER
        cleanPhoneNumber.forEachIndexed { index, character ->
            mask = mask.replaceFirst('#', character)
        }
        val delimiter = mask.lastOrNull { it.isDigit() }
        val phoneMasked = delimiter?.let {
            mask.replaceAfterLast(delimiter = it, "")
        } ?: run {
            ""
        }
        view?.fillPhoneInputWithMask(phoneMasked = phoneMasked)
    }

    override fun isValidPassword(newPassword: String) {
        val errorMessageNewPassword = newPassword.isValidPassword()
        _isValidPassword = errorMessageNewPassword.isEmpty()
        view?.enableButtonLogin(isEnabled = isEnabled())
    }

    fun isEnabled(): Boolean {
        return _isValidPhone and _isValidPassword
    }

    override fun onSignupClick() {
        view?.navigateToSignup()
    }

    fun onSiginClick(phone: String, password: String) {
        // try to login
        val user = Firebase.auth.currentUser

    }

    private fun checkIfUserExistInDatabase() {

    }

    override fun onDestroy() {
        view = null
    }
}
