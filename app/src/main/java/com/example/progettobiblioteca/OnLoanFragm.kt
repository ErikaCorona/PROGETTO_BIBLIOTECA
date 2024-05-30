package com.example.progettobiblioteca

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.progettobiblioteca.DataBaseHelper.Companion.COLLECTION_PRESTITI
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class OnLoanFragm : Fragment() {

    private lateinit var nome: EditText
    private lateinit var effettuaPrestito: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_loan, container, false)
        nome = view.findViewById(R.id.item_name)
        effettuaPrestito = view.findViewById(R.id.loan_button)

        effettuaPrestito.setOnClickListener {
            val objectName = nome.text.toString().trim()
                .toLowerCase(Locale.getDefault()) // Normalizza la stringa di ricerca
            val context = requireContext()
            val userEmail = getUserEmail(context)

            // Ottieni l'ID dell'oggetto dal suo nome
            getObjectIdFromName(context, objectName) { itemId ->
                if (itemId != "") {
                    // Aggiungi il prestito utilizzando Firestore
                    addPrestito(context, userEmail, itemId)
                } else {
                    Toast.makeText(context, "Oggetto non trovato", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    private fun getObjectIdFromName(
        context: Context,
        objectName: String,
        callback: (String) -> Unit
    ) {
        val collections = listOf("Libri", "Film", "Canzone")
        var itemId = ""
        var found = false

        for (collection in collections) {
            db.collection(collection)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val title = document.getString("Titolo")?.toLowerCase(Locale.getDefault())
                            ?: "" // Normalizza i titoli nel database
                        if (title == objectName) {
                            itemId = document.id // Prendi l'ID del primo documento trovato
                            found = true
                            callback(itemId)
                            return@addOnSuccessListener
                        }
                    }
                    if (!found) {
                        callback("") // Se l'oggetto non è stato trovato in nessuna collezione
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        context,
                        "Errore durante la ricerca: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    callback("")
                }
        }
    }

    private fun addPrestito(context: Context, userEmail: String, itemId: String) {
        getUserIdFromEmail(context, userEmail) { userId ->
            if (userId != "") {
                db.collection(COLLECTION_PRESTITI)
                    .whereEqualTo(DataBaseHelper.COL_PRESTITI_USERID, userId)
                    .whereEqualTo(DataBaseHelper.COL_PRESTITI_ITEMID, itemId)
                    .get()
                    .addOnSuccessListener { userDocuments ->
                        if (!userDocuments.isEmpty) {
                            Toast.makeText(
                                context,
                                "L'oggetto è già presente nella sezione 'i miei prestiti'",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            db.collection(COLLECTION_PRESTITI)
                                .whereEqualTo(DataBaseHelper.COL_PRESTITI_ITEMID, itemId)
                                .get()
                                .addOnSuccessListener { itemDocuments ->
                                    if (!itemDocuments.isEmpty) {
                                        val prestitoDoc = itemDocuments.documents[0]
                                        val dataRestituzione =
                                            prestitoDoc.getString(DataBaseHelper.COL_PRESTITI_DATARESTITUZIONE)
                                                ?: ""
                                        Toast.makeText(
                                            context,
                                            "L'oggetto sarà disponibile per il prestito dopo il $dataRestituzione",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        val currentDate = getCurrentDate()
                                        val returnDate = getReturnDate(currentDate)
                                        val prestito = hashMapOf(
                                            DataBaseHelper.COL_PRESTITI_USERID to userId,
                                            DataBaseHelper.COL_PRESTITI_ITEMID to itemId,
                                            DataBaseHelper.COL_PRESTITI_DATANOLEGGIO to currentDate,
                                            DataBaseHelper.COL_PRESTITI_DATARESTITUZIONE to returnDate
                                        )
                                        db.collection(COLLECTION_PRESTITI)
                                            .add(prestito)
                                            .addOnSuccessListener {
                                                Toast.makeText(
                                                    context,
                                                    "Prestito effettuato con successo",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            .addOnFailureListener { exception ->
                                                Toast.makeText(
                                                    context,
                                                    "Errore durante l'aggiunta del prestito: ${exception.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(
                                        context,
                                        "Errore durante il controllo del prestito: ${exception.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(
                            context,
                            "Errore durante il controllo del prestito: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(context, "Utente non trovato", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserEmail(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", "") ?: ""
    }

    private fun getUserIdFromEmail(
        context: Context,
        userEmail: String,
        callback: (String) -> Unit
    ) {
        db.collection("Users")
            .whereEqualTo("Email", userEmail)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userId = documents.documents[0].id
                    callback(userId)
                } else {
                    callback("")
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Errore: ${exception.message}", Toast.LENGTH_SHORT).show()
                callback("")
            }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getReturnDate(currentDate: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = sdf.parse(currentDate)!!
        calendar.add(Calendar.DAY_OF_MONTH, 30)
        return sdf.format(calendar.time)
    }
}
