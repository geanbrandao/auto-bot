package com.example.autobot.mvp.validation_code

import com.example.autobot.ui.validation_code.ValidationCodeFragment
import org.junit.After
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ValidationCodePresenterTest {

    private val view = mock(ValidationCodeContract.View::class.java)
    private val presenter = ValidationCodePresenter(view)

    @After
    fun done() {
        presenter.onDestroy()
    }

}