package com.example.progettobiblioteca
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView



class MenuHandler(private val navView: NavigationView, private val bottomNavigationView: BottomNavigationView) {

    init {
        setupNavigationMenu()
        //setupBottomNavigation()
    }

    private fun setupNavigationMenu() {
        navView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationMenuItemClick(menuItem)
        }
    }

    /*private fun setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            handleBottomNavigationItemClick(menuItem)
        }*/
    }

    private fun handleNavigationMenuItemClick(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.nav_news -> {
                // Gestisci il click sul primo elemento del menu di navigazione
                true
            }
            R.id.nav_catalog -> {
                // Gestisci il click sul secondo elemento del menu di navigazione
                true
            }
            R.id.nav_loan -> {
                // Gestisci il click sul secondo elemento del menu di navigazione
                true
            }
            R.id.nav_settings -> {
                // Gestisci il click sul secondo elemento del menu di navigazione
                true
            }
            R.id.nav_logout -> {
                // Gestisci il click sul secondo elemento del menu di navigazione
                true
            }
            // Aggiungi più casi se hai più elementi nel tuo menu di navigazione
            else -> false
        }
    }

    private fun handleBottomNavigationItemClick(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.bottom_nav_item1 -> {
                // Gestisci il click sul primo elemento della barra di navigazione inferiore
                true
            }
            R.id.bottom_nav_item2 -> {
                // Gestisci il click sul secondo elemento della barra di navigazione inferiore
                true
            }
            // Aggiungi più casi se hai più elementi nella tua barra di navigazione inferiore
            else -> false
        }
    }

