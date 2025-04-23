package com.boilerplate.base

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.android_boilerplate.BR
import com.google.android.material.snackbar.Snackbar
import com.boilerplate.dialog.ImagePickerDialogFragment
import com.boilerplate.dialog.MessageDialog
import com.boilerplate.dialog.ProgressDialog
import com.boilerplate.ext.withGravity
import com.boilerplate.interfaces.FilePickerPermission
import com.boilerplate.interfaces.OnDismissedCall
import com.boilerplate.interfaces.OnItemSelected
import com.boilerplate.utils.PrintLogManager
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScoped
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM
    private lateinit var binding: VB
    abstract val layoutId: Int

    @Inject
    lateinit var printLogManager: PrintLogManager

    abstract fun observer()
    abstract fun init()

    private var progressDialog: ProgressDialog? = null
    lateinit var mActivity: FragmentActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.vm, viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.observe(viewLifecycleOwner) { it.manageProgressbar() }
                viewModel.isSecondaryLoading.observe(viewLifecycleOwner) { it.manageProgressbar() }
                viewModel.errorMessage.observe(viewLifecycleOwner) { it.showSnackBar() }
                viewModel.successMessage.observe(viewLifecycleOwner) { it.successDialog() }
                observer()
            }
        }
        init()
    }

    private fun showProgressbar() {
        hideProgressbar()
        if (progressDialog == null) {
            progressDialog = ProgressDialog(mActivity)
        }

        if (progressDialog?.isShowing != true) progressDialog?.show()
    }

    fun hideProgressbar() {
        if (isAdded && progressDialog != null && progressDialog?.isShowing == true) progressDialog?.dismiss()
    }

    protected fun Boolean.manageProgressbar() {
        if (this) showProgressbar() else hideProgressbar()
    }

    private fun messageDialog(message: String) {
        MessageDialog.getInstance(mActivity).setMessage(message).show()
    }

    protected fun String.successDialog() {
        messageDialog(this)
    }

    protected fun String.showSnackBar(gravity: Int = Gravity.TOP) {
        Snackbar.make(binding.root, this, Toast.LENGTH_SHORT)
//            .withBackgroundColor(viewModel.colorPrimary().color())
//            .withTextColor(viewModel.colorSecondary().color())
            .withGravity(gravity).show()
    }

    protected fun snackBar(message: String) {
        message.showSnackBar()
    }

    protected fun SwipeRefreshLayout.refresh(onDismissedCall: OnDismissedCall) {
//        setColorSchemeColors(viewModel.colorPrimary().color())
//        setProgressBackgroundColorSchemeColor(viewModel.colorSecondary().color())
        setOnRefreshListener {
            viewModel.page = 1
            onDismissedCall.onDismissCalled()
            isRefreshing = false
        }
    }

    protected fun manageScroll(recyclerView: RecyclerView, swipeRefreshLayout: SwipeRefreshLayout) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val enabled = recyclerView.getChildAt(0)?.top in 0..50
                swipeRefreshLayout.isEnabled = enabled
            }
        })
    }

    protected fun openImagePickerDialog(
        isChooseFile: Boolean = false,
        fileSizeInMB: Int = 15,
        filePickerPermission: FilePickerPermission,
        onItemSelected: OnItemSelected<Uri>,
    ) {
        //get image uri
        ImagePickerDialogFragment.getInstance()
            .chooseFile(isChooseFile)
            .fileSize(fileSizeInMB)
            .onFilePickerPermission(filePickerPermission)
            .onItemSelect { t ->
                    printLogManager.printMsg("Image: $t")
                    onItemSelected.onItemSelected(t)

            }.show(
                mActivity.supportFragmentManager, "ImagePickerDialogFragment"
            )
    }
}