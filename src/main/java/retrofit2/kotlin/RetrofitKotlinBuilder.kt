package retrofit2.kotlin

import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import java.util.concurrent.Executor

data class RetrofitKotlinBuilder(var baseUrl: String? = null,
                                 var client: OkHttpClient = OkHttpClient(),
                                 var converterFactories: MutableList<Converter.Factory> = mutableListOf(),
                                 var callAdapterFactories: MutableList<CallAdapter.Factory> = mutableListOf(),
                                 var callbackExecutor: Executor? = null,
                                 var validateEagerly: Boolean? = false)