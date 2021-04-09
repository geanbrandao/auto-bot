package com.example.autobot.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.autobot.extensions.showSnackBar
import com.example.autobot.models.UserModel
import com.example.autobot.mvp.signup.SignUpContract
import com.example.autobot.mvp.signup.SignUpPresenter
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern
import java.util.regex.Pattern.compile

class SignupFragment : Fragment(), SignUpContract.View {

    override lateinit var presenter: SignUpPresenter
    private lateinit var binding: FragmentSignupBinding

    private lateinit var phoneTextWatcher: TextWatcher

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater)
        presenter = SignUpPresenter(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createListeners()
    }

    private fun createListeners() {
        binding.textActionSignin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_signinFragment)
        }

        binding.buttonSignup.setOnClickListener {
            presenter.isValid(binding.inputPhone.text.toString())
        }

        phoneTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // no implementation here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // no implementation here
            }

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

        fillInputsToTest()
    }

    override fun showSnackbar(message: String) {
        requireView().showSnackBar(message = message)
    }

    private fun fillInputsToTest() {
        binding.inputPhone.setText("53999999999")
        binding.inputName.setText("Gean Teste")
        binding.inputNewPassword.setText("123456")
        binding.inputConfirmPassword.setText("123456")
    }

    override fun displayErrorMessage() {
        TODO("Not yet implemented")
    }

    override fun displaySuccessToast() {
        Snackbar.make(binding.root, "Cadastro realizado", Snackbar.LENGTH_SHORT).show()
    }

    override fun goToSMSCodeValidationScreen() {
        findNavController().navigate(
            SignupFragmentDirections.actionSignupFragmentToValidationFragment(
                getUserDataFromInput()
            )
        )
    }

    private fun getUserDataFromInput(): UserModel {
        return UserModel(
            phone = binding.inputPhone.text.toString(), // phone is masked
            name = binding.inputName.text.toString(),
            password = binding.inputConfirmPassword.text.toString()
        )
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