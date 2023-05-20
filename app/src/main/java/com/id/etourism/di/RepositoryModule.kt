package com.id.etourism.di

import com.google.firebase.firestore.FirebaseFirestore
import com.id.etourism.data.network.repository.wisata.WisataRepository
import com.id.etourism.data.network.repository.wisata.WisataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        database: FirebaseFirestore
    ): WisataRepository {
        return WisataRepositoryImpl(database)
    }
}