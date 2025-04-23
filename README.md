# Android Boilerplate

A robust, production-ready Android boilerplate module designed to accelerate development by providing a scalable architecture, essential utilities, and a collection of battle-tested components.

## Integration

### Step 1: Add JitPack to your Root `build.gradle`
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add the Dependency
```groovy
dependencies {
    implementation 'com.github.sarkar-jayanta:android-boilerplate:1.0.0'
}
```

---

## 📦 Included Components

### ✅ NetworkConnectionInterceptor
Ensures network availability before API calls. Prevents failed requests due to lack of internet connectivity.

### 🔐 AuthenticationInterceptor
Manages secure, token-based authentication seamlessly by automatically appending necessary credentials to each request.

### 🧰 BaseAdapter
Simplifies RecyclerView implementations by providing a reusable, extendable base adapter.

---

## 🧱 BaseActivity & BaseFragment

Provides a foundational architecture for activities and fragments, enabling consistent UI behavior and lifecycle handling.

**Core Features:**
- Loading indicators
- Standardized error handling (Snackbar/Dialog)
- Customizable success/info dialogs
- Swipe-to-Refresh support
- RecyclerView scroll management
- Abstract methods for layout binding and ViewModel injection

---

## 🧠 BaseViewModel

An abstract base class to streamline ViewModel development with:
- Built-in dependency injection (Context, Shared Preferences, ResourceProvider)
- Reactive state handling via LiveData & SingleLiveEvent
- Built-in pagination and search logic
- Unified API response and error processing

**Reactive Variables:**
- `loading`, `error`, `success`, `isDataEmpty` (LiveData)
- `eventSuccess`, `eventError` (SingleLiveEvent)

**Utility Methods:**
- `checkResponse()`
- `manageErrorAndSessionOut()`
- `managePagination()`
- `manageSearch()`

---

## 🧾 PreferenceManager

A secure and efficient singleton class to manage SharedPreferences using encryption.

**Features:**
- Singleton instance
- Supports all primitive types, JSON, custom models, and arrays
- Fully encrypted storage
- Optional bulk clear functionality

---

## 🌐 Network Module

Comprehensive networking setup using Retrofit and OkHttp with support for:
- Lazy initialization
- Singleton pattern
- HTTP request/response logging (via HttpLoggingInterceptor)

---

## 🧪 ResourcesProvider

Enables clean and testable access to Android string resources within ViewModels without needing direct `Context` access.

**Advantages:**
- Decouples resource access from Android framework
- Enhances ViewModel testability
- Easy to mock during unit testing

---

## 🔁 Unified API Response & Error Handling

Standardizes how API responses and errors are processed throughout the application. Improves maintainability and reduces code repetition.

**Benefits:**
- Uniform API response models
- Centralized session expiration handling
- Scalable error management pattern

---

## 🧩 Kotlin Extensions & Utilities

Boost developer productivity with custom Kotlin extensions for:
- `Activity` & `Fragment` (navigation, keyboard, etc.)
- `View` (visibility handling)
- `Date`, `String`, `Permission` utilities
- JSON conversion helpers
- Network & File operations

---

## ⚙️ EventChannel (Coroutines + Flow)

Advanced event-driven communication using Kotlin Coroutines and Channels. Designed to respect component lifecycles and ensure message delivery.

**Highlights:**
- Lifecycle-aware
- Channel and Flow based architecture
- Simplifies ViewModel-to-UI communication

---

## 📡 SingleLiveEvent

Lifecycle-aware observable designed for single-use events like toasts, snackbars, or navigation.

**Why Use It:**
- Prevents event duplication after configuration changes
- Thread-safe
- Easy to integrate into any LiveData architecture

---

## 🏁 Conclusion

This boilerplate isn't just a template—it is a highly modular, enterprise-grade foundation for Android development. By abstracting and automating repetitive tasks, it enables developers to focus on feature delivery while adhering to clean architecture principles.

Leverage this module to build secure, scalable, and maintainable Android applications from day one.

---

**Author:** Jayanta Sarkar  
**Repository:** [GitHub - android-boilerplate](https://github.com/sarkar-jayanta/android-boilerplate)

