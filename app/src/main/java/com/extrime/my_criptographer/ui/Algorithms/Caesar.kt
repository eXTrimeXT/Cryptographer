package com.extrime.my_criptographer.ui.Algorithms

import android.os.Bundle
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
            // получаем текст из поля input_text
            val text = (findViewById<View>(R.id.input_text) as EditText).text.toString()
            val step = step
            if (step > maxStep) MyToast(conditionStep)
            else {
                val new_str = StringBuilder()
                for (i in text.indices) {
                    c = if (isLeft) text[i].toByte() - step // влево
                    else text[i].toByte() + step // вправо
                    y =
                        (c - 1040) % 32 + 1040 // 1040 - это код буквы А, 32 - это кол-во букв 1072 - это код буквы а, 1103 - код буквы я
                    new_str.append(y.toChar()) // получаем букву по коду
                }
                (findViewById<View>(R.id.secret_text) as TextView).text = new_str.toString()
            }
        } catch (e: Exception) {
            MyToast("Заполните все поля!")
        }
    }
}