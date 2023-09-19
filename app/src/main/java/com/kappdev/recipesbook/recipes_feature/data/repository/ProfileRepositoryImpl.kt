package com.kappdev.recipesbook.recipes_feature.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.kappdev.recipesbook.core.data.common.Firestore
import com.kappdev.recipesbook.recipes_feature.domain.model.User
import com.kappdev.recipesbook.recipes_feature.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    override suspend fun getUser(): Flow<User> {
        val users = firestore.collection(Firestore.Collection.USERS)
        return users.document(auth.uid!!).snapshots().map { snapshot ->
            snapshot.parseUser()
        }
    }

    private fun DocumentSnapshot.parseUser(): User {
        return User(
            name = this.getString(Firestore.Field.USER_NAME) ?: "",
            email = this.getString(Firestore.Field.USER_EMAIL) ?: "",
            profileImage = this.getString(Firestore.Field.USER_PROFILE_IMAGE) ?: "",
        )
    }
}