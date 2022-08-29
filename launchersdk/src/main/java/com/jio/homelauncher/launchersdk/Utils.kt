package com.jio.homelauncher.launchersdk

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object Utils {

    fun getSimpleDialog(context: Context, message : String,title : String, listener : AlertDialogListener) : AlertDialog {
        val alertBuilder : AlertDialog.Builder = AlertDialog.Builder(context)
        alertBuilder.setCancelable(false)
        alertBuilder.setTitle(title)
        alertBuilder.setMessage(message)
        alertBuilder.setPositiveButton(context.resources.getString(R.string.str_yes),
            object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    dialog?.dismiss()
                    listener.onConfirmed(true)
                }
            })

        alertBuilder.setNeutralButton(context.resources.getString(R.string.str_no),
            object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    dialog?.dismiss()
                    listener.onConfirmed(false)
                }
            })
        val alertDialog = alertBuilder.create()
        return alertDialog

    }
}