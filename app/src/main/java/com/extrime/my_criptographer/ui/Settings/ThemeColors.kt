package com.extrime.my_criptographer.ui.Settings

import android.content.Context
import android.os.Build
import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.extrime.my_criptographer.R
import com.extrime.my_criptographer.StartActivity
import com.extrime.my_criptographer.StartActivity.Companion.TAG
import com.extrime.my_criptographer.StartActivity.Companion.NAME_SETTINGS

class ThemeColors(context: Context) {
    var color: Int

    // Checking if title text color will be black
    private val isLightActionBar: Boolean
        get() { // Checking if title text color will be black
            val rgb = (Color.red(color) + Color.green(color) + Color.blue(color)) / 3
            return rgb > 210
        }

    companion object {
        fun setNewThemeColor(activity: StartActivity, red: Int, green: Int, blue: Int) {
            var red = red
            var green = green
            var blue = blue
            val colorStep = 15
            red = Math.round((red / colorStep).toFloat()) * colorStep
            green = Math.round((green / colorStep).toFloat()) * colorStep
            blue = Math.round((blue / colorStep).toFloat()) * colorStep
            val stringColor = Integer.toHexString(Color.rgb(red, green, blue)).substring(2)
            val editor = activity.getSharedPreferences(NAME_SETTINGS, Context.MODE_PRIVATE).edit()
            editor.putString("color", stringColor)
            editor.apply()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) activity.recreate()
            else {
                val i = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
                i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                activity.startActivity(i)
            }
        }
    }

    init {
        val sharedPreferences = context.getSharedPreferences(NAME_SETTINGS, Context.MODE_PRIVATE)
        val stringColor = sharedPreferences.getString("color", "B64D4D")
        color = Color.parseColor("#$stringColor")
        if (isLightActionBar) context.setTheme(R.style.AppTheme)
        context.setTheme(
            context.resources.getIdentifier
                ("T_$stringColor", "style", context.packageName)
        )
    }
}