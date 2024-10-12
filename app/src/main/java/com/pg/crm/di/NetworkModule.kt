package com.pg.crm.di

import com.pg.crm.api.ApiService
import com.pg.crm.utils.Constants.Companion.AUTHORIZATION
import com.pg.crm.utils.Constants.Companion.AUTH_TOKEN
import com.pg.crm.utils.Constants.Companion.BASE_URL
import com.pg.crm.utils.Constants.Companion.CONTENT_TYPE
import com.pg.crm.utils.Prefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder().addInterceptor(provideLoggingInterceptor()).addInterceptor(provideHeaders())
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideHeaders():Interceptor{
        return Interceptor {
            val requestBuilder=it.request().newBuilder()
            requestBuilder.addHeader(AUTHORIZATION,Prefs.getString(AUTH_TOKEN))
            requestBuilder.addHeader(CONTENT_TYPE,"application/json")
            it.proceed(requestBuilder.build())
        }

    }
    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

}