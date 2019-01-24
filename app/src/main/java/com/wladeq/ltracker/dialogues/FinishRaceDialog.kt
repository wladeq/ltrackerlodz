package com.wladeq.ltracker.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import com.wladeq.ltracker.R

class FinishRaceDialog : androidx.fragment.app.DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Do you want to finish race?")
                .setPositiveButton("Finish") { _, _ ->
                    System.exit(0)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
        return builder.create()
    }
}
