package cz.dmn.droneworldguide.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cz.dmn.droneworldguide.api.DwgApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    companion object {
        val CONN_TIMEOUT_SECONDS = 20L
        val READ_TIMEOUT_SECONDS = 20L
        val WRITE_TIMEOUT_SECONDS = 20L
        val API_BASE_URL = "http://api.dmn.cz/dwg/"

        private fun provideBaseOkHttpClientBuilder(): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()
                    .connectTimeout(CONN_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            return builder
        }

        private fun buildClient(baseUrl: String, httpClient: OkHttpClient, gson: Gson) = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideDwgApi(client: OkHttpClient, gson: Gson): DwgApi = buildClient(API_BASE_URL, client, gson).create(DwgApi::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient() = provideBaseOkHttpClientBuilder().build()

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()
}
