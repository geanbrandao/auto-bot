package com.example.autobot.mvp

import com.example.autobot.extensions.isValidName
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_FIRST_NAME
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_NEED_TWO_NAMES
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_REQUIRED_FIELD
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_SECOND_NAME
import com.example.autobot.ui.signup.SignupFragment.Companion.NO_ERROR_MESSAGE
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
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

    @Test
    fun `is valid return when name is empty`() {
        val name = ""
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_REQUIRED_FIELD)))
        assertThat(isValid, `is`(equalTo(false)))
    }

    @Test
    fun `is valid return when dont has name and lastname`() {
        val name = "Gean"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_NEED_TWO_NAMES)))
        assertThat(isValid, `is`(equalTo(false)))
    }

    @Test
    fun `is valid return when first name lenght is smaller than three`() {
        val name = "Ge Carlos"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_FIRST_NAME)))
        assertThat(isValid, `is`(equalTo(false)))
    }

    @Test
    fun `is valid return when second name lenght is smaller than three`() {
        val name = "Gean Ca"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_SECOND_NAME)))
        assertThat(isValid, `is`(equalTo(false)))
    }

    @Test
    fun `is valid return when is valid name`() {
        val name = "Gean Carlos"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(NO_ERROR_MESSAGE)))
        assertThat(isValid, `is`(equalTo(true)))
    }
}