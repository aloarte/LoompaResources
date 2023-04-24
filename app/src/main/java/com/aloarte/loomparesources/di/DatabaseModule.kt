package com.aloarte.loomparesources.di

import android.app.Application
import androidx.room.Room
import com.aloarte.loomparesources.data.room.WillyWonkaDao
import com.aloarte.loomparesources.data.room.WillyWonkaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(application: Application): WillyWonkaDatabase {
        return Room.databaseBuilder(application, WillyWonkaDatabase::class.java, "willy_wonka_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(dataBase: WillyWonkaDatabase): WillyWonkaDao {
        return dataBase.willyWonkaDao()
    }

}