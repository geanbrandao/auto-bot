package com.example.autobot.mvp

import androidx.appcompat.widget.AppCompatTextView
import com.example.autobot.isValidName

class SignUpPresenter(private var view: SignUpContract.View?) : SignUpContract.Presenter {

    private var _isValidName: Boolean = false

    override fun isValid() {
        view?.displaySuccessToast()
    }

    override fun isValidName(name: String, labelError: AppCompatTextView
    ) {
        val errorMessage = name.isValidName()
        _isValidName  = errorMessage.isEmpty()
        view?.enableButtonCreate(isEnabled = isEnabled())
        if (_isValidName) {
            view?.hideErrorOnInput(labelError = labelError)
        } else {
            view?.showErrorOnInput(errorMessage, labelError)
        }
    }

    override fun onDestroy() {
        view = null
    }

    private fun isEnabled(): Boolean {
        return _isValidName
    }
}