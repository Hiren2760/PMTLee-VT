package com.pg.crm.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Toast
import com.pg.crm.R

class AppUtils {
    companion object {
        var dialog: Dialog? = null //obj
        fun showLoadingDialog(context: Context): Dialog {
            dialog = Dialog(context)
            val inflate = LayoutInflater.from(context)
                .inflate(R.layout.progress_dialog, null)
            dialog!!.setContentView(inflate)
            dialog!!.setCancelable(false)
            dialog!!.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            dialog!!.show()
            return dialog!!
        }

        fun hideDialog() {
            try {
                if (dialog != null) {
                    dialog!!.dismiss()
                }
            } catch (e: Exception) {
            }
        }

        fun toastMsg(context: Context, meaasge: String) {
            Toast.makeText(context, meaasge, Toast.LENGTH_SHORT).show()
        }
    }
}