# Practical Work

The first part of this TP is to display a paginated list containing characters from [Rick and Morty API](https://rickandmortyapi.com/documentation) 

## Data 

This part will define what is the package `data.

The package is _fr.appsolute.tp.data_ and its file architecture is : 

```text
    data
    ├── model
    │   ├── Character.kt
    │   └── PaginatedResult.kt
    ├── networking
    │   ├── HttpClientManager.kt
    │   ├── api
    │   │   └── CharacterApi.kt
    │   └── datasource
    │       └── CharacterDataSource.kt
    └── repository
        └── CharacterRepository.kt
```

### Model 

Package _fr.appsolute.tp.data.model_

There is two classes in this package, `Character` and `PaginatedResult`. 

These class are [data class](https://kotlinlang.org/docs/reference/data-classes.html#data-classes) :
in the constructor, you just have to define what you need, and it will provide 
you setter/getter, override `equals`, `hashCode`, `toString` methods

[Character](https://rickandmortyapi.com/documentation/#character-schema) will 
define the [POJO](https://en.wikipedia.org/wiki/Plain_old_Java_object)
for a character

PaginatedResult is a [generic class](https://kotlinlang.org/docs/reference/generics.html)
to catch result from the API (We use a generic class to reuse this part later)

### Networking

Package _fr.appsolute.tp.data.networking_

In this package, there are only sub package and the `HttpClientManager`
(In fact it's not a good name because it manage `Retrofit` instance)

The Manager will give you an access to a system resource.
In this case it's a web access because Retrofit give you network
access to fetch data from API. 

About the reified function you can learn a lot [here](https://kotlinlang.org/docs/reference/inline-functions.html#reified-type-parameters)
but is a very advanced Kotlin

### API

Package _fr.appsolute.tp.data.networking.api_

This package contains all end point for all api in the project,
here there is just the `CharacterApi`

With [Retrofit](https://square.github.io/retrofit/), an Api is a
list a method with annotations: 

The `suspend` keyword say that function must be call in a
coroutine more [here](https://kotlinlang.org/docs/reference/coroutines-overview.html)

### DataSource

Package `fr.appsolute.tp.data.networking.datasource`

A DataSource is used to load page into a PagedList more information
[here](https://developer.android.com/reference/androidx/paging/DataSource.html)

In this project we used [PageKeyedDataSource](https://developer.android.com/reference/androidx/paging/PageKeyedDataSource.html)

### Repository

Package `fr.appsolute.tp.data.repository`

A repository is an instance which manage a **Model**

So the `CharacterRepository` is used to manage the CharacterModel.
It will provide the "Observable"/model to observe. For now, there is 
just a paged list to observe. 

## UI

This part will define what is the package `ui`.

This Practical work will 
be a Single Activity App, we will use the [Jetpack Navigation Component](https://developer.android.com/guide/navigation)

The package is _fr.appsolute.tp.ui and its file architecture is : 

```text
    ui
    ├── activity
    │   └── MainActivity.kt
    ├── adapter
    │   └── CharacterAdapter.kt
    ├── fragment
    │   └── CharacterListFragment.kt
    ├── viewmodel
    │   └── CharacterViewModel.kt
    └── widget
        └── holder
            └── CharacterViewHolder.kt
```

### Activity

This package contain the MainActivity. 

The layout resource contain a [Toolbar](https://developer.android.com/reference/android/widget/Toolbar)
and a [FragmentContainerView](https://developer.android.com/reference/androidx/fragment/app/FragmentContainerView) (this part will is just a container)

Here we just override the function `onNavigateUp` to use the [NavController](https://developer.android.com/reference/androidx/navigation/NavController)

The Navigation Graph is defined in `res/navigation/main_nav_graph.xml`

### Adapter

The class `CharacterAdapter` is a special `RecyclerView.Adapter`. It is responsible of pagination behavior. 

The class must extend `PagedListAdapter<T, VH>` where `T` is the model to bind, and VH is the `ViewHolder`

It must have a `DiffUtil.ItemCallback<T>` (where `T` is also the model). It define how you can compare two instance a of `T`.

### Fragment

The `CharacterListFragment` is the `startDestination` in the navigation graph. It only contains a `RecyclerView`. We override 3 methods :  

- `onCreate(savedInstanceState: Bundle?)` : Here we asked activity lifecycle to get the ViewModel. If if we asked the fragment 
lifecycle, we can not share the ViewModel between fragments. We provide a `ViewModelProvider.Factory` to define how the view model 
should be created
- `onCreateView(...)`: We just return the result of view inflation
- `override fun onViewCreated(view: View, savedInstanceState: Bundle?)` : This method is call when the view inflation is a success,
so here, we create the adapter, attach it to the `RecyclerView`, and start to observe the model  

This Fragment implement `OnCharacterClickListener` which is not an Interface but a method definition. more details [here](https://kotlinlang.org/docs/reference/type-aliases.html)

### ViewModel

The ViewModel is the part of architecture between the View and the Model. A view can have more than 1 ViewModel.
The rule is "For one Model, you have one ViewModel".

The `CharacterViewModel` will provide an access to the data source. 

**PS** : A LiveData will start when the first `Observer` will be attached. So just the creation will not make an API call.
 
### Holder

Package _fr.appsolute.tp.ui.widget.holder_

The `CharacterViewHolder` is a separate class which extend `RecyclerView.ViewHolder`. We separate the calls from the adapter because maybe we can reuse it later.  

The companion object of the ViewHolder is a Factory the constructor of this class is private.  

## To Do 

- Fork this project.
- Try to fetch the details of character in the next fragment. There is no design guidelines for now but try to follow `Material Design`. You will have to : 
    - [ ] Create the call to the api 
    - [ ] Make the a new method in the API
    - [ ] Create a a method in the repository.
    - [ ] Create the method in the ViewModel
    - [ ] Bind the UI
    - [ ] Make the fragment UI
- **If you have time**, try to make all unit test needed
- **If you have time**, try to build a material theme. All the documentation is available on Material Design (Section **More**) 
## More

- [Rick and Morty API](https://rickandmortyapi.com/documentation)
- [Guide to App Architecture](https://developer.android.com/jetpack/docs/guide)
- [View Model Overview](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Navigation CodeLab](https://codelabs.developers.google.com/codelabs/android-navigation/index.html?index=..%2F..%2Findex#0)
- [The Android Lifecycle cheat sheet](https://medium.com/androiddevelopers/the-android-lifecycle-cheat-sheet-part-i-single-activities-e49fd3d202ab)
- [Material Design](https://material.io)