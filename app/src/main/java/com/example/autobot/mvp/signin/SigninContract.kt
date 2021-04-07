package com.example.autobot.mvp.signin

import com.example.autobot.mvp.BasePresenter
import com.example.autobot.mvp.BaseView

interface SigninContract {

    interface View: BaseView<SigninPresenter> {

    }

    interface Presenter: BasePresenter {

    }
}