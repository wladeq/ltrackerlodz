package com.wladeq.ltracker

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan

class MakeTextBold {

    fun makeTextBold(vararg content: CharSequence) = apply(content, StyleSpan(Typeface.BOLD))

    private fun apply(content: Array<out CharSequence>, vararg tags: Any): CharSequence {
        return SpannableStringBuilder().apply {
            openTags(tags)
            content.forEach { charSequence ->
                append(charSequence)
            }
            closeTags(tags)
        }
    }

    private fun Spannable.openTags(tags: Array<out Any>) {
        tags.forEach { tag ->
            setSpan(tag, 0, 0, Spannable.SPAN_MARK_MARK)
        }
    }

    private fun Spannable.closeTags(tags: Array<out Any>) {
        tags.forEach { tag ->
            if (length > 0) {
                setSpan(tag, 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {
                removeSpan(tag)
            }
        }
    }
}
