package com.aloarte.loomparesources.di

import com.aloarte.loomparesources.data.WillyWonkaRepositoryImpl
import com.aloarte.loomparesources.data.datasources.OompaLoompaPagingSource
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasource
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasourceImpl
import com.aloarte.loomparesources.domain.WillyWonkaRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindWillyWonkaDatasource(impl: WillyWonkaDatasourceImpl): WillyWonkaDatasource
    @Binds
    abstract fun bindWillyWonkaRepository(impl: WillyWonkaRepositoryImpl): WillyWonkaRepository
}