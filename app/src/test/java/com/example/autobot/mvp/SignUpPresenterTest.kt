package com.example.autobot.mvp

import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignUpPresenterTest {

    private val view = mock(SignUpContract.View::class.java)
    private val presenter = SignUpPresenter(view)

    @After
    fun done() {
        presenter.onDestroy()
    }

    @Test
    fun `test return method view`() {
        presenter.isValid()

        verify(view).displaySuccessToast()
    }
}