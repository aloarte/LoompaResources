package com.aloarte.loomparesources.di

import com.aloarte.loomparesources.data.WillyWonkaDatasource
import com.aloarte.loomparesources.data.WillyWonkaDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindSearchDatasource(impl: WillyWonkaDatasourceImpl): WillyWonkaDatasource
}