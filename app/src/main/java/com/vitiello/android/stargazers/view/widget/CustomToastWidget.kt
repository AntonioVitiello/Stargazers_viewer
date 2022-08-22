package com.vitiello.android.stargazers.view.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.vitiello.android.stargazers.R
import kotlinx.android.synthetic.main.widget_custom_toast.view.*

/**
 * Created by Antonio Vitiello
 */
class CustomToastWidget : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.widget_custom_toast, this)
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToastWidget)
            try {
                val message = typedArray.getText(R.styleable.CustomToastWidget_toast_message)
                if (message != null) {
                    setMessage(message)
                }
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun setMessage(message: CharSequence) {
        messageText.text = message
    }

    fun showFaded(@StringRes idMessage: Int) {
        showFaded(context.getText(idMessage))
    }

    fun showFaded(message: CharSequence) {
        setMessage(message)
        showFaded()
    }

    fun showFaded() {
        val showAnimator = ObjectAnimator.ofFloat(container, View.ALPHA, 1f).apply {
            duration = 400
            // interpolator = DecelerateInterpolator(1f)
            interpolator = AccelerateInterpolator(1f)
        }
        val hideAnimator = ObjectAnimator.ofFloat(container, View.ALPHA, 0f).apply {
            duration = 3000
            interpolator = AccelerateInterpolator(2f)
        }
        AnimatorSet().apply {
            play(showAnimator).before(hideAnimator)
        }.start()
    }

}