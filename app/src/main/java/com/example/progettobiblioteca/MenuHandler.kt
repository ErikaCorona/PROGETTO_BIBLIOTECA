package com.example.progettobiblioteca

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MenuHandler : AppCompatActivity() {
     lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_search -> Toast.makeText(
                    applicationContext,
                    " search cliccato",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_news -> Toast.makeText(
                    applicationContext,
                    " news cliccato",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.nav_catalog -> Toast.makeText(
                    applicationContext,
                    " catalogo cliccato",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.nav_loan -> Toast.makeText(
                    applicationContext,
                    " loan cliccato",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.nav_settings -> Toast.makeText(
                    applicationContext,
                    " parametri cliccato",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.nav_logout -> Toast.makeText(
                    applicationContext,
                    " logout cliccato",
                    Toast.LENGTH_SHORT
                ).show()

            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){

        }
        return true
    }
}
