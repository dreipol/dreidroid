package com.github.dreipol.dreidroid.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Basic dialog that ask for approval and executes 'onApproval' if
 * user clicks the positive button.
 */
public class ApprovalDialog(
    private val titleResource: Int,
    private val messageResource: Int,
    private val cancelMessageResource: Int,
    private val approvalMessageResource: Int,
    private val onApproval: () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.apply {
            setTitle(titleResource)
            setMessage(messageResource)
            setNegativeButton(cancelMessageResource) { _, _ -> Unit }
            setPositiveButton(approvalMessageResource) { _, _ -> onApproval() }
        }

        return builder?.create() ?: throw Exception()
    }
}