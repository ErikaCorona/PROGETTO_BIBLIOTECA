package com.example.progettobiblioteca
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.graphics.Color

class MainActivity_secondaschermatamenulaterale : AppCompatActivity() {


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun showBottomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layoutinferiore)

        val musicLayout = dialog.findViewById<LinearLayout>(R.id.layoutMusic)
        val filmsLayout = dialog.findViewById<LinearLayout>(R.id.layoutFilms)
        val booksLayout = dialog.findViewById<LinearLayout>(R.id.layoutBooks)
        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)

        musicLayout.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity_secondaschermatamenulaterale, "Search music cliccato", Toast.LENGTH_SHORT).show()
        }

        filmsLayout.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity_secondaschermatamenulaterale, "Search films cliccato", Toast.LENGTH_SHORT).show()
        }

        booksLayout.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity_secondaschermatamenulaterale, "Search books cliccato", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener { dialog.dismiss() }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}

