package com.extrime.my_criptographer.ui.Ciphers

import android.widget.RadioButton
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.extrime.my_criptographer.R
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class AES : DES() {
    private var secretKey: SecretKey? = null
    private var cipher: Cipher? = null
    var algorithm = "AES" // AES/ECB/PKCS5Padding
    var input: String? = null
    var secretText: String? = null
    private var bits_key = 0
    private var KeySecret: String? = null
    private var rb_128: RadioButton? = null
    private var rb_192: RadioButton? = null
    private var rb_256: RadioButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aes_activity)
        input = (findViewById<View>(R.id.input_text) as EditText).text.toString()
        rb_128 = findViewById(R.id.radio_128)
        rb_192 = findViewById(R.id.radio_192)
        rb_256 = findViewById(R.id.radio_256)
        SetOnClick(aesListener)
    }

    private val aesListener = View.OnClickListener { view: View ->
        when (view.id) {
            R.id.BtnInfo -> GoTo(this, getString(R.string.AES_About), algorithm)
            R.id.BtnEncrypt -> AES_Encode()
            R.id.BtnDecrypt -> AES_Decode()
        }
    }

    fun setKey(myKey: String) {
        try {
            var key: ByteArray? = myKey.toByteArray(StandardCharsets.UTF_8)
            val sha = MessageDigest.getInstance("SHA-1")
            key = sha.digest(key)
            key = Arrays.copyOf(key, bits_key / 8)
            secretKey = SecretKeySpec(key, algorithm)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    fun encode(strToEncrypt: String, secret: String): String? {
        try {
            setKey(secret)
            cipher = Cipher.getInstance(algorithm)
            cipher!!.init(Cipher.ENCRYPT_MODE, secretKey)
            val arr_encode = cipher!!.doFinal(strToEncrypt.toByteArray(StandardCharsets.UTF_8))
            return Base64.getEncoder().encodeToString(arr_encode)
        } catch (e: Exception) {
            println("Error while encrypting: $e")
        }
        return null
    }

    fun decode(strToDecrypt: String?, secret: String): String? {
        try {
            setKey(secret)
            cipher = Cipher.getInstance(algorithm)
            cipher!!.init(Cipher.DECRYPT_MODE, secretKey)
            return String(cipher!!.doFinal(Base64.getDecoder().decode(strToDecrypt)))
        } catch (e: Exception) {
            println("Error while decrypting: $e")
        }
        return null
    }

    fun CheckBits() {
        if (rb_128!!.isChecked) bits_key = 128
        if (rb_192!!.isChecked) bits_key = 192
        if (rb_256!!.isChecked) bits_key = 256
        if (bits_key != 128 && bits_key != 192 && bits_key != 256) {
            MyToast("Измените битность пароля")
        }
    }

    fun AES_Encode() {
        try {
            input = (findViewById<View>(R.id.input_text) as EditText).text.toString()
            KeySecret = (findViewById<View>(R.id.password) as EditText).text.toString()
            CheckBits()
            secretText = encode(input!!, KeySecret!!)
            (findViewById<View>(R.id.secret_text) as TextView).text = secretText
            MyToast("Зашифровано")
        } catch (e: Exception) {
            if (bits_key == 0) {
                MyToast("Заполните все поля!")
            } else MyToast("Ошибка!")
        }
    }

    fun AES_Decode() {
        try {
            input = (findViewById<View>(R.id.input_text) as EditText).text.toString()
            KeySecret = (findViewById<View>(R.id.password) as EditText).text.toString()
            CheckBits()
            secretText = decode(input, KeySecret!!)
            if (secretText == null) {
                MyToast("Ошибка дешифрования")
                (findViewById<View>(R.id.secret_text) as TextView).text = null
            } else {
                (findViewById<View>(R.id.secret_text) as TextView).text = secretText
                MyToast("Дешифровано")
            }
        } catch (e: Exception) {
            MyToast("Ошибка!")
        }
    }
}