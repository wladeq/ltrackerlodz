package com.wladeq.ltracker.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.wladeq.ltracker.R

//this class describes popup, which comes after pressing "finish" button

class FinishRaceDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //using class "Builder" to build pop-up construction
        //taking ready to use "Alert Dialog" construction

        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Do you want to finish race?")
                .setPositiveButton("Finish") { dialog, id ->
                    //closing current class if user wants to stop recording

                    System.exit(0)
                }

                //closing popup if user presses "cancel"
                .setNegativeButton(R.string.cancel) { dialog, id -> }

        // creating "AlertDialog" and returning it where it was called
        return builder.create()
    }
}
