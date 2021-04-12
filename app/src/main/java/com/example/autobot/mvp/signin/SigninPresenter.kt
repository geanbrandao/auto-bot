package com.example.autobot.mvp.signin

import com.example.autobot.constants.Constants.MASK_PHONE_NUMBER
import com.example.autobot.extensions.isValidPassword
import com.example.autobot.extensions.isValidPhone

/**
 * Receba essa propriedade como final, não faz sentido seu presenter receber uma view como propriedade nula
 */
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

    /**
     * o ideal seria criar uma textwatcher que faz a mascara de telefone e não fazer a mascara no presenter
     */
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

    override fun onDestroy() {
        view = null
    }
}
/**
 * sempre deixa uma linha vazia ao fim do arquivo, também é uma convenção
 */