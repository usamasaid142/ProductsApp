package com.osama.productsapp.di


import com.osama.productsapp.BuildConfig
import com.osama.productsapp.common.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private val logging: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    private fun getOkHttpClient(): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {

            okHttpClientBuilder
                .addInterceptor(logging)

        }

        okHttpClientBuilder.addInterceptor { chain ->

            try {
                val originalRequest = chain.request()
                val requestBuilder =
                    originalRequest.newBuilder()
                chain.proceed(requestBuilder.build())
            } catch (exception: SocketTimeoutException) {
                exception.printStackTrace()
                chain.proceed(chain.request())
            }

        }

        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideRetrofitInstance(
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
            .create(ApiService::class.java)
    }

}