package com.example.autobot.mvp.signin

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.autobot.mvp.BasePresenter
import com.example.autobot.mvp.BaseView

interface SigninContract {

    interface View : BaseView<SigninPresenter> {
        fun displaySuccessToast()
        fun enableButtonLogin(isEnabled: Boolean)
        fun fillPhoneInputWithMask(phoneMasked: String)
    }

    interface Presenter : BasePresenter {
        fun isValid()
        fun isValidPhone(phone: String)
        fun formatPhoneInput(phone: String)
        fun isValidPassword(newPassword: String)
    }
}