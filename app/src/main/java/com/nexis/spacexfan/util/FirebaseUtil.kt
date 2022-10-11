package com.nexis.spacexfan.util

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nexis.spacexfan.model.Favorite

@SuppressLint("StaticFieldLeak")
object FirebaseUtil {
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val mFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var fUser: FirebaseUser? = null
    lateinit var mDocRef: DocumentReference
    lateinit var mQuery: Query

    fun checkFavorite(userId: String, rocketId: String, checkFavoriteListener: (isFavorite: Boolean, onError: String?) -> Unit){
        mDocRef = mFireStore.collection("Users").document(userId)
            .collection("Favorites").document(rocketId)

        mDocRef.addSnapshotListener { value, error ->
            if (error != null){
                checkFavoriteListener(false, error.message)
                return@addSnapshotListener
            }

            if (value != null){
                if (value.exists())
                    checkFavoriteListener(true, null)
                else
                    checkFavoriteListener(false, null)
            } else
                checkFavoriteListener(false, null)
        }
    }

    fun addFavorite(userId: String, favorite: Favorite, addFavoriteOnComplete: (onMessage: String?) -> Unit){
        mFireStore.collection("Users").document(userId)
            .collection("Favorites").document(favorite.rocketId)
            .set(favorite)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    addFavoriteOnComplete("Successfully added to favorites")
                else
                    addFavoriteOnComplete(it.exception?.message)
            }
    }

    fun removeFavorite(userId: String, rocketId: String, removeFavoriteOnComplete: (onMessage: String?) -> Unit){
        mFireStore.collection("Users").document(userId)
            .collection("Favorites").document(rocketId)
            .delete()
            .addOnCompleteListener {
                if (it.isSuccessful)
                    removeFavoriteOnComplete("Successfully removed from favorites")
                else
                    removeFavoriteOnComplete(it.exception?.message)
            }
    }
}