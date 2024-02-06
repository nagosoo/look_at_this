package com.eunji.lookatthis.presentation.di

import com.eunji.lookatthis.data.repository.AlarmRepository
import com.eunji.lookatthis.data.repository.UserRepository
import com.eunji.lookatthis.domain.repositoryImpl.AlarmRepositoryImpl
import com.eunji.lookatthis.domain.repositoryImpl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository

    @Singleton
    @Binds
    abstract fun bindAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl) : AlarmRepository

}