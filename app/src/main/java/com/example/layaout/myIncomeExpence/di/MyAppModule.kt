package com.example.layaout.myIncomeExpence.di


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.layaout.myIncomeExpence.ripository.TransactionRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideTransactionRepository(db: FirebaseFirestore): TransactionRepository {
        return TransactionRepository(db)
    }


}
