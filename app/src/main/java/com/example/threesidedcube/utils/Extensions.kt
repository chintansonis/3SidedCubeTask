package com.example.threesidedcube.utils

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.threesidedcube.R

fun Fragment.showOkCancelDialog(title:String? = null, message:String,
                                noAction : ()-> Unit = {},
                                cancelAction : ()-> Unit = {},
                                yesAction : ()-> Unit) : AlertDialog {
    val builder = AlertDialog.Builder(requireContext())
    title.run { builder.setTitle(this)}
    builder.setMessage(message)
    builder.setCancelable(true)
    builder.setOnCancelListener { cancelAction() }
    builder.setPositiveButton(getText(R.string.yes)){ dialog, which ->
        yesAction()
    }
    builder.setNegativeButton(getText(R.string.cancel)){ dialog, which ->
        dialog.dismiss()
        noAction()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
    return dialog
}

/**
 * utility alerdialogue
 */
fun Fragment.showOkDialog(btnText:String?,title:String? = null, message:String, cancellable: Boolean = true,okAction : ()-> Unit = {}){
    val builder = AlertDialog.Builder(requireContext())
    title.run { builder.setTitle(this)}
    builder.setMessage(message)
    builder.setCancelable(cancellable)
    builder.setNegativeButton(btnText){ dialog, _ ->
        dialog.dismiss()
        okAction()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}
