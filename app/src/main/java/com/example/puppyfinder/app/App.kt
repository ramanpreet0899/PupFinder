package com.example.puppyfinder.app

import android.app.Application
import com.example.puppyfinder.BreedRepository
import com.example.puppyfinder.PupApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesBreedsRepository(pupApi: PupApi): BreedRepository {
        return BreedRepository(pupApi)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideBreedApiService(): PupApi {
        return Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PupApi::class.java)
    }
}

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}