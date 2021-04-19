package com.extrime.my_criptographer.ui.Ciphers

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.extrime.my_criptographer.Base64
import com.extrime.my_criptographer.R
import com.extrime.my_criptographer.StartActivity
import java.lang.Exception
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class RSA : StartActivity() {
    private var privateKey: PrivateKey
    private var publicKey: PublicKey
    val algorithm = "RSA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rsa_activity)
    }

    fun getStringPublicKey() : String { return Base64.getEncoder().encodeToString(this.publicKey.encoded); }
    fun getStringPrivateKey() : String { return Base64.getEncoder().encodeToString(this.privateKey.encoded); }

    fun RSA_OnClick(view: View){
        when(view.id){
            R.id.BtnEncrypt -> {
                try {
                    var text = findViewById<EditText>(R.id.input_text).text.toString()
                    var key = findViewById<EditText>(R.id.password).text.toString()
                    var secret_text = findViewById<TextView>(R.id.secret_text)

                    if (key.equals("")) secret_text.text = encrypt(text, getStringPublicKey())
                    else secret_text.text = encrypt(text, key)

                    MyToast("Зашифровано")
                }catch (e: Exception){
                    MyToast("Вы уже зашифровали сообщение!")
                }
            }

            R.id.BtnDecrypt -> {
                try {
                    var text = findViewById<EditText>(R.id.input_text).text.toString()
                    var key = findViewById<EditText>(R.id.password).text.toString()
                    var secret_text = findViewById<TextView>(R.id.secret_text)

                    if (key.equals("")) secret_text.text = decrypt(text, getStringPrivateKey())
                    else secret_text.text = decrypt(text, key)
                    MyToast("Дешифровано")

                } catch (e: Exception){
                    MyToast("Введите приватный ключ")
                    var secret_text = findViewById<TextView>(R.id.secret_text)
                    secret_text.text = ""
                }
            }

            R.id.BtnInfo -> GoTo(this, getString(R.string.RSA_About), algorithm)

            R.id.BtnPublicKey -> copyKeys(R.id.BtnPublicKey)
            R.id.BtnPrivateKey -> copyKeys(R.id.BtnPrivateKey)
        }
    }

    fun copyKeys(button : Int){
        try {
            var copyKey = ""
            var PublicOrPrivate = ""
            if(button == R.id.BtnPublicKey) {
                copyKey = getStringPublicKey()
                PublicOrPrivate = "Публичный"
            }
            else if (button == R.id.BtnPrivateKey) {
                copyKey = getStringPrivateKey()
                PublicOrPrivate = "Приватный"
            }
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", copyKey)
            clipboard.setPrimaryClip(clip)
            if (copyKey.equals("")) MyToast("Нечего копировать!")
            else MyToast("$PublicOrPrivate ключ скопирован в буфер обмена")
        } catch (e: java.lang.Exception) { MyToast("Нечего копировать!")}
    }

    init {
        val keyGen = KeyPairGenerator.getInstance(algorithm)
        keyGen.initialize(1024)
        val pair = keyGen.generateKeyPair()
        privateKey = pair.private
        publicKey = pair.public
    }

    private fun getPublicKey(base64PublicKey: String): PublicKey? {
        var publicKey: PublicKey? = null
        try {
            val keySpec = X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.toByteArray()))
            val keyFactory = KeyFactory.getInstance(algorithm)
            publicKey = keyFactory.generatePublic(keySpec)
        }
        catch (e: Exception) { e.printStackTrace() }
        return publicKey
    }

    private fun getPrivateKey(base64PrivateKey: String): PrivateKey? {
        var privateKey: PrivateKey? = null
        try {
            val keySpec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.toByteArray()))
            val keyFactory = KeyFactory.getInstance(algorithm)
            privateKey = keyFactory!!.generatePrivate(keySpec)
        }
        catch (e: InvalidKeySpecException) { e.printStackTrace() }
        return privateKey
    }

    @Throws(Exception::class)
    private fun encrypt(data: String, publicKey: String): String {
        val cipher = Cipher.getInstance("$algorithm/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey))
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.toByteArray()))
    }

    @Throws(Exception::class)
    private fun decrypt(string_data: String, string_PrivateKey: String): String {
        val byte_data = Base64.getDecoder().decode(string_data.toByteArray())
        val privateKey = getPrivateKey(string_PrivateKey)
        val cipher = Cipher.getInstance("$algorithm/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return String(cipher.doFinal(byte_data))
    }

//    companion object {
//        @Throws(Exception::class)
//        @JvmStatic
//        fun main(args: Array<String>) {
//            try {
//                val kotlinRsaUtil = Kotlin_RSA_Util()
//                val encryptedString = kotlinRsaUtil.encrypt("Приветик", kotlinRsaUtil.getStringPublicKey())
////                val encryptedString = "fW0JJwycXsm+cX7M5v0dPQZsB8yCt1y5qexJZo5+gVmEhCKQY88ysFgOT5IjQAkuUgoRU4hnhUCWwHM3ipKX0/geWyEY9s7UoKg9matl70C6ALhHmqnDuxN2WxsTLCbLT0B0kjdcShvu55IUNbuWhzPRkvZ3a3NIAkkPFu045gY="
////                val encryptedString = "RKq0/gifvb46e2jVr71I1H97Pj+n0TLVj940eeFwo6yqcUr2XWtl/IJVfTixbtKggLy4tl8mx8W5L0QsDAmvYJ96hmL7lxrCJ+ygJqAHIJeCZHTyQL7PMq4H2rK+5gaLAF9C7ruEHkndpzzLJlIwygEK1aOYpwmOf+tCHz8qm+g="
//                val decryptedString = kotlinRsaUtil.decrypt(encryptedString, kotlinRsaUtil.getStringPrivateKey())
//                println("\nПубличный ключ: ${kotlinRsaUtil.publicKey} " +
//                        "\nПриватный ключ: ${kotlinRsaUtil.privateKey} " +
//                        "\n\nПубличный ключ-строка: ${kotlinRsaUtil.getStringPublicKey()} " +
//                        "\nПриватный ключ-строка: ${kotlinRsaUtil.getStringPrivateKey()} " +
//                        "\n\nЗашифрованная строка: $encryptedString " +
//                        "\nРасшифрованная строка: $decryptedString")
//            } catch (e: NoSuchAlgorithmException) {
//                System.err.println(e.message)
//            }
//        }
//    }
}