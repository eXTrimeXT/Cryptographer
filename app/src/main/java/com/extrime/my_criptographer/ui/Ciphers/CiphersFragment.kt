package com.extrime.my_criptographer.ui.Ciphers

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.extrime.my_criptographer.R
import com.extrime.my_criptographer.StartActivity

class CiphersFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        val view =  inflater.inflate(R.layout.ciphers_activity, container, false)
//        checkVersion(view)
        return view
    }

    private fun checkVersion(view: View) {
        val version = Build.VERSION.SDK_INT
        Log.e(StartActivity.TAG, "version: $version")
//        if (Build.VERSION.SDK_INT > 26) {
            val button_aes = view.findViewById<Button>(R.id.btnAES)
            val button_blowfish = view.findViewById<Button>(R.id.btnBlowFish)
            button_aes.visibility = View.GONE // скрываем кнопки
            button_blowfish.visibility = View.GONE
//        }
    }
}