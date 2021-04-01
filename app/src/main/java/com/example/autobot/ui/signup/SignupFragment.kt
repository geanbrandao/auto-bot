package com.example.autobot.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.autobot.R
import androidx.fragment.app.Fragment
import com.example.autobot.databinding.FragmentSignupBinding
import com.example.autobot.hide
import com.example.autobot.mvp.SignUpContract
import com.example.autobot.mvp.SignUpPresenter
import com.example.autobot.show
import com.google.android.material.snackbar.Snackbar

class SignupFragment : Fragment(), SignUpContract.View {

    override lateinit var presenter: SignUpPresenter
    private lateinit var binding: FragmentSignupBinding

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

        binding.inputName.doOnTextChanged { text, _, _, _ ->
            presenter.isValidName(text.toString(), binding.labelErrorName)
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

    override fun showErrorOnInput(errorMessage: String, labelError: AppCompatTextView) {
        labelError.text = errorMessage
        labelError.show()
    }

    override fun hideErrorOnInput(labelError: AppCompatTextView) {
        labelError.hide()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}