 package com.example.progettobiblioteca

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Switch
import com.example.progettobiblioteca.Notifica.Companion.showNotification

val DATABASE_NAME="Library"
val TABLE_NAME="Users"
 val TABLE_NAME_BOOKS = "Libri"
 val TABLE_NAME_FILM= "Film"
val COL_EMAIL="Email"
val COL_PASSWORD="Password"
val COL_ADMIN="Admin"
val COL_ID= "_id"
 val COL_BOOK_TITOLO= "Titolo"
 val COL_BOOK_AUTORE= "Autore"
 val COL_ANNO="Anno"
 val COL_DATA_NOLLEGGIO="Data Nolleggio"
 val COL_DATA_RESTITUZIONE= "Data Restituzione"
 val COL_FILM_TITOLO= "Titolo"
 val COL_FILM_REGISTA= "Regista"

class DataBaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME+ "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_ADMIN + " INTEGER);";
        db?.execSQL(createTable)

        val createBookTable= "CREATE TABLE " + TABLE_NAME_BOOKS+ "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_BOOK_TITOLO + " TEXT, "+
                COL_BOOK_AUTORE+ " TEXT, "+
                COL_ANNO+ " INTEGER, "+
                COL_DATA_NOLLEGGIO + " TEXT NULL, "+
                COL_DATA_RESTITUZIONE+" TEXT NULL);";
        db?.execSQL(createBookTable)

        val createFilmTable= "CREATE TABLE " + TABLE_NAME_FILM + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_FILM_TITOLO+ " TEXT, "+
                COL_FILM_REGISTA+ " TEXT, "+
                COL_ANNO+ " INTEGER, "+
                COL_DATA_NOLLEGGIO + " TEXT NULL, "+
                COL_DATA_RESTITUZIONE+" TEXT NULL);";
        db?.execSQL(createFilmTable)




    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    private fun addUser(context: Context, email: String, password: String, @SuppressLint("UseSwitchCompatOrMaterialCode") admin: Switch) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_EMAIL, email)
        cv.put(COL_PASSWORD, password)
        cv.put(COL_ADMIN,if (admin.isChecked) 1 else 0)
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

    fun checkEmail(context: Context, email: String, password: String, @SuppressLint("UseSwitchCompatOrMaterialCode") admin: Switch) {
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
            dbHelper.addUser(context, email,password,admin)

        }
    }
    fun addBook( context:Context, titolo:String, autore:String, anno:Int){
        val dbHelper = DataBaseHelper(context)
            val db = dbHelper.writableDatabase
            val cv = ContentValues().apply {
                put(COL_BOOK_TITOLO, titolo)
                put(COL_BOOK_AUTORE, autore)
                put(COL_ANNO, anno)

        }
            val result = db.insert(TABLE_NAME_BOOKS, null, cv)
            db.close()
            if (result == -1L) {
                val title = "Errore"
                val message = "Errore durante l'aggiunta del libro"
                showNotification(context, title, message)
            } else {
                val title = "Successo"
                val message = "Libro aggiunto con successo"
                showNotification(context, title, message)
            }

    }



}



