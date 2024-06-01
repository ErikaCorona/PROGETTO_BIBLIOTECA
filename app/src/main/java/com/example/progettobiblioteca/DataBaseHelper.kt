package com.example.progettobiblioteca

import android.content.Context


class DataBaseHelper(private val context: Context) {
    companion object {
        const val COLLECTION_USERS = "Users"
        const val COLLECTION_BOOKS = "Libri"
        const val COLLECTION_FILM = "Film"
        const val COLLECTION_MUSIC = "Canzone"
        const val COLLECTION_PRESTITI = "Prestiti"


        const val COL_EMAIL = "Email"
        const val COL_PASSWORD = "Password"
        const val COL_ADMIN = "Admin"
        const val COL_BOOK_TITOLO = "Titolo"
        const val COL_BOOK_AUTORE = "Autore"
        const val COL_ANNO = "Anno"
        const val COL_FILM_TITOLO = "Titolo"
        const val COL_FILM_REGISTA = "Regista"
        const val COL_FILM_ANNO = "Anno"
        const val COL_MUSICA_TITOLO = "Titolo"
        const val COL_MUSICA_CANTANTE = "Cantante"
        const val COL_MUSICA_ANNO = "Anno"
        const val COL_MUSICA_GENERE = "Genere"
        const val COL_PRESTITI_USERID = "userId"
        const val COL_PRESTITI_ITEMID = "itemId"
        const val COL_PRESTITI_DATANOLEGGIO = "dataNoleggio"
        const val COL_PRESTITI_DATARESTITUZIONE = "dataRestituzione"
        const val COL_PRESTITI_NAME= "collectionName"
        }

}
