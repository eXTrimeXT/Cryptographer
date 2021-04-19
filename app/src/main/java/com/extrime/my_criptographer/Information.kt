package com.extrime.my_criptographer

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.extrime.my_criptographer.ui.Algorithms.Caesar
import com.extrime.my_criptographer.ui.Algorithms.SwitchChar
import com.extrime.my_criptographer.ui.Algorithms.eXT
import com.extrime.my_criptographer.ui.Ciphers.AES
import com.extrime.my_criptographer.ui.Ciphers.BlowFish
import com.extrime.my_criptographer.ui.Ciphers.DES
import com.extrime.my_criptographer.ui.Ciphers.RSA
import com.extrime.my_criptographer.ui.Settings.ThemeColors
import java.lang.Exception

class Information : AppCompatActivity() {
    var about_algorithm: String? = null
    var algorithm: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeColors(this)
        setContentView(R.layout.information_activity)
        about_algorithm = getIntent().getStringExtra("about")
        (findViewById<View>(R.id.about) as TextView).text =
            """$about_algorithm""".trimIndent()
        algorithm = getIntent().getStringExtra("Algorithm")
        Log.e(StartActivity.TAG, algorithm!!)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        GoToBack()
    }

    private fun GoToBack() {
        try {
            if (algorithm == "AES") intent = Intent(this, AES::class.java)
            else if (algorithm == "BLOWFISH") intent = Intent(this, BlowFish::class.java)
            else if (algorithm == "CAESAR") intent = Intent(this, Caesar::class.java)
            else if (algorithm == "DES") intent = Intent(this, DES::class.java)
            else if (algorithm == "RSA") intent = Intent(this, RSA::class.java)
            else if (algorithm == "EXT") intent = Intent(this, eXT::class.java)
            else if (algorithm == "SWITCH_CHAR") intent = Intent(this, SwitchChar::class.java)
        } catch (e: Exception) {
            Log.e(StartActivity.TAG, "Information Class is have Error !!!")
        }
        startActivity(intent)
        finish()
        if (StartActivity.isToast) Toast.makeText(this, "Вы вернулись к $algorithm", Toast.LENGTH_SHORT).show()
    }
}