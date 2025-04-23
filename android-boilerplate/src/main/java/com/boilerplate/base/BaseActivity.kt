package com.boilerplate.base

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.boilerplate.dialog.ProgressDialog
import com.boilerplate.ext.withGravity
import com.boilerplate.utils.PrintLogManager
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: VM

    protected lateinit var binding: B

    abstract val layoutId: Int

    @Inject
    lateinit var printLogManager: PrintLogManager

    private var progressDialog: ProgressDialog? = null

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        init()
    }

    private fun showProgressbar() {
        hideProgressbar()
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
        }
        progressDialog?.show()
    }

    private fun hideProgressbar() {
        if (progressDialog != null && progressDialog?.isShowing == true) progressDialog!!.dismiss()
    }

    protected fun Boolean.manageProgressbar() {
        if (this) showProgressbar() else hideProgressbar()
    }

    fun String.showSnackBar(gravity: Int = Gravity.TOP) {
        Snackbar.make(binding.root, this, Toast.LENGTH_SHORT)
//            .withBackgroundColor(viewModel.colorPrimary().color())
//            .withTextColor(viewModel.colorSecondary().color())
            .withGravity(gravity).show()
    }

}