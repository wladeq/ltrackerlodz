package com.wladeq.ltracker.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.wladeq.ltracker.InstructorChoose
import com.wladeq.ltracker.R

class InstructorChoiceDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        // setting the name of pop-up
        builder.setTitle(R.string.dialog_choose_instructor)
        // identifying what was chosen
        builder.setItems(R.array.instructors) { dialog, choice ->
            // reading the choice and sending to other class
            when(choice){
                0 -> InstructorChoose().setChoice("ucBNQA1WR3SM5fUgNdniVumm67r2")
                1 -> InstructorChoose().setChoice("3AFbHnKxUkV5xuFlmBWZ57C0RcS2")
                2 -> InstructorChoose().setChoice("8uuQvE4UofbkHdBarenVGlY7IkR2")
            }
        }
        return builder.create()
    }
}
