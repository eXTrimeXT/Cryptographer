package com.extrime.my_criptographer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.extrime.my_criptographer.ui.Algorithms.Caesar
import com.extrime.my_criptographer.ui.Algorithms.SwitchChar
import com.extrime.my_criptographer.ui.Algorithms.eXT
import com.extrime.my_criptographer.ui.Ciphers.AES
import com.extrime.my_criptographer.ui.Ciphers.BlowFish
import com.extrime.my_criptographer.ui.Ciphers.DES
import com.extrime.my_criptographer.StartActivity.Companion.NAME_SETTINGS
import com.extrime.my_criptographer.ui.Ciphers.RSA
import com.extrime.my_criptographer.ui.Settings.SettingsFragment
import com.extrime.my_criptographer.ui.Settings.ThemeColors
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.io.File
import java.util.*

open class StartActivity : AppCompatActivity() {

    companion object {
        val NAME_SETTINGS = "settings"
        val TAG = "eXTrimeTAG"
        var isToast = true
        var isExit = false

        var copyText = ""
        var btn_Copy: Button? = null
        var btn_Paste: Button? = null
        var btn_Clear: Button? = null
        var btn_info: ImageButton? = null
        var btn_Encrypt: Button? = null
        var btn_Decrypt: Button? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeColors(this)
        checkSettings()
    }

    open fun MyToast(text: String?) {
        if (isToast) Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun GoTo(text: String?) {
        try {
            startActivity(intent)
            MyToast(text)
            finish()
        } catch (e: Exception) {
            MyToast("MANIFEST!!!")
        }
    }

    fun GoTo(context: Context?, about: String?, algorithm: String?) {
        intent = Intent(context, Information::class.java)
        intent!!.putExtra("about", about)
        intent!!.putExtra("Algorithm", algorithm)
        startActivity(intent)
        MyToast("About")
    }

    fun changeTheme(v: View) {
        val red: Int = Random().nextInt(255)
        val green: Int = Random().nextInt(255)
        val blue: Int = Random().nextInt(255)
        ThemeColors.setNewThemeColor(this, red, green, blue)
        MyToast("Тема сменилась")
    }

    fun ClearText() {
        (findViewById<View>(R.id.input_text) as EditText).setText("")
        (findViewById<View>(R.id.password) as EditText).setText("")
        (findViewById<View>(R.id.secret_text) as TextView).text = ""
        MyToast("Очищено")
    }

    fun CopySecretText() {
        try {
            copyText = (findViewById<View>(R.id.secret_text) as TextView).text.toString()
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", copyText)
            clipboard.setPrimaryClip(clip)
            if (copyText.equals("")) MyToast("Нечего копировать!")
            else MyToast("Скопировано в буфер обмена")
        } catch (e: java.lang.Exception) {
            MyToast("Нечего копировать!")
        }
    }

    fun PasteText() {
        (findViewById<View>(R.id.input_text) as EditText).setText(copyText)
        MyToast("Текст вставлен")
    }

    open fun SetOnClick(onClickListener: View.OnClickListener?) {
        // 1
        btn_info = findViewById(R.id.BtnInfo)
        btn_Encrypt = findViewById(R.id.BtnEncrypt)
        btn_Decrypt = findViewById(R.id.BtnDecrypt)
        btn_info!!.setOnClickListener(onClickListener)
        btn_Encrypt!!.setOnClickListener(onClickListener)
        btn_Decrypt!!.setOnClickListener(onClickListener)
    }

    fun MainClick(view: View) {
        when (view.id) {
            R.id.BtnCopyText -> CopySecretText()
            R.id.BtnPasteText -> PasteText()
            R.id.BtnClearText -> ClearText()

            R.id.go_vk1, R.id.go_vk2, R.id.go_vk3 -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.vk_link)))
                startActivity(browserIntent)
            }
            R.id.setup_new_apk -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_link)))
                startActivity(browserIntent)
                MyToast("Нажмите на кнопку \"Donwload\" ")
            }
            R.id.btnAES -> {
                intent = Intent(this, AES::class.java)
                GoTo("AES")
            }
            R.id.btnBlowFish -> {
                intent = Intent(this, BlowFish::class.java)
                GoTo("BlowFish")
            }
            R.id.btnCaesar -> {
                intent = Intent(this, Caesar::class.java)
                GoTo("Caesar")
            }
            R.id.btnDES -> {
                intent = Intent(this, DES::class.java)
                GoTo("DES")
            }
            R.id.btnRSA -> {
                intent = Intent(this, RSA::class.java)
                GoTo("RSA")
            }
            R.id.btnEXT -> {
                intent = Intent(this, eXT::class.java)
                GoTo("eXT")
            }
            R.id.btnSwitchChar -> {
                intent = Intent(this, SwitchChar::class.java)
                GoTo("Switch Char")
            }
        }
    }

    fun checkSettings(){
        val prefs = this.getSharedPreferences(NAME_SETTINGS, MODE_PRIVATE)
        isToast = prefs.getBoolean("Toast", true)
        isExit = prefs.getBoolean("Exit", false)
    }

    override fun onPause() {
        super.onPause()
        checkSettings()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}