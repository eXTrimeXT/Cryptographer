package com.extrime.my_criptographer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.extrime.my_criptographer.ui.Algorithms.Caesar
import com.extrime.my_criptographer.ui.Algorithms.SwitchChar
import com.extrime.my_criptographer.ui.Algorithms.eXT
import com.extrime.my_criptographer.ui.Ciphers.AES
import com.extrime.my_criptographer.ui.Ciphers.BlowFish
import com.extrime.my_criptographer.ui.Ciphers.DES
import com.extrime.my_criptographer.ui.Settings.ThemeColors
import java.lang.Exception
import java.util.*

open class MainActivity : StartActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)
        onToolBar()
    }

    fun onToolBar() {
        try {
            val toolbar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
        }catch (e: Exception){
            Log.e(TAG, " ToolBar Error !!!")
        }finally {
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            val navView: NavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment)
            // Передаем каждый идентификатор меню, как набор идентификаторов,
            // потому что каждое меню следует рассматривать как пункты назначения верхнего уровня.
            appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_algorithms,
                R.id.nav_ciphers,
                R.id.nav_settings,
                R.id.nav_about_program
            ), drawerLayout)

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (!isExit) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        finish()
    }

}