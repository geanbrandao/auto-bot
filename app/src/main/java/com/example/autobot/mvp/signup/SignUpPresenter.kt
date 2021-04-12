package com.example.autobot.mvp.signup

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.autobot.constants.Constants
import com.example.autobot.constants.Constants.MASK_PHONE_NUMBER
import com.example.autobot.extensions.isPasswordsMatchs
import com.example.autobot.extensions.isValidName
import com.example.autobot.extensions.isValidPassword
import com.example.autobot.extensions.isValidPhone
import com.example.autobot.utils.AESEncyption
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class SignUpPresenter(private var view: SignUpContract.View?) : SignUpContract.Presenter {

    private var _isValidPhone: Boolean = false
    private var _isValidName: Boolean = false
    private var _isValidNewPassword: Boolean = false
    private var _isValidConfirmPassword: Boolean = false
    private var _isPasswordsMatchs: Boolean = false

    private val databaseReference = Firebase.database.reference

    override fun isValid(phone: String) {
        checkIsUserExistInDatabase(phone = phone)
    }

    private fun checkIsUserExistInDatabase(phone: String) {
        val userId = AESEncyption.encrypt(phone)
        databaseReference.child(Constants.DATABASE_CHILD_USERS).child(userId).get().addOnSuccessListener {
            Timber.d("DEBUG3 - User already exist in database")
            view?.showSnackbar("O número $phone já está cadastrado.")
        }.addOnFailureListener {
            Timber.d("DEBUG3 - User do not exist")
            view?.navigateToSMSCodeValidationScreen()
        }
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

    override fun isValidPhone(
        phone: String,
        labelError: AppCompatTextView,
        input: AppCompatEditText
    ) {
        val errorMessage = phone.isValidPhone()
        _isValidPhone = errorMessage.isEmpty()
        _isValidPhone.handleWithErrorOnInput(labelError, input, errorMessage)
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

    fun  isEnabled(): Boolean {
        return _isValidPhone and _isValidName and _isValidNewPassword and _isValidConfirmPassword and _isPasswordsMatchs
    }

    override fun onSigninClick() {
        view?.navigateToSignin()
    }
}
