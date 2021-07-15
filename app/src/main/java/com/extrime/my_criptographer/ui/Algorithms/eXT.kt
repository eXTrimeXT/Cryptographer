package com.extrime.my_criptographer.ui.Algorithms

import android.widget.RadioButton
import android.widget.TextView
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.extrime.my_criptographer.R
import com.extrime.my_criptographer.StartActivity
import java.lang.Exception
import java.lang.StringBuilder

class eXT : StartActivity() {
    private var input: String? = null
    private var output: String? = null
    private var algorithm = "EXT"
    private var password: Int = 0
    private var first_root: RadioButton? = null
    private var second_root: RadioButton? = null
    private var rb_chetnyu: RadioButton? = null
    private var rb_nechetnyu: RadioButton? = null
    private var keyTextView: TextView? = null
    private var isEncoding = true
    private var x1 = 0
    private var x2 = 0
    private var z = 0
    private var y = 0
    private var key = 0
    private var count = 0
    private var a = 0
    private var b = 0
    private var c = 0
    private var e = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ext_activity)
        keyTextView = findViewById(R.id.key)
        first_root = findViewById(R.id.radio_1)
        second_root = findViewById(R.id.radio_2)
        SetShowElements(false)
        rb_chetnyu = findViewById(R.id.radio_3)
        rb_nechetnyu = findViewById(R.id.radio_4)
        count = 0
        SetOnClick(eXT_Listener)
    }

    override fun SetOnClick(onClickListener: View.OnClickListener?) {
        Companion.btn_info = findViewById(R.id.BtnInfo)
        Companion.btn_Encrypt = findViewById(R.id.BtnEncrypt)
        Companion.btn_info!!.setOnClickListener(onClickListener)
        Companion.btn_Encrypt!!.setOnClickListener(onClickListener)

        btn_Clear = findViewById(R.id.BtnClearText)
        btn_Clear!!.setOnClickListener(onClickListener)
    }

    private val eXT_Listener = View.OnClickListener { view: View ->
        when (view.id) {
            R.id.BtnInfo -> GoTo(this@eXT, getString(R.string.eXT_About), algorithm)
            R.id.BtnEncrypt -> eXT_process()
            R.id.BtnClearText -> {
                ClearText()
                SetShowElements(false)
            }
        }
    }

    fun SetShowElements(Show: Boolean) {
        if (Show) {
            keyTextView!!.visibility = View.VISIBLE
            first_root!!.visibility = View.VISIBLE
            second_root!!.visibility = View.VISIBLE
        } else {
            keyTextView!!.visibility = View.GONE
            first_root!!.visibility = View.GONE
            second_root!!.visibility = View.GONE
        }
    }

    fun getInput(): String {
        return (findViewById<View>(R.id.input_text) as EditText).text.toString()
    }

    fun getPassword(): Int {
        var pass = (findViewById<View>(R.id.password) as EditText).text.toString()
        Log.e(TAG, "pass = $pass")
        if (pass == "" || pass.toInt() < 999) return 1283
        else return pass.toInt() + 1283
    }

    fun setSecretText(str: String?) {
        (findViewById<View>(R.id.secret_text) as TextView).text = str
    }

    fun eXT_process() {
        try {
            if (count == 0) {
                First_Step()
                if (input == "" || password == 0) MyToast("Заполните все поля!")
                Discriminant(a, b, c, e)
                Second_Step()
                SetShowElements(true)
                CheckSelectKey()
                CheckIsEncode()
                count++
                setSecretText("")
                Log.e(TAG, "count if = " + count)
            } else if (count == 1) {
                CheckSelectKey()
                CheckIsEncode()
                output = SwitchSymbol(input, key, isEncoding)
                setSecretText(output)
                Log.e(TAG, "count else if1 = " + count)
                count = 0
                Log.e(TAG, "count else if2 = " + count)
            }
        } catch (e: Exception) {
            Log.e(TAG, "EXCEPTION\n$e")
            if (input == "" || password == 0) MyToast("Заполните все поля!")
        }
    }

    fun First_Step() {
        input = getInput()
        password = getPassword()
        a = password / 1000
        b = password / 100 % 10
        c = password / 10 % 10
        e = password % 10
    }

    fun Second_Step() {
        first_root!!.text = x1.toString()
        second_root!!.text = x2.toString()
    }

    fun CheckSelectKey() {
        if (first_root!!.isChecked) key = x1
        else if (second_root!!.isChecked) key = x2
    }

    fun CheckIsEncode() {
        if (rb_chetnyu!!.isChecked) isEncoding = false
        else if (rb_nechetnyu!!.isChecked) isEncoding = true
    }

    fun SwitchSymbol(str: String?, step: Int, isEncode: Boolean): String {
        var zero = 1
        val new_str = StringBuilder()
        if (isEncode) {
            zero = 0
        }
        for (i in 0 until str!!.length) {
            // нечетный
            // получаем букву по коду
            // 1040 - это код буквы А, 32 - это кол-во букв
            if (i % 2 == zero) {
                z = str[i].toInt() + step
            } else {
                z = str[i].toInt() - step
            }
            y = (z - 1040) % 32 + 1040 // 1040 - это код буквы А, 32 - это кол-во букв
            new_str.append(y.toChar()) // получаем букву по коду
        }
        Log.e(TAG, "ответ = $new_str\nЧетность = $isEncode")
        return new_str.toString()
    }

    fun Discriminant(a: Int, b: Int, c: Int, e: Int) {
        try {
            this@eXT.c -= e
            val Discr = b * b - 4 * a * c
            x1 = (-b + Discr) / (2 * a)
            x2 = (-b - Discr) / (2 * a)
            Log.e("INFO EXT", "Discr = $Discr  x1 = $x1  x2 = $x2")
        } catch (exception: Exception) {
            Log.e("ERROR", "Введите число больше 999")
        }
    }
}