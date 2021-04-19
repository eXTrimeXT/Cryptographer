package com.extrime.my_criptographer.ui.Ciphers

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.extrime.my_criptographer.Base64
import com.extrime.my_criptographer.R
import com.extrime.my_criptographer.StartActivity
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

class BlowFish : StartActivity() {
    var KeyData : ByteArray? = null
    private var cipher: Cipher? = null
    var KS: SecretKeySpec? = null
    var key: String? = null
    var input: String? = null
    var algorithm = "BLOWFISH"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blowfish_and_des_activity)
        SetOnClick(blowfishListener)
    }

    private val blowfishListener = View.OnClickListener { view: View ->
        when (view.id) {
            R.id.BtnInfo -> GoTo(this@BlowFish, getString(R.string.BlowFish_About), algorithm)
            R.id.BtnEncrypt -> BlowFish_Encode()
            R.id.BtnDecrypt -> BlowFish_Decode()
        }
    }

    private fun createCipherAndKey(key: String) {
        try {
            cipher = Cipher.getInstance(algorithm)
            KeyData = key.toByteArray()
            KS = SecretKeySpec(KeyData, algorithm)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        }
    }

    private fun BlowFish_Encode() {
        try {
            input = (findViewById<View>(R.id.input_text) as EditText).text.toString()
            key = (findViewById<View>(R.id.password) as EditText).text.toString()
            createCipherAndKey(key!!)
            cipher!!.init(Cipher.ENCRYPT_MODE, KS)
            val encrypted = cipher!!.doFinal(input!!.toByteArray(StandardCharsets.UTF_8))
            val encode = Base64.getEncoder().encodeToString(encrypted)
            (findViewById<View>(R.id.secret_text) as TextView).text = encode
            MyToast("Зашифровано")
        } catch (e: Exception) {
            MyToast("Заполните все поля!")
        }
    }

    private fun BlowFish_Decode() {
        try {
            input = (findViewById<View>(R.id.input_text) as EditText).text.toString()
            key = (findViewById<View>(R.id.password) as EditText).text.toString()
            createCipherAndKey(key!!)
            cipher!!.init(Cipher.DECRYPT_MODE, KS)
            val decode = String(cipher!!.doFinal(Base64.getDecoder().decode(input)))
            println("decode = $decode")
            (findViewById<View>(R.id.secret_text) as TextView).text = decode
            MyToast("Дешифровано")
        } catch (e: Exception) {
            if (input == null || key == null) MyToast("Заполните все поля!")
            else MyToast("Ошибка дешифрования")

            (findViewById<View>(R.id.secret_text) as TextView).text = ""
        }
    }
}