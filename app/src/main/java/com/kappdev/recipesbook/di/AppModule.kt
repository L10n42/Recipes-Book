package com.kappdev.recipesbook.di

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kappdev.recipesbook.auth_feature.data.repository.AuthRepositoryImpl
import com.kappdev.recipesbook.auth_feature.domain.repository.AuthRepository
import com.kappdev.recipesbook.categories_feature.data.repository.CategoriesRepositoryImpl
import com.kappdev.recipesbook.categories_feature.domain.repository.CategoriesRepository
import com.kappdev.recipesbook.categories_feature.domain.use_case.GetCategories
import com.kappdev.recipesbook.categories_feature.domain.use_case.InsertCategories
import com.kappdev.recipesbook.recipes_feature.data.repository.ProfileRepositoryImpl
import com.kappdev.recipesbook.recipes_feature.data.repository.RecipeRepositoryImpl
import com.kappdev.recipesbook.recipes_feature.data.repository.StorageRepositoryImpl
import com.kappdev.recipesbook.recipes_feature.domain.repository.ProfileRepository
import com.kappdev.recipesbook.recipes_feature.domain.repository.RecipeRepository
import com.kappdev.recipesbook.recipes_feature.domain.repository.StorageRepository
import com.kappdev.recipesbook.recipes_feature.domain.use_case.DeleteImages
import com.kappdev.recipesbook.recipes_feature.domain.use_case.DeleteRecipe
import com.kappdev.recipesbook.recipes_feature.domain.use_case.GetRecipes
import com.kappdev.recipesbook.recipes_feature.domain.use_case.UploadImages
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
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

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

    @Provides
    @Singleton
    fun provideRecipeRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): RecipeRepository {
        return RecipeRepositoryImpl(auth = auth, firestore = firestore)
    }

    @Provides
    @Singleton
    fun provideCategoriesRepository(auth: FirebaseAuth, firestore: FirebaseFirestore,  app: Application): CategoriesRepository {
        return CategoriesRepositoryImpl(auth = auth, firestore = firestore, app)
    }

    @Provides
    @Singleton
    fun provideStorageRepository(storage: FirebaseStorage,): StorageRepository {
        return StorageRepositoryImpl(storage = storage)
    }

    @Provides
    @Singleton
    fun provideUploadImagesUseCase(repository: StorageRepository): UploadImages = UploadImages(repository)

    @Provides
    @Singleton
    fun provideDeleteImagesUseCase(repository: StorageRepository): DeleteImages = DeleteImages(repository)

    @Provides
    @Singleton
    fun provideDeleteRecipeUseCase(repository: RecipeRepository, deleteImages: DeleteImages): DeleteRecipe {
        return DeleteRecipe(repository, deleteImages)
    }

    @Provides
    @Singleton
    fun provideGetRecipesUseCase(repository: RecipeRepository) = GetRecipes(repository)

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(repository: CategoriesRepository) = GetCategories(repository)

    @Provides
    @Singleton
    fun provideInsertCategoriesUseCase(repository: CategoriesRepository) = InsertCategories(repository)
}
