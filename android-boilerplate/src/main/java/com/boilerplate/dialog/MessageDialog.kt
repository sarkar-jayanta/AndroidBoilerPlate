package com.boilerplate.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android_boilerplate.R
import com.android_boilerplate.databinding.DialogMessageBinding
import com.boilerplate.ext.hideShowViews
import com.boilerplate.ext.isNotNullOrEmpty

class MessageDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogMessageBinding
    private var title: String? = null
    private var message: String? = null
    private var positiveButtonText: String? = null

    companion object {
        fun getInstance(context: Context): MessageDialog {
            return MessageDialog(context)
        }
    }

    fun setTitle(title: String): MessageDialog {
        this.title = title
        return this
    }

    fun setMessage(msg: String): MessageDialog {
        message = msg
        return this
    }

    fun setButtonText(btnText: String): MessageDialog {
        positiveButtonText = btnText
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_message,
            null,
            false
        )

        binding.title = title
        binding.message = message

        if (positiveButtonText.isNotNullOrEmpty()) {
            binding.txtOk.text = positiveButtonText
        }

        hideShowViews(title.isNotNullOrEmpty(), binding.txtTitle)
        hideShowViews(message.isNotNullOrEmpty(), binding.txtMessage)

        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set dialog window width to match parent
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        binding.txtOk.setOnClickListener {
            dismiss()
        }

    }
}
