package com.example.autobot.utils

import android.util.Base64
import timber.log.Timber
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object AESEncyption {

    private const val secretKey =
        "aGVsbG8gZGFya25lc3MgbXkgb2xkIGZyaWVuZA==" // base64 decode => hello darkness my old friend
    private const val salt = "VjBiSThGNWxCeDZUU1NqdQo=" // base64 decode => V0bI8F5lBx6TSSju
    private const val iv = "R01za1g4TE95enpKNjVNdw==" // base64 decode => GMskX8LOyzzJ65Mw

    fun encrypt(strToEncrypt: String): String {
        try {
            val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.NO_WRAP))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec =
                PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.NO_WRAP), 10000, 256)
            val tmp = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.encoded, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
            return Base64.encodeToString(
                cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)),
                Base64.NO_WRAP
            )
        } catch (e: Exception) {
            Timber.e(e)
            throw Exception(e)
        }
    }

    fun decrypt(strToDecrypt: String): String {
        try {

            val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.NO_WRAP))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec =
                PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.NO_WRAP), 10000, 256)
            val tmp = factory.generateSecret(spec);
            val secretKey = SecretKeySpec(tmp.encoded, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.NO_WRAP)))
        } catch (e: Exception) {
            Timber.e(e)
            throw Exception(e)
        }
    }
}