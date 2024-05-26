 package com.example.progettobiblioteca

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Switch
import com.example.progettobiblioteca.Notifica.Companion.showNotification
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar

 val DATABASE_NAME="Library"
val TABLE_NAME="Users"
 val TABLE_NAME_BOOKS = "Libri"
 val TABLE_NAME_FILM= "Film"
 val TABLE_NAME_CANZONE= "Canzone"
val COL_EMAIL="Email"
val COL_PASSWORD="Password"
val COL_ADMIN="Admin"
val COL_ID= "_id"
 val COL_BOOK_TITOLO= "TitoloBook"
 val COL_BOOK_AUTORE= "Autore"
 val COL_ANNO="AnnoBook"
 val COL_FILM_ANNO="AnnoFilm"
 val COL_MUSICA_ANNO="AnnoMusica"
 val COL_DATA_NOLEGGIO="DataNoleggio"
 val COL_DATA_RESTITUZIONE= "DataRestituzione"
 val COL_FILM_TITOLO= "TitoloFilm"
 val COL_FILM_REGISTA= "Regista"
 val COL_MUSICA_TITOLO="TitoloMusica"
 val COL_MUSICA_CANTANTE="Cantante"
 val COL_MUSICA_GENERE="Genere"
 val TABLE_PRESTITI= "Prestiti"
 val COL_USER_ID="user_id"
 val COL_ITEM_ID ="item_id"

 val DATABASE_VERSION = 1

class DataBaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = "CREATE TABLE " + TABLE_NAME+ "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_ADMIN + " INTEGER);"
        db?.execSQL(createUserTable)

        val createBookTable= "CREATE TABLE " + TABLE_NAME_BOOKS+ "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_BOOK_TITOLO + " TEXT, "+
                COL_BOOK_AUTORE+ " TEXT, "+
                COL_ANNO+ " INTEGER); "
        db?.execSQL(createBookTable)

        val createFilmTable= "CREATE TABLE " + TABLE_NAME_FILM + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_FILM_TITOLO+ " TEXT, "+
                COL_FILM_REGISTA+ " TEXT, "+
                COL_FILM_ANNO+ " INTEGER); "

        db?.execSQL(createFilmTable)

        val createMusicaTable= "CREATE TABLE " + TABLE_NAME_CANZONE + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_MUSICA_TITOLO+ " TEXT, "+
                COL_MUSICA_CANTANTE+ " TEXT, "+
                COL_MUSICA_ANNO+ " INTEGER, "+
                COL_MUSICA_GENERE+ " TEXT); "
        db?.execSQL(createMusicaTable)



        val createPrestitiTable="CREATE TABLE "+ TABLE_PRESTITI + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_USER_ID+ " INTEGER NOT NULL, "+
                COL_ITEM_ID+ " INTEGER NOT NULL, "+
                COL_DATA_NOLEGGIO+ " TEXT DEFAULT NULL, "+
                COL_DATA_RESTITUZIONE+ " TEXT DEFAULT NULL); "
        db?.execSQL(createPrestitiTable)




    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_BOOKS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_FILM")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_CANZONE")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRESTITI")

        onCreate(db)
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


        fun addBook( context:Context, titolo:String, autore:String, anno:Int) {

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

    fun addFilm( context:Context, titolo:String, regista:String, anno:Int) {

        val dbHelper = DataBaseHelper(context)
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put(COL_FILM_TITOLO, titolo)
            put(COL_FILM_REGISTA, regista)
            put(COL_FILM_ANNO, anno)

        }


        val result = db.insert(TABLE_NAME_FILM, null, cv)
        db.close()
        if (result == -1L) {
            val title = "Errore"
            val message = "Errore durante l'aggiunta del film"
            showNotification(context, title, message)
        } else {
            val title = "Successo"
            val message = "film aggiunto con successo"
            showNotification(context, title, message)
        }
    }

    fun addMusic( context:Context, titolo:String, cantante:String, anno:Int, genere:String) {

        val dbHelper = DataBaseHelper(context)
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put(COL_MUSICA_TITOLO, titolo)
            put(COL_MUSICA_CANTANTE, cantante)
            put(COL_MUSICA_ANNO, anno)
            put(COL_MUSICA_GENERE , genere)

        }


        val result = db.insert(TABLE_NAME_CANZONE, null, cv)
        db.close()
        if (result == -1L) {
            val title = "Errore"
            val message = "Errore durante l'aggiunta della canzone"
            showNotification(context, title, message)
        } else {
            val title = "Successo"
            val message = "canzone aggiunta con successo"
            showNotification(context, title, message)
        }
    }


}



