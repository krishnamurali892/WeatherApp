This is a demo android application which is developed on MVVM Architectural design pattern by using Kotlin programming language.

By referring this application code we can understand most of the latest android application development concepts like

- ViewModel
- DataBinding
- LiveData
- Hilt (for dependency injection)
- Kotlin Coroutines
- Retrofit
- Room
- Workmanager

**MVVM**
 - It is an architectural design pattern, where presentation, business and data layers are loosely coupled.
   So that the application can be easily testable and maintainable(Modifiable)
   
**Fused Location Provider**
 - This is the latest API provided by google play services to get current location details
 
**DataBinding**(no more findViewById's)
 - This library allows us to bind(inject) views into code(XML -> Kotlin) and binding data into views(XML <- Kotlin),
   without having any view's reference explicitly(findViewById's).

**Dagger Hilt**(Annotation based)
 - It's a compile time Android based Dependency Injection framework built on top of dagger2 framework.
 - If we want to make our code loosely coupled then we need to go with Manual Dependency Injection,
   but it adds a lot of extra code and time to develop. 
   
**Kotlin coroutines**
- For making asynchronous calls AsyncTask's are no more supported and they can be replaced with
  kotlin coroutines

**Retrofit**
- REST-Client based web services implementation with Retrofit library.
- It's simple to use and removes a lot of effort for making REST based API calls.
- API calls are asynchronous and it will be taken care of by the library itself.

**Room**
- For local data storage - its a just an abstraction on top of sqlite DB

**Workmanager**
- WorkManager is an API that makes it easy to schedule reliable, asynchronous tasks 
  that are expected to run even if the app in background, exits or the device restarts.


   

