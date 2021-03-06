package com.hello.spiralworktask.libs.di.module

import com.google.gson.Gson
import com.hello.spiralworktask.libs.di.scopes.PerApplication
import com.hello.spiralworktask.libs.net.APIService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
class NetworkModule(private val endpoint: String) {

  @PerApplication
  @Provides
  fun provideRestService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)

  @Provides
  fun provideRetrofit(
    gson: Gson,
    callFactory: Call.Factory
  ): Retrofit =
    Retrofit.Builder()
        .callFactory(callFactory)
        .baseUrl(endpoint)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

  @Provides
  fun provideOkHttpCallFactory(): Call.Factory = OkHttpClient
      .Builder()
      .build()

}