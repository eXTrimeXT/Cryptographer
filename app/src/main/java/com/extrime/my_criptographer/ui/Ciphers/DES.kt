package com.extrime.my_criptographer.ui.Ciphers

import android.os.Bundle
import kotlin.Throws
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import com.extrime.my_criptographer.R
import com.extrime.my_criptographer.StartActivity
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.Exception
import java.lang.StringBuilder
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import javax.crypto.*
import javax.crypto.spec.DESKeySpec
import kotlin.text.Charsets.UTF_8

open class DES : StartActivity() {
    private var cipher: Cipher? = null
    private var copyText: String? = null
    private val algorithm = "DES"
    private var output: ByteArrayOutputStream? = null
    var btn_info: ImageButton? = null
    var btn_Encrypt: Button? = null
    var btn_Decrypt: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blowfish_and_des_activity)
        SetOnClick(desListener)
        create_cipher()
    }

    private fun create_cipher() {
        try {
            val key = KeyGenerator.getInstance(algorithm).generateKey()
            cipher = Cipher.getInstance(algorithm)
            cipher!!.init(Cipher.DECRYPT_MODE, key)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        }
    }

    private val desListener = View.OnClickListener { view: View ->
    when (view.id) {
            R.id.BtnInfo -> GoTo(this, getString(R.string.DES_About), algorithm)
            R.id.BtnEncrypt -> DES_Encode()
            R.id.BtnDecrypt -> DES_Decode()
        }
    }

    @Throws(IOException::class, InvalidKeyException::class,
        NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    private fun code(input: String, key: String): String {
        DES_CreateKeys(input, key, true)
        return Base64.encodeToString(output!!.toByteArray(), Base64.DEFAULT)
    }

    @Throws(IOException::class, InvalidKeyException::class,
        NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    private fun DES_CreateKeys(input: String, key: String, Encode: Boolean) {
        val KS = DESKeySpec(key.toByteArray(UTF_8))
        val skf = SecretKeyFactory.getInstance(algorithm)
        val desKey = skf.generateSecret(KS)
        val cis: CipherInputStream =
            if (Encode) {
            cipher!!.init(Cipher.ENCRYPT_MODE, desKey)
            CipherInputStream(ByteArrayInputStream(input.toByteArray()), cipher)
        } else {
            cipher!!.init(Cipher.DECRYPT_MODE, desKey)
            CipherInputStream(ByteArrayInputStream(Base64.decode(input, Base64.DEFAULT)), cipher)
        }
        output = ByteArrayOutputStream()
        val buffer = ByteArray(64)
        var numBytes: Int
        while (cis.read(buffer).also { numBytes = it } != -1) {
            output!!.write(buffer, 0, numBytes)
        }
    }

    @Throws(IOException::class, InvalidKeyException::class,
        NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    private fun decode(input: String, key: String): String {
        DES_CreateKeys(input, key, false)
        return output.toString()
    }

    private fun CheckLengthPassword(password: String): String {
        val startStr = password.length
        val new_password = StringBuilder()
        for (i in 0 until startStr) {
            new_password.append(password[i])
        }
        for (i in startStr..7) {
            new_password.append("#")
        }
        Log.e("passwd", new_password.toString())
        return new_password.toString()
    }

    private fun DES_Encode() {
        val input = (findViewById<View>(R.id.input_text) as EditText).text.toString()
        var password = (findViewById<View>(R.id.password) as EditText).text.toString()
        val output = findViewById<TextView>(R.id.secret_text)
        try {
            password = CheckLengthPassword(password)
            val secretText = code(input, password)
            output.text = secretText
            if (input != decode(secretText, password)) {
                // Расшифровка текста приводит к другому результату.
                    MyToast("Внутренняя ошибка шифрования")
            } else MyToast("Зашифровано")
        } catch (e: Exception) {
            MyToast("Заполните все поля!")
        }
    }

    private fun DES_Decode() {
        val input = (findViewById<View>(R.id.input_text) as EditText).text.toString()
        var password = (findViewById<View>(R.id.password) as EditText).text.toString()
        try {
            password = CheckLengthPassword(password)
            val output = decode(input, password)
            (findViewById<View>(R.id.secret_text) as TextView).text = output
            MyToast("Дешифровано")

        } catch (e: Exception) {
            MyToast("Заполните все поля!")
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//        MyToast("Шифры")
//        finish()
//    }
}