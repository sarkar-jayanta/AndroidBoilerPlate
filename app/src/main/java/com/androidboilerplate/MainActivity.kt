package com.androidboilerplate

import androidx.activity.viewModels
import com.androidboilerplate.databinding.ActivityMainBinding
import com.boilerplate.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()

    override val layoutId: Int = R.layout.activity_main

    override fun init() {

    }

}