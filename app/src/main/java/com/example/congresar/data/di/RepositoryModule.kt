package com.example.congresar.data.di

import com.example.congresar.data.repository.DiputadoRepositoryImpl
import com.example.congresar.domain.repository.DiputadoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDiputadoRepository(impl: DiputadoRepositoryImpl): DiputadoRepository
}
