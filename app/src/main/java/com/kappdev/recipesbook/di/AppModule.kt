package com.kappdev.recipesbook.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kappdev.recipesbook.auth_feature.data.repository.AuthRepositoryImpl
import com.kappdev.recipesbook.auth_feature.domain.repository.AuthRepository
import com.kappdev.recipesbook.recipes_feature.data.repository.ProfileRepositoryImpl
import com.kappdev.recipesbook.recipes_feature.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        auth: FirebaseAuth,
        storage: FirebaseStorage,
        firestore: FirebaseFirestore,
    ): AuthRepository {
        return AuthRepositoryImpl(context = context, auth = auth, storage = storage, firestore = firestore)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        auth: FirebaseAuth,
        storage: FirebaseStorage,
        firestore: FirebaseFirestore,
    ): ProfileRepository {
        return ProfileRepositoryImpl(auth = auth, storage = storage, firestore = firestore)
    }
}
