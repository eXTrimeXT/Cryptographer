package com.extrime.my_criptographer.ui.Settings

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.extrime.my_criptographer.R
import com.extrime.my_criptographer.StartActivity.Companion.TAG
import com.extrime.my_criptographer.StartActivity.Companion.isToast
import com.extrime.my_criptographer.StartActivity.Companion.NAME_SETTINGS
import com.extrime.my_criptographer.StartActivity.Companion.isExit

class SettingsFragment : Fragment() {

    companion object {
        private var savedToast = true
        private var savedExit = false
    }
    private lateinit var switchToast: Switch
    private lateinit var switchExit: Switch
    private lateinit var prefs : SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        val view = inflater.inflate(R.layout.settings_activity, container, false)

        prefs = this.requireActivity().getSharedPreferences(NAME_SETTINGS, MODE_PRIVATE)

        initToast(view)
        initExit(view)

        return view
    }

    fun initExit(view: View){
        switchExit = view.findViewById(R.id.setExit)
        savedExit = prefs.getBoolean("Exit", false)
        switchExit.setChecked(savedExit)
        checkExit()
        switchExit.setOnClickListener{ checkExit() }
    }

    fun checkExit(){
        if (switchExit.isChecked) {
            isExit = true
            Log.e(TAG, "EXIT ON")
        } else {
            Log.e(TAG, "EXIT OFF")
            isExit = false
            switchExit.isChecked = false
        }
    }

    fun initToast(view: View) {
        switchToast = view.findViewById(R.id.setToast)
        savedToast = prefs.getBoolean("Toast", true)
        switchToast.setChecked(savedToast)
        checkToast()
        switchToast.setOnClickListener{ checkToast() }
    }

    fun checkToast(){
        if (switchToast.isChecked) {
            isToast = true
            Log.e(TAG, "TOAST ON")
        } else {
            Log.e(TAG, "TOAST OFF")
            isToast = false
            switchToast.isChecked = false
        }
    }

    override fun onPause() {
        super.onPause()
        // пишем нужное в SharedPreferences
        val ed = this.requireActivity().getSharedPreferences(NAME_SETTINGS, MODE_PRIVATE).edit()
        ed.putBoolean("Toast", switchToast.isChecked())
        ed.putBoolean("Exit", switchExit.isChecked())
        ed.commit()
    }
}