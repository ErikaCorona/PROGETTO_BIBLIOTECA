package com.example.signlogintab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.progettobiblioteca.LoginFragm
import com.example.progettobiblioteca.SignupFragm

class AdatatoreVistaPagina (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            SignupFragm()
        } else LoginFragm()
    }

    override fun getItemCount(): Int {
        return 2
    }
}