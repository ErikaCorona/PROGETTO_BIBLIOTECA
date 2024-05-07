package com.example.progettobiblioteca

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME="Library"
val TABLE_NAME="Users"
val COL_EMAIL="Email"
val COL_PASSWORD="Password"
val COL_ID="id"
class DataBaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable= "CREATE TABLE " + TABLE_NAME+ "(" + COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," + COL_EMAIL + "VARCHAR(256)" + COL_PASSWORD + "VARCHAR(20)";
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
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
        }
    }


}