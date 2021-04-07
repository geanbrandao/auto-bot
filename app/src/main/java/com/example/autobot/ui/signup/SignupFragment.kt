package com.example.autobot.ui.signup

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.autobot.R
import com.example.autobot.databinding.FragmentSignupBinding
import com.example.autobot.extensions.hide
import com.example.autobot.extensions.show
import com.example.autobot.mvp.SignUpContract
import com.example.autobot.mvp.SignUpPresenter
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern
import java.util.regex.Pattern.compile
import android.text.TextWatcher

class SignupFragment : Fragment(), SignUpContract.View {

    companion object {
        const val NO_ERROR_MESSAGE = ""
        const val ERROR_MESSAGE_REQUIRED_FIELD = "Campo obrigatorio"
        const val ERROR_MESSAGE_NEED_TWO_NAMES = "Necessário nome e sobrenome"
        const val ERROR_MESSAGE_FIRST_NAME = "Primeiro nome precisa ter mais de 2 letras"
        const val ERROR_MESSAGE_SECOND_NAME = "Segundo nome precisa ter mais de 2 letras"

        const val ERROR_MESSAGE_INVALID_PHONE = "Número de celular inválido"

        const val ERROR_MESSAGE_PASSWORD_MIN_LENGHT = "A senha precisa ter no minimo 6 caracters"
        const val ERROR_MESSAGE_PASSWORDS_DO_NOT_MATCH = "Senhas não são iguais"
        val emailRegex: Pattern = compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        const val MASK_PHONE_NUMBER = "(##) #####-####"
    }

    override lateinit var presenter: SignUpPresenter
    private lateinit var binding: FragmentSignupBinding

    private lateinit var phoneTextWatcher: TextWatcher

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater)
        presenter = SignUpPresenter(this)

        createListeners()

        return binding.root
    }

    private fun createListeners() {
        binding.textActionSignin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_signinFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonSignup.setOnClickListener {
            presenter.isValid()
        }


        phoneTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.inputPhone.removeTextChangedListener(this)
                presenter.formatPhoneInput(s.toString())
            }

        }


        binding.inputPhone.addTextChangedListener(phoneTextWatcher)

        binding.inputPhone.doOnTextChanged { text, _, _, _ ->
            presenter.isValidPhone(text.toString(), binding.labelErrorEmail, binding.inputPhone)
        }

        binding.inputName.doOnTextChanged { text, _, _, _ ->
            presenter.isValidName(text.toString(), binding.labelErrorName, binding.inputName)
        }

        binding.inputNewPassword.doOnTextChanged { text, _, _, _ ->
            presenter.isValidNewPassword(
                newPassword = text.toString(),
                labelErrorNewPassword = binding.labelErrorNewPassword,
                inputNewPassword = binding.inputNewPassword,
            )
            presenter.isPasswordMatchs(
                newPassword = text.toString(),
                confirmPassword = binding.inputConfirmPassword.text.toString(),
                labelErrorConfirmPassword = binding.labelErrorConfirmPassword,
                inputConfirmPassword = binding.inputConfirmPassword
            )
        }

        binding.inputConfirmPassword.doOnTextChanged { text, _, _, _ ->
            presenter.isValidConfirmPassword(
                confirmPassword = text.toString(),
                labelErrorConfirmPassword = binding.labelErrorConfirmPassword,
                inputConfirmPassword = binding.inputConfirmPassword,
            )
            presenter.isPasswordMatchs(
                newPassword = binding.inputNewPassword.text.toString(),
                confirmPassword = text.toString(),
                labelErrorConfirmPassword = binding.labelErrorConfirmPassword,
                inputConfirmPassword = binding.inputConfirmPassword
            )
        }
    }

    override fun displayErrorMessage() {
        TODO("Not yet implemented")
    }

    override fun displaySuccessToast() {
        Snackbar.make(binding.root, "Cadastro realizado", Snackbar.LENGTH_SHORT).show()
    }

    override fun goToHomeFragment() {
        TODO("Not yet implemented")
    }

    override fun enableButtonCreate(isEnabled: Boolean) {
        binding.buttonSignup.isEnabled = isEnabled
    }

    override fun showErrorOnInput(
        errorMessage: String,
        labelError: AppCompatTextView,
        input: AppCompatEditText
    ) {
        labelError.text = errorMessage
        labelError.show()
        input.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_input_default_error)
    }

    override fun hideErrorOnInput(labelError: AppCompatTextView, input: AppCompatEditText) {
        labelError.hide()
        input.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_input_default)
    }

    override fun fillPhoneInputWithMask(phoneMasked: String) {
        binding.inputPhone.setText(phoneMasked)
        binding.inputPhone.setSelection(phoneMasked.length)
        binding.inputPhone.addTextChangedListener(phoneTextWatcher)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}