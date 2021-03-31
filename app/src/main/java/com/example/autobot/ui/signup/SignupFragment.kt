package com.example.autobot.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.autobot.R
import androidx.fragment.app.Fragment
import com.example.autobot.databinding.FragmentSignupBinding
import com.example.autobot.mvp.SignUpContract
import com.example.autobot.mvp.SignUpPresenter
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

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}