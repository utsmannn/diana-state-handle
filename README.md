<h1 align="center">
  Diana
</h1>

<p align="center">
  <img src="https://i.ibb.co/0sgvNdB/carbon-1.png"/>
</p>

<p align="center">
  <a href="https://jitpack.io/#utsmannn/diana-state-handle"><img alt="Version" src="https://img.shields.io/jitpack/version/com.github.utsmannn/diana-state-handle"></a>
  <a href="LICENSE"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"></a>
  <a href="https://github.com/utsmannn/diana-state-handle/pulls"><img alt="Pull request" src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat"></a>
  <a href="https://twitter.com/utsmannn"><img alt="Twitter" src="https://img.shields.io/twitter/follow/utsmannn"></a>
  <a href="https://github.com/utsmannn"><img alt="Github" src="https://img.shields.io/github/followers/utsmannn?label=follow&style=social"></a>
  <h3 align="center">Event handler, designed for retrofit and coroutine</h3>
</p>

---

# Download
Add jitpack repository in settings.gradle
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
}
```

Add dependencies
```groovy
    // core of event state
    implementation 'com.github.utsmannn.diana-state-handle:core:1.0.3'

    // adapter for retrofit
    implementation 'com.github.utsmannn.diana-state-handle:adapter:1.0.3'

    // susbcriber interface (optional)
    implementation 'com.github.utsmannn.diana-state-handle:subscriber:1.0.3'
```

# Retrofit Adapter
For the use adapter, return parameter must be `StateEventResponse` and set adapter to `StateEventAdapterFactory.create()`

```kotlin
interface WebServices {

    @GET(EndPoint.GET_USER)
    suspend fun getList(): StateEventResponse<UserResponses>
    
    companion object {
        fun build(): WebServices {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(StateEventAdapterFactory.create()) // use this adapter
                .client(okHttpClient)
                .build()
                .create(WebServices::class.java)
        }
    }
}
```

# Handler
```kotlin
class UseCase(val webServices: WebServices) {
    suspend fun getList() {
        when (val state = webServices.getList().eventFlow()) {
            is StateEvent.Idle -> { RenderIdle() }
            is StateEvent.Loading -> { RenderLoading() }
            is StateEvent.Failure -> { RenderFailure(state.exception) }
            is StateEvent.Success -> { RenderSuccess(state.data) }
        }
    }
}
```

# Sample
Check sample folder for advanced used

---