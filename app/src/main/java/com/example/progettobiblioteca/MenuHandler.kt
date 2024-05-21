package com.example.progettobiblioteca

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
                R.id.add->openFragment(AddFragm())
                R.id.nav_search -> openFragment(SearchFragment())

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

                R.id.nav_loan -> openFragment(OnLoanFragm())

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

    private fun openFragment(fragment: Fragment): Boolean {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawers()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){

        }
        return true
    }
}
