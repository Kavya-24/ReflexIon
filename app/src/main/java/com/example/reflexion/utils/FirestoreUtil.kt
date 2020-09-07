package com.example.reflexion.utils

import android.util.Log
import com.example.reflexion.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUtil {

    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document(
            "users/${FirebaseAuth.getInstance().currentUser?.uid ?: throw NullPointerException(
                "UID Not found"
            )}"
        )

    private val TAG = FirestoreUtil::class.java.simpleName
    //Collection for all the notes in here
    //private  val myTasksCollectionReference = firestoreInstance.collection("myTasks")


    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        //We want to get the current user document refernece

        Log.e(TAG,"In init current user")

        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                //If reference does not exists create a new user
                val newUser =
                    User(FirebaseAuth.getInstance().currentUser?.displayName ?: "", null)

                Log.e(TAG,"In current doc ref wuth user name as " + newUser.name)

                //Contain the document in firestore
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else {
                onComplete()
            }
        }

    }

    fun getCurrentUser(onComplete: (User) -> Unit) {

        currentUserDocRef.get().addOnSuccessListener {
            it.toObject(User::class.java)?.let { it1 -> onComplete(it1) }
        }
    }

}