import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

class MyApplication : Application() {

 val db = FirebaseFirestore.getInstance()
}
