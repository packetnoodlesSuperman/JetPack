package com.jetpack.xhb.video.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *   Kotlin中object关键字用以修饰类，
 *  在声明类的同时创建一个实例（即该类的一个对象）。可以理解成Java中的静态单例
 *
 *  -用以声明一个单例类
 *  -伴生对象
 *  -对象表达式（Java的匿名内部类）
 */
object RetrofitManager {

    val service: ApiService by lazy (LazyThreadSafetyMode.SYNCHRONIZED) {
        getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("")
                .client(getOkHttpClient())
                .build();
    }

    private fun getOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .build();

}