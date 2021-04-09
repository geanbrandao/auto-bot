package com.example.autobot.ui.validation_code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.autobot.R
import com.example.autobot.databinding.FragmentValidationCodeBinding
import com.example.autobot.extensions.showSnackBar
import com.example.autobot.mvp.validation_code.ValidationCodeContract
import com.example.autobot.mvp.validation_code.ValidationCodePresenter
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ValidationCodeFragment : Fragment(), ValidationCodeContract.View {

    private val args: ValidationCodeFragmentArgs by navArgs()

    private lateinit var binding: FragmentValidationCodeBinding

    override lateinit var presenter: ValidationCodePresenter

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var auth: FirebaseAuth

//    private lateinit var verificationId: String

    private lateinit var storedVerificationId: String

    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentValidationCodeBinding.inflate(inflater)
        presenter = ValidationCodePresenter(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        createListeners()
    }

    private fun createListeners() {

        val userModel = args.userModel
        binding.textLabel.text = getString(R.string.fragment_validation_text_label, userModel)
        binding.inputSmsCode.doOnTextChanged { text, _, _, _ ->
            presenter.isSMSCodevalid(text.toString())
        }

        binding.buttonValidate.setOnClickListener {
            verifySigninCode()
        }

        val phoneWithCountryCode = "+55".plus(userModel.phone.filter { it.isDigit() })
//        sendMessage(phoneWithCountryCode)
        createPhoneAuthCallbacks()
        startPhoneNumberVerification(phoneWithCountryCode)

    }

    override fun showSnackbar(message: String) {
        requireView().showSnackBar(message = message)
    }

    private fun createPhoneAuthCallbacks() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
//                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Timber.d("onVerificationFailed $e")

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Timber.d("onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)        // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS)  // Timeout and unit
            .setActivity(requireActivity())     // Activity (for callback binding)
            .setCallbacks(callbacks)            // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Timber.d("###################### COMECOU")
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Timber.tag("signCredential").d("signInWithCredential:success")
                Timber.d("Verificado com sucesso")
                // ...
                Timber.d("###################### TERMINOU")
                val user: FirebaseUser? = task.result?.user
                user?.uid

                val userModel = args.userModel
                presenter.writeUser(userModel = userModel)

            } else {
//                    Toast.makeText(context!!, "Sigin failed", Toast.LENGTH_LONG).show()
                // Sign in failed, display a message and update the UI
                Timber.d("signInWithCredential:failure ${task.exception}")
                Timber.tag("signInWithPhone").e(task.exception)
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code entered was invalid
                    Timber.tag("VerificationError").e("Código de verificação é inválido")
                    Timber.e(task.exception)

                } else {
                    Timber.tag("VerificationError").e("Erro ao validar código")
                    Timber.e(task.exception)
                }
                Timber.d("###################### TERMINOU")

            }
        }

    }

//    private fun sendMessage(phone: String) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//            phone,
//            30,
//            TimeUnit.SECONDS,
//            requireActivity(),
//            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
//                    Timber.tag("VerifyNumber").d("code:: ${p0.smsCode}")
//
//                    binding.inputSmsCode.setText(p0.smsCode)
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    Timber.tag("VerifyNumber").d("falhouu")
//                    e.printStackTrace()
//                    when (e) {
//                        is FirebaseAuthInvalidCredentialsException -> {
//                            // Invalid request
//                            // ...
//                            Timber.tag("VerifyNumber").e("Invalid request")
//                            Timber.tag("VerifyNumber").e(e)
////                            showDialogError("Invalid request")
//                        }
//                        is FirebaseTooManyRequestsException -> {
//                            // The SMS quota for the project has been exceeded
//                            Timber.tag("VerifyNumber").e("The SMS quota for the project has been exceeded")
//                            Timber.tag("VerifyNumber").e(e)
////                            showDialogError("The SMS quota for the project has been exceeded")
//                        }
//                        else -> {
//                            Timber.tag("VerifyNumber").e(e)
////                            showDialogError(e.message.toString())
//                        }
//                    }
//                }
//
//                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
//                    Timber.tag("VerifyNumber").d("p0 - $p0")
//                    Timber.tag("VerifyNumber").d("p1 - $p1")
//                    verificationId = p0
//                    Toast.makeText(requireContext(), "Codigo SMS enviado aguarde aqui", Toast.LENGTH_LONG).show()
//                }
//
//                override fun onCodeAutoRetrievalTimeOut(p0: String) {
////                    Toast.makeText(requireContext(), "Timeout, tenta mandar o SMS de novo - $p0", Toast.LENGTH_LONG).show()
//                }
//            })
//
//    }

    private fun verifySigninCode() {
        val code = binding.inputSmsCode.text.toString()
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(storedVerificationId ?: "", code)
        signInWithPhoneAuthCredential(credential)
    }


    override fun enableButtonValidate(isEnabled: Boolean) {
        binding.buttonValidate.isEnabled = isEnabled
    }
}