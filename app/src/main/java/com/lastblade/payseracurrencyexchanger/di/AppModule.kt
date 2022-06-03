package com.lastblade.payseracurrencyexchanger.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.lastblade.payseracurrencyexchanger.data.db.RoomHelper
import com.lastblade.payseracurrencyexchanger.data.network.ApiEndPoint
import com.lastblade.payseracurrencyexchanger.data.network.ApiHelper
import com.lastblade.payseracurrencyexchanger.data.network.IApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    open var baseUrl = ApiEndPoint.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "")

                chain.proceed(request.build())
            }.addInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): IApiService {
        return retrofit.create(IApiService::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteApiHelper

    @Provides
    @Singleton
    @RemoteApiHelper
    fun provideApiHelper(apiService: IApiService): ApiHelper {
        return ApiHelper(apiService)
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalRoomHelper


    @Provides
    @Singleton
    @LocalRoomHelper
    fun provideRoomHelper(@ApplicationContext context: Context): RoomHelper {
        return RoomHelper(context)
    }

}