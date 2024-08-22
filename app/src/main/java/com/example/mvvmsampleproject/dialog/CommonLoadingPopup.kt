package com.example.mvvmsampleproject.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.mvvmsampleproject.R
import com.example.mvvmsampleproject.util.LogUtil

class CommonLoadingPopup(context: Context) : Dialog(context), DialogInterface.OnCancelListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i("onCreate")

        window?.apply {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
            requestFeature(Window.FEATURE_NO_TITLE)

            setContentView(R.layout.dialog_common_loading)
            setCanceledOnTouchOutside(false)

            attributes = attributes.apply {
                flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
                dimAmount = 0.5f
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = WindowManager.LayoutParams.MATCH_PARENT
            }

            setBackgroundDrawableResource(android.R.color.transparent)
        }

        updateLayout()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun updateLayout() {
        setCancelable(true)
        setOnCancelListener(this)
        // setTitle(R.string.acc_common_loading)
    }

    override fun onCancel(dialog: DialogInterface?) {
        // Do nothing
    }
}
