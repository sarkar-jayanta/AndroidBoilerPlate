package com.boilerplate.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.android_boilerplate.R
import com.android_boilerplate.databinding.DialogConfirmationBinding


class ConfirmationDialog(
    context: Context,
    private var message: String?,
) : Dialog(context) {

    private lateinit var binding: DialogConfirmationBinding
    private var listener: OkButtonListener? = null
    private var positiveTxt = context.getString(android.R.string.ok)
    private var title: String? = null
    private var negativeTxt = context.getString(R.string.cancel)
    private var negativeBtnVisibility = View.GONE
    private var cancellable = false

    companion object {
        fun getInstance(
            context: Context,
            message: String? = null,
        ): ConfirmationDialog {
            return ConfirmationDialog(context, message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(cancellable)
        setCancelable(cancellable)

        binding = DialogConfirmationBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )

        setContentView(binding.root)
        if (!message.isNullOrBlank()) binding.tvMessage.text = message
        if (positiveTxt.isNotBlank()) binding.btnOk.text = positiveTxt
        if (negativeTxt.isNotBlank()) binding.btnCancel.text = negativeTxt
        binding.btnCancel.visibility = negativeBtnVisibility
        binding.titleText = title
        binding.btnOk.setOnClickListener {
            if (listener == null) dismiss()
            else {
                listener?.onOkPressed(this)
            }
        }


        binding.btnCancel.setOnClickListener {
            if (listener == null) dismiss()
            else listener?.onCancelClicked(this)
        }
    }

    fun setTitle(title: String): ConfirmationDialog {
        this.title = title
        return this
    }

    fun setMessage(msg: String): ConfirmationDialog {
        message = msg
        return this
    }

    fun setListener(listener: OkButtonListener?): ConfirmationDialog {
        this.listener = listener
        return this
    }

    fun setNegativeButton(isVisible: Boolean, text: String = "Cancel"): ConfirmationDialog {
        this.negativeBtnVisibility = if (isVisible) View.VISIBLE else View.GONE
        this.negativeTxt = text
        return this
    }

    fun setPositiveButtonText(text: String = "Ok"): ConfirmationDialog {
        this.positiveTxt = text
        return this
    }

    fun setCancellable(cancellable: Boolean): ConfirmationDialog {
        this.cancellable = cancellable
        return this
    }

    interface OkButtonListener {
        fun onOkPressed(dialog: ConfirmationDialog)

        fun onCancelClicked(dialog: ConfirmationDialog) {
            dialog.dismiss()
        }
    }
}
