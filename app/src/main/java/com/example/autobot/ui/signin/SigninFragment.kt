package com.example.autobot.ui.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.autobot.R
import com.example.autobot.databinding.FragmentSigninBinding
import com.example.autobot.mvp.signin.SigninContract
import com.example.autobot.mvp.signin.SigninPresenter

class SigninFragment : Fragment(), SigninContract.View {

    override lateinit var presenter: SigninPresenter
    private lateinit var binding: FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater)

        createListeners()

        return binding.root
    }

    private fun createListeners() {
        binding.textActionSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signinFragment_to_signupFragment)
        }
    }
}