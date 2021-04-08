package com.example.autobot.mvp.validation_code

import com.example.autobot.mvp.BasePresenter
import com.example.autobot.mvp.BaseView

interface ValidationCodeContract {

    interface View: BaseView<ValidationCodePresenter> {
        fun enableButtonValidate(isEnabled: Boolean)

    }

    interface Preseter: BasePresenter {

    }
}