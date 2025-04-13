package com.blackghost.bloom

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var open_menu_button: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById(R.id.drawer_layout)
        open_menu_button = findViewById(R.id.open_menu_button)


        open_menu_button.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.END)
        }



        val navView: NavigationView = findViewById(R.id.navigation_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_randomize -> {

                }
                R.id.menu_privacy -> {

                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}