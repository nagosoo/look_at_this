package com.eunji.lookatthis.presentation.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.eunji.lookatthis.BuildConfig
import com.eunji.lookatthis.data.retrofit.ApiClient
import com.eunji.lookatthis.data.retrofit.services.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile("appDataStore")
            }
        )

}