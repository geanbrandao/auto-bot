package com.example.autobot.extensions

import com.example.autobot.ui.signup.SignupFragment
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Test

class ExtensionsValidationKtTest {

    companion object Constants {
        private const val IS_VALID: Boolean = true
        private const val IS_NOT_VALID: Boolean = false
    }

    @Test
    fun `is valid return when name is empty`() {
        val name = ""
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_REQUIRED_FIELD))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when dont has name and lastname`() {
        val name = "Gean"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_NEED_TWO_NAMES))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when first name lenght is smaller than three`() {
        val name = "Ge Carlos"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_FIRST_NAME))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when second name lenght is smaller than three`() {
        val name = "Gean Ca"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_SECOND_NAME))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when is valid name`() {
        val name = "Gean Carlos"
        val errorMessage = name.isValidName()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.NO_ERROR_MESSAGE))
        )
        assertThat(isValid, CoreMatchers.`is`(CoreMatchers.equalTo(IS_VALID)))
    }

    @Test
    fun `is valid return when email is empty`() {
        val email = ""
        val errorMessage = email.isValidEmail()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_REQUIRED_FIELD))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when email is invalid`() {
        val email = "gean"
        val errorMessage = email.isValidEmail()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_INVALID_EMAIL))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when email is valid`() {
        val name = "gean@gmail.com"
        val errorMessage = name.isValidEmail()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.NO_ERROR_MESSAGE))
        )
        assertThat(isValid, CoreMatchers.`is`(CoreMatchers.equalTo(IS_VALID)))
    }

    @Test
    fun `is valid return when password is empty`() {
        val password = ""
        val errorMessage = password.isValidPassword()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_REQUIRED_FIELD))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when password is smaller than six`() {
        val password = "23da"
        val errorMessage = password.isValidPassword()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_PASSWORD_MIN_LENGHT))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when password is valid`() {
        val password = "asd123"
        val errorMessage = password.isValidPassword()
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.NO_ERROR_MESSAGE))
        )
        assertThat(isValid, CoreMatchers.`is`(CoreMatchers.equalTo(IS_VALID)))
    }

    @Test
    fun `is valid return when new password is empty`() {
        val newPassword = ""
        val confirmPassword = "asd123"
        val errorMessage = newPassword.isPasswordsMatchs(confirmPassword = confirmPassword)
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_REQUIRED_FIELD))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when confirm password is empty`() {
        val newPassword = "asd123"
        val confirmPassword = ""
        val errorMessage = newPassword.isPasswordsMatchs(confirmPassword = confirmPassword)
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_REQUIRED_FIELD))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when password dosent match`() {
        val newPassword = "asd124"
        val confirmPassword = "asd123"
        val errorMessage = newPassword.isPasswordsMatchs(confirmPassword = confirmPassword)
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.ERROR_MESSAGE_PASSWORDS_DO_NOT_MATCH))
        )
        assertThat(
            isValid,
            CoreMatchers.`is`(CoreMatchers.equalTo(IS_NOT_VALID))
        )
    }

    @Test
    fun `is valid return when passwords matchs`() {
        val newPassword = "asd123"
        val confirmPassword = "asd123"
        val errorMessage = newPassword.isPasswordsMatchs(confirmPassword = confirmPassword)
        val isValid = errorMessage.isEmpty()
        assertThat(
            errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo(SignupFragment.NO_ERROR_MESSAGE))
        )
        assertThat(isValid, CoreMatchers.`is`(CoreMatchers.equalTo(IS_VALID)))
    }
}