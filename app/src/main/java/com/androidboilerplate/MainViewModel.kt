package com.androidboilerplate


import androidx.lifecycle.viewModelScope
import com.androidboilerplate.data.remote.Repository
import com.boilerplate.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository) : BaseViewModel() {

    fun getPostListApiCall() {
        viewModelScope.launch(Dispatchers.Main) {

        }
    }

}