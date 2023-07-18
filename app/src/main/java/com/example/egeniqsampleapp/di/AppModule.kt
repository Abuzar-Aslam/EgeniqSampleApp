package com.example.egeniqsampleapp.di

import android.util.Log
import com.example.egeniqsampleapp.BuildConfig
import com.example.egeniqsampleapp.data.repository.BreedRepository
import com.example.egeniqsampleapp.data.repository.BreedRepositoryImpl
import com.example.egeniqsampleapp.data.source.BreedPagingSource
import com.example.egeniqsampleapp.data.source.DataSource
import com.example.egeniqsampleapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides dependencies for the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides an instance of the OkHttpClient.
     *
     * @param headerInterceptor The [Interceptor] for adding headers to the requests.
     * @param loggingInterceptor The [HttpLoggingInterceptor] for logging network requests.
     * @return An instance of [OkHttpClient].
     */
    @Singleton
    @Provides
    fun provideClient(
        headerInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Provides an instance of the header interceptor.
     *
     * @return An instance of [Interceptor].
     */
    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()

            // Add header to the original request
            val modifiedRequest = originalRequest.newBuilder()
                .addHeader(
                    "x-api-key",
                    BuildConfig.ApiKey
                )
                .build()

            chain.proceed(modifiedRequest)
        }
    }

    /**
     * Provides an instance of the logging interceptor.
     *
     * @return An instance of [HttpLoggingInterceptor].
     */
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.d("API_LOG", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Provides an instance of the Retrofit client.
     *
     * @param client The [OkHttpClient] for making network requests.
     * @return An instance of [Retrofit].
     */
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides an instance of the [DataSource] interface.
     *
     * @param retrofit The [Retrofit] dependency.
     * @return An instance of [DataSource].
     */
    @Singleton
    @Provides
    fun provideDataSource(retrofit: Retrofit): DataSource {
        return retrofit.create(DataSource::class.java)
    }

    /**
     * Provides an instance of the [BreedRepository] interface.
     *
     * @param breedPagingSource The [BreedPagingSource] dependency.
     * @return An instance of [BreedRepository].
     */
    @Singleton
    @Provides
    fun providesBreedRepository(
        breedPagingSource: BreedPagingSource
    ): BreedRepository {
        return BreedRepositoryImpl(breedPagingSource)
    }
}
