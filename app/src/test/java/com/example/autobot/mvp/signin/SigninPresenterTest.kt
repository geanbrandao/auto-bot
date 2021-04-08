package com.example.autobot.mvp.signin

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.autobot.mvp.SignUpPresenterTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SigninPresenterTest {

    companion object Constants {
        private const val IS_VALID: Boolean = true
        private const val IS_NOT_VALID: Boolean = false
    }

    private val view = mock(SigninContract.View::class.java)
    private val textView = mock(AppCompatTextView::class.java)
    private val editText = mock(AppCompatEditText::class.java)
    private val presenter = SigninPresenter(view)

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
        presenter.isValidPhone("(55) 981")

        verify(view).enableButtonLogin(isEnabled = IS_NOT_VALID)
    }

    @Test
    fun `test view method when isValidPassword is called`() {
        presenter.isValidPassword("123456")

        verify(view).enableButtonLogin(isEnabled = IS_NOT_VALID)
    }

    @Test
    fun `test view method when form is fill and valid`() {
        presenter.isValidPhone("(55) 99999-9999")

        presenter.isValidPassword("123456")

        verify(view).enableButtonLogin(isEnabled = presenter.isEnabled())
    }
}