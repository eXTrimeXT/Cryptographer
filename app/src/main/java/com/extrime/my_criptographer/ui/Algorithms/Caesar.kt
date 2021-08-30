package com.extrime.my_criptographer.ui.Algorithms

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.extrime.my_criptographer.R
import com.extrime.my_criptographer.StartActivity
import java.lang.Exception
import java.lang.StringBuilder

class Caesar : StartActivity() {
    private var c = 0
    private var y = 0
    private var maxStep: Byte = 16
    private var conditionStep = "Шаг должен быть < " + (maxStep + 1)
    private var algorithm = "CAESAR"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.caesar_activity)
        SetOnClick(caesarListener)
        initAds() // todo: Init Ads
    }

    private val step: Byte get() = (findViewById<View>(R.id.password) as EditText).text.toString().toByte()

    private val caesarListener = View.OnClickListener { view: View ->
        when (view.id) {
            R.id.BtnInfo -> GoTo(this@Caesar, getString(R.string.Caesar_About), algorithm)
            R.id.BtnEncrypt -> ClickLeft(true)
            R.id.BtnDecrypt -> ClickLeft(false)
        }
    }

    private fun ClickLeft(isLeft: Boolean) {
        try {
            val text = (findViewById<View>(R.id.input_text) as EditText).text.toString()
            val new_str = StringBuilder()
            for (i in text.indices) {
                /* TODO: получаем букву по коду, добавляем шаг, в зависимости от движения влево или вправо
                    1040 - это код буквы А, 32 - это кол-во букв */
                val zz = if (isLeft) text[i].toInt() + step else text[i].toInt() - step
                Log.d(TAG, "zz = $zz & ${zz.toChar()}")
                y = (zz - 1040) % 32 + 1040 // 1040 - это код буквы А, 32 - это кол-во букв
                new_str.append(y.toChar()) // получаем букву по коду
            }
            (findViewById<View>(R.id.secret_text) as TextView).text = new_str.toString()
        } catch (e: Exception) {
            MyToast("Заполните все поля!")
        }
    }
}