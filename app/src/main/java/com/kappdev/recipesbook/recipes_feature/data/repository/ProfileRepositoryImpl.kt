package com.kappdev.recipesbook.recipes_feature.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.storage.FirebaseStorage
import com.kappdev.recipesbook.core.data.common.Firestore
import com.kappdev.recipesbook.core.data.common.Storage
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.recipes_feature.domain.model.User
import com.kappdev.recipesbook.recipes_feature.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    override suspend fun getUser(): Flow<User> {
        val users = firestore.collection(Firestore.Collection.USERS)
        return users.document(auth.uid!!).snapshots().map { snapshot ->
            snapshot.parseUser()
        }
    }

    override suspend fun updateName(name: String): ResultState<Unit> {
        return updateUserData(Firestore.Field.USER_NAME, name)
    }

    override suspend fun updateProfileImage(imageUri: Uri): ResultState<Unit> {
        val imageUrl = imageUri.uploadToStorage()
        return updateUserData(Firestore.Field.USER_PROFILE_IMAGE, imageUrl)
    }

    override fun logout() {
        auth.signOut()
    }

    private suspend fun updateUserData(field: String, value: Any?): ResultState<Unit> {
        val users = firestore.collection(Firestore.Collection.USERS)
        return try {
            users.document(auth.currentUser!!.uid).update(field, value).await()
            ResultState.Success(Unit)
        } catch (e: Exception) {
            ResultState.Failure(e)
        }
    }

    private suspend fun Uri.uploadToStorage(): String {
        val reference = storage.reference.child(Storage.Folder.PROFILE_PICTURES).child(auth.currentUser!!.uid)
        reference.putFile(this).await()
        return reference.downloadUrl.await().toString()
    }

    private fun DocumentSnapshot.parseUser(): User {
        return User(
            name = this.getString(Firestore.Field.USER_NAME) ?: "",
            email = this.getString(Firestore.Field.USER_EMAIL) ?: "",
            profileImage = this.getString(Firestore.Field.USER_PROFILE_IMAGE) ?: "",
        )
    }
}