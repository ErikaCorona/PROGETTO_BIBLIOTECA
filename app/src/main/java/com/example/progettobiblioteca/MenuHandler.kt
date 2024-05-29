
package com.example.progettobiblioteca
import OnLoanFragm
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MenuHandler : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")
        navView.setNavigationItemSelectedListener { menuItem ->
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
                R.id.nav_logout -> openFragment(LogoutFrag())
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
