package com.extrime.my_criptographer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class AboutProgramFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        val view = inflater.inflate(R.layout.aboutprogram_activity, container, false)
        val textView = view.findViewById<TextView>(R.id.tvAboutProgram)
        textView.append(getString(R.string.about_program))
        return view
    }
}
