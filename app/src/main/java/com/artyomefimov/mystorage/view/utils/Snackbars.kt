package com.artyomefimov.mystorage.view.utils

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbarWithMessage(messageResId: Int) =
    Snackbar
        .make(
            view!!,
            messageResId,
            Snackbar.LENGTH_LONG
        ).show()

fun Fragment.showSnackbarWithMessage(message: String) =
    Snackbar
        .make(
            view!!,
            message,
            Snackbar.LENGTH_LONG
        ).show()

fun Fragment.showSnackbarWithAction(messageResId: Int, actionTextResId: Int, action: () -> Unit) =
    Snackbar
        .make(
            view!!,
            messageResId,
            Snackbar.LENGTH_INDEFINITE
        )
        .setAction(actionTextResId) {
            action()
        }.show()