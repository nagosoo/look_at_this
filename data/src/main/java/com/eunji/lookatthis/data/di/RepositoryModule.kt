package com.eunji.lookatthis.data.di

import com.eunji.lookatthis.domain.repository.AlarmRepository
import com.eunji.lookatthis.domain.repository.LinkRepository
import com.eunji.lookatthis.domain.repository.UserRepository
import com.eunji.lookatthis.data.repositoyImpl.AlarmRepositoryImpl
import com.eunji.lookatthis.data.repositoyImpl.LinkRepositoryImpl
import com.eunji.lookatthis.data.repositoyImpl.UserRepositoryImpl
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
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl): AlarmRepository

    @Singleton
    @Binds
    abstract fun bindLinkRepository(linkRepositoryImpl: LinkRepositoryImpl): LinkRepository

}