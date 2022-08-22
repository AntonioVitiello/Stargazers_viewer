package com.vitiello.android.stargazers.tools

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vitiello.android.stargazers.R
import io.reactivex.Single
import java.net.InetAddress

/**
 * Created by Antonio Vitiello
 */
fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun takeIfNotEmpty(p1: TextView, p2: TextView, block: (t1: String, t2: String) -> Unit?): Unit? {
    return if (p1.isNotEmpty() && p2.isNotEmpty()) block(p1.typedText(), p2.typedText()) else null
}

fun takeIfNotEmpty(p1: String?, p2: String?, block: (String, String) -> Unit) {
    if (p1?.isNotEmpty() == true && p2?.isNotEmpty() == true) block(p1, p2)
}

fun hostAvailable(host: String? = "google.com"): Boolean {
    return try {
        InetAddress.getByName(host) is InetAddress
    } catch (exc: Exception) {
        Log.e("Utils", "Host '$host' not Available.", exc)
        false
    }
}

fun ImageView.loadImage(imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .fit()
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_broken_image)
        .into(this, object : Callback {
            override fun onSuccess() {
                Log.d("AndroidExtensions", "Image loaded: $imageUrl")
            }

            override fun onError(exc: Exception) {
                Log.e("AndroidExtensions", "Error while loading image: $imageUrl", exc)
            }
        })
}

fun <T> Single<T>.manageProgress(progressLiveData: MutableLiveData<SingleEvent<Boolean>>): Single<T> {
    return compose { upstream ->
        upstream
            .doOnSubscribe {
                progressLiveData.postValue(SingleEvent(true))
            }
            .doOnDispose {
                progressLiveData.postValue(SingleEvent(false))
            }
            .doOnError {
                progressLiveData.postValue(SingleEvent(false))
            }
            .doOnSuccess {
                progressLiveData.postValue(SingleEvent(false))
            }
    }
}

fun Int.isPair(): Boolean {
    return this % 2 == 0
}

fun Int.isOdd(): Boolean {
    return !isPair()
}

fun <T : TextView> T.typedText() = text?.toString() ?: ""
fun <T : TextView> T.isEmpty() = text?.isEmpty() ?: true
fun <T : TextView> T.isNotEmpty() = text?.isNotEmpty() ?: false

fun FragmentActivity.closeKeyboard() {
    currentFocus?.let { view ->
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Fragment.closeKeyboard() {
    requireActivity().closeKeyboard()
}
