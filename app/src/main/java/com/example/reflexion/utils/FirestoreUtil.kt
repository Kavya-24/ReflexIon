package com.example.reflexion.utils

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


    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        //We want to get the current user document refernece
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                //If reference does not exists create a new user
                val newUser =
                    User(FirebaseAuth.getInstance().currentUser?.displayName ?: "", null)

                //Contain the document in firestore
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else {
                onComplete()
            }
        }
    }

}