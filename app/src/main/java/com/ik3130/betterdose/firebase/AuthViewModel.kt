package com.ik3130.betterdose.firebase

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ik3130.apihomework.factory.RetrofitFactory
import com.ik3130.betterdose.data.models.Diary
import com.ik3130.betterdose.data.models.Medication
import com.ik3130.betterdose.data.models.Report
import com.ik3130.betterdose.data.models.User
import com.ik3130.betterdose.repos.OpenFDAApi
import com.ik3130.betterdose.ui.screens.NavGraphs
import com.ik3130.betterdose.ui.screens.destinations.Destination
import com.ik3130.betterdose.ui.screens.destinations.DiaryScreenDestination
import com.ik3130.betterdose.ui.screens.startAppDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.streams.toList

class AuthViewModel(val auth: FirebaseAuth) : ViewModel() {
    private val _userLoginStatus = MutableStateFlow<UserLoginStatus?>(null)
    var userLoginStatus = _userLoginStatus.asStateFlow()
    private val _userRegisterStatus = MutableStateFlow<UserRegisterStatus?>(null)
    var userRegisterStatus = _userRegisterStatus.asStateFlow()

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val openFdaApi = RetrofitFactory.getInstance().create(OpenFDAApi::class.java)

    var currentColorMode by mutableStateOf(false)
    var currentUser by mutableStateOf<FirebaseUser?>(null)
        private set
    var firestoreUser by mutableStateOf<User?>(null)
    var moodsList by mutableStateOf(emptyList<Report>())
    var diaryList by mutableStateOf(emptyList<Diary>())
    var medication by mutableStateOf<Medication?>(null)
    var loading by mutableStateOf(false)
    var currentDestination by mutableStateOf(NavGraphs.app.startAppDestination)
        private set

    init {
        auth.addAuthStateListener { auth ->
            Log.i("CURRENT USER", auth.currentUser.toString())
            setCurrentUser(auth.currentUser)
        }
    }

    @JvmName("assignCurrentUser")
    fun setCurrentUser(user: FirebaseUser?) {
        Log.i("USER UPDATE", currentUser.toString())
        currentUser = user
        if (user != null) this.fetchAndSetUser(user.uid)
    }

    @JvmName("assignCurrentDestination")
    fun setCurrentDestination(destination: Destination) {
        currentDestination = destination
    }

    fun executeMedSearch(medName: String) {
        viewModelScope.launch {
            val query = "openfda.brand_name:${medName.trim()}"
            val result = openFdaApi.getDrugByName(query)
            if (result.isSuccessful && result.body() != null && result.body()!!.results.isNotEmpty()) {
                medication = result.body()!!.results[0]
            }
        }
    }

    fun createDiaryEntry(request: Diary) {
        db.collection("Diary").document(request.id).set(request).addOnSuccessListener {
            medication = null
            //navController.navigate(DiaryScreenDestination.invoke())
            setCurrentDestination(DiaryScreenDestination)
        }
    }

    fun executeLogin(email: String, password: String) {
        FirebaseAuthService.login(email, password, firebaseAuth, onSuccess = {
            _userLoginStatus.value = UserLoginStatus.Successful; Log.i(
            "LOGIN SUCCESS", _userLoginStatus.value.toString()
        )
            setCurrentUser(auth.currentUser)
        }, onFailure = {
            _userLoginStatus.value = UserLoginStatus.Failure(it); Log.i(
            "LOGIN FAIL", _userLoginStatus.value.toString()
        )
        })
    }

    fun executeSignOut() {
        FirebaseAuthService.logout(firebaseAuth)
        _userLoginStatus.value = null
        currentUser = null
    }

    fun executeRegister(email: String, password: String, fullName: String) {
        FirebaseAuthService.register(email, password, firebaseAuth, onSuccess = {
            _userRegisterStatus.value = UserRegisterStatus.Successful; Log.i(
            "REGISTER SUCCESS", _userRegisterStatus.value.toString()
        )
            val newUser = currentUser?.let { User(it.uid, it.email, fullName) }
            if (newUser != null) {
                createUser(newUser)
            }

        }, onFailure = {
            _userRegisterStatus.value = UserRegisterStatus.Failure(it); Log.i(
            "LOGIN FAIL", _userRegisterStatus.value.toString()
        )
        })
    }

    private fun createUser(user: User) {
        db.collection("Users").document(user.id).set(user)
            .addOnSuccessListener { firestoreUser = user }
    }

    fun updateName(name: String) {
        val ref = this.db.collection("Users").whereEqualTo("id", currentUser?.uid ?: "Failed")

        ref.get().addOnSuccessListener { it ->
            it.documents.forEach {
                this.db.collection("Users").document(it.id).set(
                    mapOf("fullName" to name), SetOptions.merge()
                )
                this.fetchAndSetUser(currentUser?.uid ?: "Failed")
            }
        }
    }

    private fun fetchAndSetUser(uid: String) {
        val docRef = db.collection("Users").document(uid)
        docRef.get().addOnSuccessListener {
            this.firestoreUser = it.toObject<User>()
        }
    }

    @JvmName("assignCurrentColorMode")
    fun setCurrentColorMode(color: Boolean) {
        currentColorMode = color
    }

    fun lightOrDarkMode(): Boolean {
        return if (!currentColorMode) false
        else currentColorMode
    }

    fun isUserLoggedIn(): Boolean {
        return this.currentUser != null
    }


    fun fetchReports() {
        viewModelScope.launch {
            db.collection("Report").whereEqualTo("userId", currentUser?.uid ?: "Failed").get()
                .addOnSuccessListener {
                    moodsList = it.toObjects(Report::class.java).stream()
                        .sorted { o1, o2 -> o2.reportedAt.compareTo(o1.reportedAt) }.toList()
                }
        }

    }

    fun fetchDiaries() {
        viewModelScope.launch {
            db.collection("Diary").whereEqualTo("userId", currentUser?.uid ?: "Failed").get()
                .addOnSuccessListener {
                    diaryList = it.toObjects(Diary::class.java).stream()
                        .sorted { o1, o2 -> o2.takeAt.compareTo(o1.takeAt) }.toList()
                }
        }

    }

    fun deleteDoc(collection: String, docId: String) {
        viewModelScope.launch {
            db.collection(collection).document(docId).delete()
            // navController.navigate(DiaryScreenDestination.invoke())
            when (collection) {
                "Report" -> fetchReports()
                "Diary" -> fetchDiaries()
                else -> currentUser = null
            }
        }
    }

    fun createReport(request: Report) {
        viewModelScope.launch {
            db.collection("Report").document(request.id).set(request).addOnSuccessListener {
                fetchReports()
            }
        }
    }

    fun deleteAccount(password: String): Boolean {
        var success = false
        currentUser?.let { user ->
            val credential = EmailAuthProvider.getCredential(user.email!!, password)
            user.reauthenticate(credential).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "User re-authenticated.")
                    user.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User account deleted.")
                            success = true
                        }
                    }
                }
            }.addOnFailureListener {
                Log.d(TAG, "Failed to re-authenticate user.")
                success = false
            }
        }
        return success
    }
}

sealed class UserLoginStatus {
    object Successful : UserLoginStatus()
    class Failure(val exception: Exception?) : UserLoginStatus()
}

sealed class UserRegisterStatus {
    object Successful : UserRegisterStatus()
    class Failure(val exception: Exception?) : UserRegisterStatus()
}