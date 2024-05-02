package com.example.progettobiblioteca

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.signlogintab.AdatatoreVistaPagina
import com.google.android.material.tabs.TabLayout


class MainActivity_new : AppCompatActivity() {
    private lateinit var adattatore: AdatatoreVistaPagina

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_nuovo)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)

        tabLayout.addTab(tabLayout.newTab().setText("Login"))
        tabLayout.addTab(tabLayout.newTab().setText("Signup"))

        adattatore = AdatatoreVistaPagina(supportFragmentManager, lifecycle)

        viewPager2.adapter = adattatore

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        fun onRegistrationSuccess() {
            // Azioni da eseguire quando la registrazione è completata con successo
            // Per esempio, passa all'attività successiva
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }
}
