package com.example.autobot.mvp

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.autobot.mvp.signup.SignUpContract
import com.example.autobot.mvp.signup.SignUpPresenter
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignUpPresenterTest {

    private val view = mock(SignUpContract.View::class.java)
    private val textView = mock(AppCompatTextView::class.java)
    private val editText = mock(AppCompatEditText::class.java)
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

    @Test
    fun `test view method when isValidPhone is called`() {
        presenter.isValidPhone("emailText", textView, editText)

        verify(view).enableButtonCreate(false)
    }

    @Test
    fun `test view method when isValidNewPassword is called`() {
        presenter.isValidNewPassword("123456", textView, editText)

        verify(view).enableButtonCreate(false)
    }

    @Test
    fun `test view method when isValidConfirmPassword is called`() {
        presenter.isValidConfirmPassword("123456", textView, editText)

        verify(view).enableButtonCreate(false)
    }

    @Test
    fun `test view method when isPasswordMatchs is called`() {
        presenter.isPasswordMatchs("123456", "123456", textView, editText)

        verify(view).enableButtonCreate(false)
    }
}