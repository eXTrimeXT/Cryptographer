package com.extrime.my_criptographer.ui.Ciphers

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.extrime.my_criptographer.R
import com.extrime.my_criptographer.StartActivity

class CiphersFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        val view =  inflater.inflate(R.layout.ciphers_activity, container, false)
        return view
    }
}