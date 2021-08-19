package com.eharoldreyes.github.di

import com.eharoldreyes.github.data.GithubService
import com.eharoldreyes.github.data.repository.GitHubRepositoryImpl
import com.eharoldreyes.github.data.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.internal.addHeaderLenient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun providesOkhttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor { chain ->
            val request = chain.request().newBuilder().apply {
                addHeader("Accept", "application/vnd.github.v3+json")
            }.build()
            return@addInterceptor chain.proceed(request)
        }.build()

    @Provides
    @Singleton
    fun provideForecastApi(retrofit: Retrofit): GithubService =
        retrofit.create(GithubService::class.java)

    @Provides
    @Singleton
    fun provideGithubRepository(service: GithubService): GithubRepository =
        GitHubRepositoryImpl(service)
}