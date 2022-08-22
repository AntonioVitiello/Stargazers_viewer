package com.vitiello.android.stargazers.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.vitiello.android.stargazers.R
import com.vitiello.android.stargazers.tools.takeIfNotEmpty
import kotlinx.android.synthetic.main.dialog_credentials.view.*

/**
 * Created by Antonio Vitiello
 */
class CredentialsDialog : DialogFragment() {

    private lateinit var listener: ICredentialsDialogListener

    interface ICredentialsDialogListener {
        fun onDialogPositiveClick(username: String, password: String)
    }

    companion object {
        const val TAG = "CredentialsDialog"
        private const val USERNAME_ARGS_KEY = "username_args_key"
        private const val PASSWORD_ARGS_KEY = "password_args_key"

        fun newInstance(username: String? = null, password: String? = null): CredentialsDialog {
            return CredentialsDialog().apply {
                arguments = Bundle().apply {
                    putString(USERNAME_ARGS_KEY, username)
                    putString(PASSWORD_ARGS_KEY, password)
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is ICredentialsDialogListener) {
            listener = activity as ICredentialsDialogListener
        } else throw IllegalArgumentException(getString(R.string.implements_icredentials))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialog_credentials, null)
        view.usernameEditText.setText(requireArguments().getString(USERNAME_ARGS_KEY))
        view.passwordEditText.setText(requireArguments().getString(PASSWORD_ARGS_KEY))
        isCancelable = false
        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle(getString(R.string.credentials))
            .setNegativeButton(getString(R.string.cancel), null)
            .setPositiveButton(getString(R.string.accept)) { dialog, which ->
                takeIfNotEmpty(
                    view.usernameEditText.text.toString(),
                    view.passwordEditText.text.toString()
                ) { username, password ->
                    listener.onDialogPositiveClick(username, password)
                }
            }
        return builder.create()
    }

}
