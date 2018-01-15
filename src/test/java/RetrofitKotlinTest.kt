import kotlinx.coroutines.experimental.runBlocking
import okhttp3.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.GET
import java.io.IOException
import retrofit2.Retrofit
import retrofit2.kotlin.createRetrofitService
import java.io.EOFException
import java.lang.reflect.Type


class RetrofitKotlinTest {

    @get:Rule val server = MockWebServer()

    private lateinit var service: Service

    @Before
    fun setUp(){
        service = createRetrofitService {
            baseUrl = server.url("/").toString()
            converterFactories = arrayListOf(StringConverterFactory()) }
    }

    interface Service {
        @GET("/")
        fun body(): Call<String>

        @GET("/")
        fun response(): Call<Response<String>>
    }

    @Test
    fun bodySuccess200(){
        kotlin.run {
            server.enqueue(MockResponse().setBody("Hello Body"))
            val call = service.body()
            assertEquals("Hello Body", call.execute().body())
            assertTrue(call.isExecuted)
        }
    }

    @Test
    fun bodySuccess404(){
        kotlin.run {
            server.enqueue(MockResponse().setResponseCode(404))
            val response = service.body().execute()
            assertFalse(response.isSuccessful)
            assertEquals(404, response.code())
        }
    }

    @Test
    fun bodyFailure(){
        kotlin.run {
            server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST))
            try {
                service.body().execute()
            }catch (e: Exception){
                assertTrue(e.cause is EOFException)
            }
        }
    }

    @Test
    fun responseSuccess200(){
        kotlin.run {
            server.enqueue(MockResponse().setBody("Hello Body"))
            val call = service.body()
            assertEquals("Hello Body", call.execute().body())
            assertTrue(call.isExecuted)
        }
    }

    @Test
    fun responseSuccess404(){
        kotlin.run {
            server.enqueue(MockResponse().setResponseCode(404).setBody("Hello Error Body"))
            val response = service.body().execute()
            assertFalse(response.isSuccessful)
            assertEquals(404, response.code())
            assertEquals("Hello Error Body", response.errorBody()?.string())
        }
    }

    @Test
    fun responseFailure(){
        kotlin.run {
            server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST))
            try {
                service.body().execute()
            }catch (e: Exception){
                assertTrue(e.cause is EOFException)
            }
        }
    }
}

class StringConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *> {
        return object : Converter<ResponseBody, String> {
            @Throws(IOException::class)
            override fun convert(value: ResponseBody): String {
                return value.string()
            }
        }
    }

    override fun requestBodyConverter(type: Type,
                                      parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody> {
        return object : Converter<String, RequestBody> {
            @Throws(IOException::class)
            override fun convert(value: String): RequestBody {
                return RequestBody.create(MediaType.parse("text/plain"), value)
            }
        }
    }
}