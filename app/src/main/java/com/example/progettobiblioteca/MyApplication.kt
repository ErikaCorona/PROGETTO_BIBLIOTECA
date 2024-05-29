
import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore


class MyApplication : Application() {

 override fun onCreate() {
  super.onCreate()

  // Initialize Firebase
  FirebaseApp.initializeApp(this)

  // Enable network and persistence for Firestore
  val firestore = FirebaseFirestore.getInstance()
  firestore.enableNetwork()





 }

}
