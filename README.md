# Retrofit Kotlin
A Kotlin DSL Language for `Retrofit 2`

## Usage
- Building Retrofit Instance
```kotlin
        val retrofit = retrofit {
            baseUrl = url
            this.client = client
            converterFactories = arrayListOf(GsonConverterFactory.create(gson))
            callAdapterFactories = arrayListOf(RxJava2CallAdapterFactory.create())
        }
```

- Creating `Retrofit` Service : 
```kotlin
    interface Service {
        @GET("/")
        fun body(): Call<String>

        @GET("/")
        fun response(): Call<Response<String>>
    }    
    
    val service: Service = service = createRetrofitService {
            baseUrl = "<<YOUR_BASE_URL>>"
            client = OkHttpClient()
            converterFactories = arrayListOf(GsonConverterFactory.create(gson))
            callAdapterFactories = arrayListOf(RxJava2CallAdapterFactory.create())
    }
```

OR

You can create `Retrofit` service using existing `Retrofit` Instance :

```kotlin
	val service: Service = retrofit {
            baseUrl = url
            this.client = client
            converterFactories = arrayListOf(GsonConverterFactory.create(gson))
            callAdapterFactories = arrayListOf(RxJava2CallAdapterFactory.create())
        }.createService()
```


## Download

-  Add the following to your project level `build.gradle`:
 
```gradle
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}
```
  -  Add this to your app `build.gradle`:
 
```gradle
dependencies {
	implementation 'com.github.amalhanaja:retrofit2-kotlin:1.0.0'
}
```

License
=======
Copyright 2018 Alfian Akmal Hanantio

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

**Special thanks** to [square](https://github.com/square), [JetBrains](https://github.com/JetBrains) and [jitpack.io](https://github.com/jitpack-io) for their contributions to this project.
