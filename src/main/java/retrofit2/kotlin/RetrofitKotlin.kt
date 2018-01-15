package retrofit2.kotlin

import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

fun retrofit(init: RetrofitKotlinBuilder.() -> Unit) =
        RetrofitKotlinBuilder().apply(init).run {
            Retrofit.Builder().apply {
                baseUrl?.let { baseUrl(it) } ?: throw IllegalArgumentException("baseUrl == null")
                client(client)
                callbackExecutor?.let { callbackExecutor(it) }
                addConverterFactories(converterFactories)
                addCallAdapterFactories(callAdapterFactories)
                validateEagerly?.let { validateEagerly(it) }
            }.build()
        }

fun Retrofit.Builder.addConverterFactories(converterFactories: List<Converter.Factory>){
    converterFactories.forEach { addConverterFactory(it) }
}

fun Retrofit.Builder.addCallAdapterFactories(callAdapterFactories: MutableList<CallAdapter.Factory>) {
    callAdapterFactories.forEach { addCallAdapterFactory(it) }
}

inline fun <reified T> Retrofit.createService(): T =
        create(T::class.java)

inline fun <reified T> createRetrofitService(init: RetrofitKotlinBuilder.() -> Unit): T =
        RetrofitKotlinBuilder().apply(init).run {
            Retrofit.Builder().apply {
                baseUrl?.let { baseUrl(it) } ?: throw IllegalArgumentException("baseUrl == null")
                client(client)
                callbackExecutor?.let { callbackExecutor(it) }
                addConverterFactories(converterFactories)
                addCallAdapterFactories(callAdapterFactories)
                validateEagerly?.let { validateEagerly(it) }
            }.build()
        }.createService()
