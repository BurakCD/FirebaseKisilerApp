package com.example.kisileruygulamasi.di

import com.example.kisileruygulamasi.data.datasource.KisilerDataSource
import com.example.kisileruygulamasi.data.repo.KisilerRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule
{
    @Provides
    @Singleton
    fun provideKisilerRepository(kds:KisilerDataSource) : KisilerRepository{

        return KisilerRepository(kds)
    }

    @Provides
    @Singleton
    fun provideKisilerDataSource(refKisiler : DatabaseReference) : KisilerDataSource{
        return KisilerDataSource(refKisiler)
    }

    @Provides
    @Singleton
    fun provideKisilerDatabaseRef() : DatabaseReference{
        val db = FirebaseDatabase.getInstance()
        return db.getReference("kisiler")
    }
}