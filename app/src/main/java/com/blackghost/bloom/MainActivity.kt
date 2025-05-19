package com.blackghost.bloom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.blackghost.bloom.Fragment.InfoFragment
import com.blackghost.bloom.Fragment.MainFragment
import com.blackghost.bloom.Fragment.SettingsFragment
import com.blackghost.bloom.Manager.PhotoManager
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var open_menu_button: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
//        supportActionBar?.title = R.string.app_name.toString()

        drawerLayout = findViewById(R.id.drawer_layout)
        open_menu_button = findViewById(R.id.open_menu_button)


        open_menu_button.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.END)
        }



        val navView: NavigationView = findViewById(R.id.navigation_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_randomize -> {
                    val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                    if (fragment is MainFragment) {
                        fragment.refreshPhotos(shuffle = true)
                    }
                }
                R.id.menu_privacy -> {
                    val photoManager = PhotoManager(this)
                    photoManager.togglePrivacy(
                        itemIconTintCallback = { color ->
                            menuItem.icon?.setTint(ContextCompat.getColor(this, color))
                        },
                        notifyMediaScanner = {
                            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).apply {
                                data = Uri.fromFile(photoManager.getGPhotosDir())
                            })
                        }
                    )
                }
                R.id.menu_inertia -> {

                }
                R.id.menu_save_position -> {

                }
                R.id.menu_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, MainFragment())
                        .commit()
                }
                R.id.menu_settings -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, SettingsFragment())
                        .commit()
                }
                R.id.menu_info -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, InfoFragment())
                        .commit()
                }
            }
            drawerLayout.closeDrawers()
            true
        }


        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment())
                .commit()
        }
    }
}