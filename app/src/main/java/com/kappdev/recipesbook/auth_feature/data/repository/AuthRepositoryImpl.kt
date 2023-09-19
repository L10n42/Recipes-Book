package com.kappdev.recipesbook.auth_feature.data.repository

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.auth_feature.domain.model.UserData
import com.kappdev.recipesbook.auth_feature.domain.repository.AuthRepository
import com.kappdev.recipesbook.auth_feature.domain.util.EmailNotVerifiedException
import com.kappdev.recipesbook.core.data.common.Firestore
import com.kappdev.recipesbook.core.data.common.Storage
import com.kappdev.recipesbook.core.domain.util.ResultState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : AuthRepository {

    override suspend fun signUp(user: UserData): ResultState<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(user.email, user.password).await()
            val imageUrl = user.profile?.uploadToStorage() ?: ""
            createUserProfile(user, imageUrl, result.user!!.uid)

            result.user?.sendEmailVerification()?.await()
            ResultState.Success(Unit)
        } catch (e: Exception) {
            ResultState.Failure(e)
        }
    }

    override suspend fun logIn(email: String, password: String): ResultState<Unit> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.checkEmailVerification()
        } catch (e: Exception) {
            ResultState.Failure(e)
        }
    }

    private suspend fun createUserProfile(user: UserData, imageUrl: String, uid: String) {
        val data = mapOf(
            Firestore.Field.USER_UID to uid,
            Firestore.Field.USER_NAME to user.name,
            Firestore.Field.USER_EMAIL to user.email,
            Firestore.Field.USER_PROFILE_IMAGE to imageUrl
        )
        val users = firestore.collection(Firestore.Collection.USERS)
        users.document(auth.currentUser!!.uid).set(data).await()
    }

    private suspend fun Uri.uploadToStorage(): String {
        val reference = storage.reference.child(Storage.Folder.PROFILE_PICTURES).child(auth.currentUser!!.uid)
        reference.putFile(this).await()
        return reference.downloadUrl.await().toString()
    }

    private fun AuthResult.checkEmailVerification(): ResultState.Success<Unit> {
        return if (this@checkEmailVerification.user?.isEmailVerified == true) {
            ResultState.Success(Unit)
        } else {
            throw EmailNotVerifiedException(context.getString(R.string.email_not_verified_msg))
        }
    }
}