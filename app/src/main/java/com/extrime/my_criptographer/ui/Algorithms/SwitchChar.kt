package com.extrime.my_criptographer.ui.Algorithms

import android.widget.EditText
import android.os.Bundle
import android.view.View
import com.extrime.my_criptographer.R
import com.extrime.my_criptographer.StartActivity

class SwitchChar : StartActivity() {
    private var edit_textET: EditText? = null
    private var from_charET: EditText? = null
    private var to_charET: EditText? = null
    private var from_char: String? = null
    private var to_char: String? = null
    private var edit_text: String? = null
    var algorithm = "SWITCH_CHAR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.switch_char_activity)
        from_charET = findViewById(R.id.password)
        to_charET = findViewById(R.id.secret_text)
        edit_textET = findViewById(R.id.input_text)
        initAds() // todo: Init Ads
    }

    fun onClick(context: View?) {
        GoTo(this, getString(R.string.SwitchChar_About), algorithm)
        MyToast("About")
    }

    fun Clear_Text(context: View?) {
        from_charET!!.setText("")
        to_charET!!.setText("")
        edit_textET!!.setText("")
        MyToast("Очищено")
    }

    fun replace(context: View?) {
        from_char = from_charET!!.text.toString()
        to_char = to_charET!!.text.toString()
        edit_text = edit_textET!!.text.toString()
        edit_text = edit_text!!.replace(from_char!!.toRegex(), to_char!!)
        edit_textET!!.setText(edit_text)
        MyToast("Изменено")
    }
}