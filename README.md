# Android-BoilerPlate

Add it in your root build.gradle at the end of repositories:

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}	
```

Add the dependency

```
dependencies {
    implementation 'com.github.sarkar-jayanta:android-boilerplate:1.0.0'
}
```

## Features

**Network Connection Interceptor**

android-boilerplate includes a network connection interceptor that checks for network availability before
making API calls. This helps ensure that your application handles network-related issues gracefully.

**Authentication Interceptor**

The library also provides an authentication interceptor that manages token-based authentication
seamlessly. It automatically attaches the necessary authentication tokens to your requests.

**Base Adapter**

android-boilerplate provides a base adapter to simplify the implementation of RecyclerViews. This base
adapter offers common functionalities and can be easily extended for custom use cases.

**BaseActivity / BaseFragment**

The `BaseActivity / BaseFragment` class provides essential functionalities for managing fragment
lifecycle and UI interactions, including:

- `Loader Management`: Easily show and hide loading indicators during data operations.
- `Error Handling`: Display error messages through Snackbars and dialog pop-ups.
- `Message and Success Dialogs`: Show informative and success messages to users with customizable
  dialogs.

#### Key Features

- **Swipe-to-Refresh**:
    - Integrates swipe-to-refresh functionality out of the box. Simply implement a listener to
      refresh your data.
    - Automatically manages the scrolling behavior when the RecyclerView is present within the
      swipe-to-refresh view.

- **Abstract Methods**:
    - `getLayoutId()`: Define the layout resource ID for the fragment.
    - `createViewModel()`: Create and return the ViewModel instance associated with the fragment.

**BaseViewModel**

The `BaseViewModel` class provides essential functionality for managing application-wide states and
interactions. It includes dependency injection for application context, shared preferences, and
resource provider, along with various LiveData variables for managing UI states.

#### Key Features

- **Injected Dependencies**:
    - `Application Context`: For accessing application-level resources.
    - `Preferences`: To manage user preferences easily.
    - `Resource Provider`: To facilitate string and resource retrieval.

- **LiveData Variables**:
    - **MutableLiveData**:
        - `loading`: Indicates loading states.
        - `error`: Captures error messages or states.
        - `success`: Indicates success messages or states.
        - `isDataEmpty`: Boolean indicating if the data set is empty.
    - **SingleLiveEvent**:
        - Used for one-time events such as showing errors or success messages.

- **Pagination Management**:
    - `perPage`: Number of items per page.
    - `page`: Current page number.

- **Search Management**:
    - `searchValue`: Current search query.
    - `searchJob`: Job used to debounce search actions.

- **Utility Methods**
    - `checkResponse`: Validates the API response and manages success/error states.
    - `manageErrorAndSessionOut`: Manages errors and session timeouts based on the API response.
    - `loading`: Manages loading states, especially useful for pagination.
    - `clearList`: Clears the current data list when starting pagination anew.
    - `managePagination`: Manages pagination logic by checking if more data needs to be loaded.
    - `manageSearch`: Handles search logic and debounces search input.

**PreferenceManager**

android-boilerplate includes a `PreferenceManager` class to simplify the management of shared preferences in
your application. This class implements the singleton pattern, ensuring that only one instance is
used throughout the app, promoting efficient memory usage and better organization. It uses
encryption to secure sensitive data. All preferences are stored in an encrypted format, ensuring
that data is protected against unauthorized access.

#### Features

- **Singleton Instance**: Access a single instance of `PreferenceManager` from anywhere in your app
  using `PreferenceManager.getInstance(context)`.

- **Comprehensive Data Storage**: Manage a variety of data types:
    - String
    - Int
    - Boolean
    - Long
    - Float
    - JSON (as String)
    - Custom Models (convert to/from JSON)
    - Arrays (as JSON)
- `Clear Preferences`: Optionally clear all stored preferences with a single method call

**Network Module**

This is a simple Kotlin network module designed for Android applications. It provides a singleton
instance of `OkHttpClient`, `Retrofit`, and `HttpLoggingInterceptor`, making it easy to manage
network requests in a clean and efficient manner.

#### Features

- `Singleton Pattern`: Ensures only one instance of each component is created.
- `Lazy Initialization`: Components are initialized only when accessed, optimizing performance.
- `HTTP Logging`: Provides detailed logs of HTTP requests and responses for easier debugging.

**ResourcesProvider**

This project includes a `ResourcesProvider` that facilitates access to string resources in Android
ViewModels. By decoupling resource retrieval from the Android framework, this pattern enhances
testability and maintainability in your applications.

#### Features

- `Decoupled Resource Access`: Provides a way to access string resources without relying directly on
  Context in your ViewModels.
- `Testability`: Makes it easier to test ViewModels by allowing you to mock resource retrieval.
- `Flexible Implementation`: Supports multiple implementations of resource providers if needed.

**API Response and Error Handling**

This project provides a standardized way to handle API responses and common errors in an Android
application. By using a base model for responses and a centralized error handling mechanism, you can
improve the maintainability and reliability of your app.

#### Features

- `Standardized API Response Structure`: A base model that standardizes how API responses are
  handled.
- `Centralized Error Management`: A common mechanism to handle different types of errors from API
  calls.

**Android Extensions and Utilities**

This project provides a set of Kotlin extensions and utility functions designed to simplify common
tasks in Android development. These extensions enhance the functionality
of `Activity`, `Fragment`, `Date`, `String`, `View`, and include various utility functions for
managing network checks, file operations, and data conversions.

#### Features

- `Activity Extensions`: Simplify keyboard management and navigation between fragments.
- `Fragment Extensions`: Easily navigate and replace fragments in an Activity.
- `Date Extensions`: Format dates to specified string patterns.
- `Permission Extensions`: Check for permissions easily.
- `String Extensions`: Convert JSON strings to models and lists, and vice versa.
- `View Extensions`: Show and hide views with ease.
- `Utility Functions`: Include various helper functions for network checks, file operations, and
  data conversions.

**EventChannel for Kotlin Coroutines**

This project includes an `EventChannel` implementation that facilitates event-driven programming
using Kotlin coroutines and flows. It allows you to send and receive events in a lifecycle-aware
manner, making it suitable for Android applications.

#### Features

- `Lifecycle Awareness`: Automatically starts and stops collecting events based on the lifecycle of
  the LifecycleOwner.
- `Channel-Based Communication`: Uses Kotlin coroutines and channels for efficient event handling.
- `Flow Integration`: Utilizes Kotlin Flows to handle event streams easily.

**SingleLiveEvent**

The `SingleLiveEvent` class is a custom implementation of `MutableLiveData` designed to handle
events that are meant to be consumed only once. It is particularly useful in Android applications
for scenarios such as navigation or showing snackbars/toasts, where you want to ensure that an event
is not re-emitted when the UI is recreated.

#### Features

- `Single Consumption`: Ensures that an event is consumed only once by observers.
- `Lifecycle-Aware`: Integrates seamlessly with Android's lifecycle components.
- `Thread-Safe`: Uses AtomicBoolean to manage the event state safely across threads.
