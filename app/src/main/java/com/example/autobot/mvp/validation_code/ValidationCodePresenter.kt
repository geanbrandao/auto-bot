package com.example.autobot.mvp.validation_code

import com.example.autobot.extensions.isValidSMSCode

class ValidationCodePresenter(private var view: ValidationCodeContract.View?): ValidationCodeContract.Preseter {

    private var _isSMSCodeValid: Boolean = false

    override fun onDestroy() {
        view = null
    }

    fun isSMSCodevalid(code: String) {
        val errorMessage = code.isValidSMSCode()
        _isSMSCodeValid = errorMessage.isEmpty()
        view?.enableButtonValidate(isEnabled = _isSMSCodeValid)
    }
}