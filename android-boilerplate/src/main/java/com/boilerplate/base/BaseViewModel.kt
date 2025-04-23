package com.boilerplate.base

import android.content.Context
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.boilerplate.di.ResourcesProvider
import com.boilerplate.ext.errorMessage
import com.boilerplate.interfaces.OnDismissedCall
import com.boilerplate.model.ResponseModel
import com.boilerplate.preference.CommonPreferenceManager
import com.boilerplate.utils.PrintLogManager
import com.boilerplate.utils.SingleLiveEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    @ApplicationContext
    lateinit var mContext: Context

    @Inject
    lateinit var preferenceManager: CommonPreferenceManager

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    @Inject
    lateinit var printLogManager: PrintLogManager

    val errorMessage: SingleLiveEvent<String> = SingleLiveEvent()
    val successMessage: SingleLiveEvent<String> = SingleLiveEvent()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isSecondaryLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isPagination: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { this.postValue(false) }
    val isSessionTimeOut: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var isDataEmpty = false
    var perPage = 25
    var page = 1
    var searchValue: String? = null
    var searchJob: Job? = null
    var isApiCalling = false

    protected fun Response<out ResponseModel<out Any>>.checkResponse(): Boolean {
        return if (isSuccessful && body() != null) {
            true
        } else {
            manageErrorAndSessionOut()
            false
        }
    }

    protected fun Response<out ResponseModel<out Any>>.manageErrorAndSessionOut() {
        isLoading.postValue(false)
        isSecondaryLoading.postValue(false)
        if (code() == 401) {
            preferenceManager.removeAllPrefs()
            isSessionTimeOut.postValue(true)
        } else errorMessage.postValue(errorBody().errorMessage())
    }


    protected fun Response<out ResponseModel<out Any>>.success() {
        if (body()?.message != null) successMessage.postValue(body()?.message)
    }

    protected fun loading(isLoad: Boolean) {
        if (page == 1) {
            isLoading.postValue(isLoad)
        } else {
            isPagination.postValue(isLoad)
        }
    }

    protected inline fun <reified T> MutableList<T>.clearList() {
        if (page == 1) this.clear()
    }

    fun managePagination(
        scrollView: NestedScrollView,
        size: Int,
        onDismissedCall: OnDismissedCall,
    ) {
        val view: View = scrollView.getChildAt(scrollView.childCount - 1) ?: return
        val diff: Int = view.bottom - (scrollView.height + scrollView.scrollY)

        if (diff in 0..999 && !isDataEmpty && perPage * page == size && isPagination.value == false) {
            isPagination.postValue(true)
            page++
            onDismissedCall.onDismissCalled()
        }
    }

    fun managePagination(
        scrollView: RecyclerView,
        size: Int,
        onDismissedCall: OnDismissedCall,
    ) {
        val view: View = scrollView.getChildAt(scrollView.childCount - 1) ?: return

        val diff: Int = view.bottom - (scrollView.height + scrollView.scrollY)

        if (diff in 0..999 && !isDataEmpty && perPage * page == size && isPagination.value == false) {
            isPagination.postValue(true)
            page++
            onDismissedCall.onDismissCalled()
        }
    }

    fun manageSearch(view: SearchView, onDismissedCall: OnDismissedCall) {
        view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    page = 1
                    searchValue = newText
                    onDismissedCall.onDismissCalled()
                }

                return false
            }
        })
    }
}