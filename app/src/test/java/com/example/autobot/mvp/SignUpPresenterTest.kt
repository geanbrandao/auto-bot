package com.example.autobot.mvp

import com.example.autobot.extensions.isPasswordsMatchs
import com.example.autobot.extensions.isValidEmail
import com.example.autobot.extensions.isValidName
import com.example.autobot.extensions.isValidPassword
import com.example.autobot.ui.signup.SignupFragment
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_FIRST_NAME
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_INVALID_EMAIL
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_NEED_TWO_NAMES
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_PASSWORDS_DO_NOT_MATCH
import com.example.autobot.ui.signup.SignupFragment.Companion.ERROR_MESSAGE_PASSWORD_MIN_LENGHT
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

    companion object {
        private const val IS_VALID: Boolean = true
        private const val IS_NOT_VALID: Boolean = false
    }

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
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when dont has name and lastname`() {
        val name = "Gean"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_NEED_TWO_NAMES)))
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when first name lenght is smaller than three`() {
        val name = "Ge Carlos"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_FIRST_NAME)))
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when second name lenght is smaller than three`() {
        val name = "Gean Ca"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_SECOND_NAME)))
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when is valid name`() {
        val name = "Gean Carlos"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(NO_ERROR_MESSAGE)))
        assertThat(isValid, `is`(equalTo(IS_VALID)))
    }

    @Test
    fun `is valid return when email is empty`() {
        val email = ""
        val errorMessage = email.isValidEmail()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_REQUIRED_FIELD)))
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when email is invalid`() {
        val email = "gean"
        val errorMessage = email.isValidEmail()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_INVALID_EMAIL)))
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when email is valid`() {
        val name = "gean@gmail.com"
        val errorMessage = name.isValidEmail()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(NO_ERROR_MESSAGE)))
        assertThat(isValid, `is`(equalTo(IS_VALID)))
    }

    @Test
    fun `is valid return when password is empty`() {
        val password = ""
        val errorMessage = password.isValidPassword()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_REQUIRED_FIELD)))
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when password is smaller than six`() {
        val password = "23da"
        val errorMessage = password.isValidPassword()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_PASSWORD_MIN_LENGHT)))
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when password is valid`() {
        val password = "asd123"
        val errorMessage = password.isValidPassword()
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(NO_ERROR_MESSAGE)))
        assertThat(isValid, `is`(equalTo(IS_VALID)))
    }

    @Test
    fun `is valid return when new password is empty`(){
        val newPassword = ""
        val confirmPassword = "asd123"
        val errorMessage = newPassword.isPasswordsMatchs(confirmPassword = confirmPassword)
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_REQUIRED_FIELD)))
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when confirm password is empty`(){
        val newPassword = "asd123"
        val confirmPassword = ""
        val errorMessage = newPassword.isPasswordsMatchs(confirmPassword = confirmPassword)
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_REQUIRED_FIELD)))
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when password dosent match`() {
        val newPassword = "asd124"
        val confirmPassword = "asd123"
        val errorMessage = newPassword.isPasswordsMatchs(confirmPassword = confirmPassword)
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(ERROR_MESSAGE_PASSWORDS_DO_NOT_MATCH)))
        assertThat(isValid, `is`(equalTo(IS_NOT_VALID)))
    }

    @Test
    fun `is valid return when passwords matchs`() {
        val newPassword = "asd123"
        val confirmPassword = "asd123"
        val errorMessage = newPassword.isPasswordsMatchs(confirmPassword = confirmPassword)
        val isValid = errorMessage.isEmpty()
        assertThat(errorMessage, `is`(equalTo(NO_ERROR_MESSAGE)))
        assertThat(isValid, `is`(equalTo(IS_VALID)))
    }
}