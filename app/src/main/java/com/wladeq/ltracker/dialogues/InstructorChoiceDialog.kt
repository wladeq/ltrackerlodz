package com.wladeq.ltracker.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import com.wladeq.ltracker.InstructorChoose
import com.wladeq.ltracker.R
import com.wladeq.ltracker.activities.TrackRecordActivity

class InstructorChoiceDialog : androidx.fragment.app.DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        // setting the name of pop-up
        builder.setTitle(R.string.dialog_choose_instructor)
        // identifying what was chosen
        builder.setItems(R.array.instructors) { _, choice ->
            // reading the choice and sending to other class
            when (choice) {
                0 -> {
                    InstructorChoose().setChoice("ucBNQA1WR3SM5fUgNdniVumm67r2")
                    start()
                }
                1 -> {
                    InstructorChoose().setChoice("3AFbHnKxUkV5xuFlmBWZ57C0RcS2")
                    start()
                }
                2 -> {
                    InstructorChoose().setChoice("8uuQvE4UofbkHdBarenVGlY7IkR2")
                    start()
                }
            }
        }
        return builder.create()
    }

    private fun start() = startActivity(Intent(context, TrackRecordActivity::class.java))

}
