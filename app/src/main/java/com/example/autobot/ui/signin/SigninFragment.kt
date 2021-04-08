package com.example.autobot.ui.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.autobot.R
import com.example.autobot.databinding.FragmentSigninBinding
import com.example.autobot.mvp.signin.SigninContract
import com.example.autobot.mvp.signin.SigninPresenter
import com.google.android.material.snackbar.Snackbar

class SigninFragment : Fragment(), SigninContract.View {

    override lateinit var presenter: SigninPresenter
    private lateinit var binding: FragmentSigninBinding

    private lateinit var phoneTextWatcher: TextWatcher

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater)

        presenter = SigninPresenter(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createListeners()
    }

    private fun createListeners() {
        binding.textActionSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signinFragment_to_signupFragment)
        }

        binding.buttonSignin.setOnClickListener {
            presenter.isValid()
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
                presenter.formatPhoneInput(phone = s.toString())
            }
        }

        binding.inputPhone.addTextChangedListener(phoneTextWatcher)

        binding.inputPhone.doOnTextChanged { text, _, _, _ ->
            presenter.isValidPhone(phone = text.toString())
        }

        binding.inputPassword.doOnTextChanged { text, _, _, _ ->
            presenter.isValidPassword(newPassword = text.toString())
        }
    }

    override fun fillPhoneInputWithMask(phoneMasked: String) {
        binding.inputPhone.setText(phoneMasked)
        binding.inputPhone.setSelection(phoneMasked.length)
        binding.inputPhone.addTextChangedListener(phoneTextWatcher)
    }

    override fun displaySuccessToast() {
        Snackbar.make(binding.root, "Login realizado", Snackbar.LENGTH_SHORT).show()
    }

    override fun enableButtonLogin(isEnabled: Boolean) {
        binding.buttonSignin.isEnabled = isEnabled
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}