package com.example.my_to_do_app.ui

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showDialog(
    message: String,
    posActionName: String? = null,
    posActionCallBack: (() -> Unit)? = null,
    negActionName: String? = null,
    negActionCallBack: (() -> Unit)? =null,
    isCancelable: Boolean = true,
):AlertDialog {
    val alertDialogBuilder = AlertDialog.Builder(requireContext())
    alertDialogBuilder.setMessage(message)

    alertDialogBuilder.setPositiveButton(posActionName)
    { dialog, id ->
        posActionCallBack?.invoke()
        dialog.dismiss()
    }

    alertDialogBuilder.setNegativeButton(negActionName)
    { dialog, id ->
        negActionCallBack?.invoke()
        dialog.dismiss()
    }

    alertDialogBuilder.setCancelable(isCancelable)
    return alertDialogBuilder.show()
}