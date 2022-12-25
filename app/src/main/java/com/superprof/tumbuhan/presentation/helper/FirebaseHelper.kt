package com.superprof.tumbuhan.presentation.helper

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class FirebaseHelper {
    private lateinit var auth: FirebaseAuth

    private lateinit var db: FirebaseFirestore

    private lateinit var storage: FirebaseStorage

    private lateinit var storageReference: StorageReference

    companion object {
        const val COLLECTION_PLANT = "plants"
    }

    fun initFirebase() {
        auth = Firebase.auth
        db = Firebase.firestore
        storage = Firebase.storage
        storageReference = storage.reference

    }

    fun saveData(
        data: Any,
        collection: String,
        document: String,
        onSuccess: () -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {

        db.collection(collection)
            .document(document)
            .set(data)
            .addOnSuccessListener { documentReference ->
                onSuccess.invoke()
            }
            .addOnFailureListener { e ->
                val errorMessage = e.message ?: "Error Not Found"
                onFailure.invoke(errorMessage)
            }
    }

    fun <T> getDatas(
        collection: String,
        data: Class<T>,
        onSuccess: (data: List<T>) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {

        val docRef = db.collection(collection)
        docRef.get()
            .addOnSuccessListener { queryDocuments ->
                val datas = queryDocuments.toObjects(data)
                if (datas.isNotEmpty()) {
                    onSuccess.invoke(datas)
                } else {
                    onFailure.invoke("Data is Empty")
                }
            }
            .addOnFailureListener { exception ->
                onFailure.invoke(exception.message ?: "error not found")
            }

    }

    fun getData(
        collection: String,
        document: String,
        onSuccess: (data: Map<String, Any>) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {

        val docRef = db.collection(collection).document(document)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    onSuccess.invoke(document.data!!)
                } else {
                    onFailure.invoke("Data Not Found")
                }
            }
            .addOnFailureListener { exception ->
                onFailure.invoke(exception.message ?: "error not found")
            }

    }
}