package com.example.progettobiblioteca

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.progettobiblioteca.Notifica.Companion.showNotification

val DATABASE_NAME="Library"
val TABLE_NAME="Users"
val COL_EMAIL="Email"
val COL_PASSWORD="Password"
val COL_ID= "_id"
class DataBaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME+ "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT);";
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun addUser(context: Context,email: String, password: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_EMAIL, email)
        cv.put(COL_PASSWORD, password)
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == -1L) {
            val title = "failed"
            val message = " errore nella registrazione"
            showNotification(context, title, message)
        } else {
            val title = "success"
            val message = " utente registrato con successo"
            showNotification(context, title, message)
        }
    }

    fun checkEmail(context: Context, email: String,password: String) {
        val dbHelper = DataBaseHelper(context)
        val db = dbHelper.readableDatabase
        val query = "SELECT $COL_EMAIL FROM $TABLE_NAME WHERE $COL_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        val emailExists = cursor.count > 0
        cursor.close()

         if (emailExists) {
            val title = "errore"
            val message = "Email già presente nel database"
            showNotification(context, title, message)

        } else {
            dbHelper.addUser(context, email,password)

        }
    }




}