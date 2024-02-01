package com.eunji.lookatthis.presentation.di

import com.eunji.lookatthis.BuildConfig
import com.eunji.lookatthis.data.retrofit.ApiClient
import com.eunji.lookatthis.data.retrofit.services.UserService
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
    fun provideUserService(apiClient: ApiClient): UserService =
        apiClient.getRetrofitBuilder(BuildConfig.baseUrl, needAuthorization = false)
            .create(UserService::class.java)

}