package com.example.progettobiblioteca

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MenuHandler : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)

        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")
        if (!email.isNullOrEmpty()) {
            updateUserEmail(email)
        }

        navView.setNavigationItemSelectedListener(this)

        // Carica il NewsFragment se non esiste già un frammento salvato
        if (savedInstanceState == null) {
            openFragment(NewsFragment())
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val email = getSharedPreferences("user_data", Context.MODE_PRIVATE).getString("email", "")
        when (menuItem.itemId) {
            R.id.add -> {
                if (!email.isNullOrEmpty()) {
                    LoginFragm.AdminManager.isAdmin(email) { isAdmin ->
                        if (isAdmin) {
                            openFragment(AddFragm())
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Non hai i permessi per questa azione",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Email non trovata. Effettua nuovamente il login.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.nav_search -> openFragment(SearchFragment())
            R.id.nav_news -> openFragment(NewsFragment())
            R.id.nav_loan -> openFragment(OnLoanFragm())
            R.id.nav_settings -> Toast.makeText(
                applicationContext,
                "Parametri cliccato",
                Toast.LENGTH_SHORT
            ).show()
            R.id.nav_logout -> openFragment(LogoutFrag())
        }
        return true
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        drawerLayout.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateUserEmail(email: String) {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navigationView.getHeaderView(0)
        val userEmailTextView: TextView = headerView.findViewById(R.id.emailTextView)
        userEmailTextView.text = email
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}
