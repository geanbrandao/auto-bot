package com.example.autobot.mvp.validation_code

import com.example.autobot.constants.Constants.DATABASE_CHILD_USERS
import com.example.autobot.constants.Constants.JWT_FIELD_PASSWORD
import com.example.autobot.constants.Constants.JWT_FIELD_PHONE
import com.example.autobot.constants.Constants.JWT_SECRET_KEY
import com.example.autobot.extensions.isValidSMSCode
import com.example.autobot.models.UserModel
import com.example.autobot.models.firebase_database.UserData
import com.example.autobot.utils.AESEncyption
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import timber.log.Timber

class ValidationCodePresenter(private var view: ValidationCodeContract.View?): ValidationCodeContract.Preseter {

    private var _isSMSCodeValid: Boolean = false

    private val databaseReference = Firebase.database.reference


    override fun onDestroy() {
        view = null
    }

    fun isSMSCodevalid(code: String) {
        val errorMessage = code.isValidSMSCode()
        _isSMSCodeValid = errorMessage.isEmpty()
        view?.enableButtonValidate(isEnabled = _isSMSCodeValid)
    }

    /**
     * gera um token e salva na database
     * para checar esse database ao fazer login
     */
    override fun writeUser(userModel: UserModel) {
        val userId = AESEncyption.encrypt(userModel.phone)
        val jwt = createJwtForNewUser(userModel.phone, userModel.password)

        val userData = UserData(
            token = jwt,
            userId = userId,
            phone = userModel.phone,
            name = userModel.name
        )

        databaseReference.child(DATABASE_CHILD_USERS).child(userId)
            .setValue(userData)
            .addOnSuccessListener {
                val message = "Sucesso ao criar usuario"
                view?.showSnackbar(message)
            }
            .addOnFailureListener {
                Timber.e(it)
                val message = "Erro ao criar usuário"
                 view?.showSnackbar(message)
            }
    }

    private fun createJwtForNewUser(phone: String, password: String): String {
        val jwt = Jwts.builder()
            .claim(JWT_FIELD_PHONE, phone)
            .claim(JWT_FIELD_PASSWORD, password)
            .signWith(SignatureAlgorithm.HS256, AESEncyption.encrypt(JWT_SECRET_KEY))
            .compact()

        Timber.d("DEBUG3 - JWT $jwt")
        return jwt
    }
}