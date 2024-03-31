package com.example.rikeandmortyapp.di

import android.widget.Toast
import com.example.rikeandmortyapp.data.CartoonApiService
import com.example.rikeandmortyapp.data.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = NetworkService.createRetrofit(okHttpClient)


    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = NetworkService.createOkHttpClient(interceptor)


    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor = NetworkService.createLoggingInterceptor()

    @Provides
    fun provideCartoonApiService(retrofit: Retrofit): CartoonApiService = retrofit.create(CartoonApiService::class.java)

}