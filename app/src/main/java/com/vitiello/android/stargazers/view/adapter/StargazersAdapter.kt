package com.vitiello.android.stargazers.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.vitiello.android.stargazers.R
import com.vitiello.android.stargazers.model.StargazerModel
import com.vitiello.android.stargazers.tools.isPair
import com.vitiello.android.stargazers.tools.loadImage
import kotlinx.android.synthetic.main.item_stargazer.view.*

/**
 * Created by Antonio Vitiello
 */
class StargazersAdapter(private val mListener: ((StargazerModel) -> Unit)) :
    RecyclerView.Adapter<StargazersAdapter.StargazerViewHolder>() {

    private val mDataItems = mutableListOf<StargazerModel>()
    private var mAnimationId = 0
    private val mAnims = arrayOf(
        R.anim.fade_scale_animation,
        R.anim.fade_rotate_animation,
        R.anim.fade_translate_animation
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StargazerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_stargazer, parent, false)
        return StargazerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StargazerViewHolder, position: Int) {
        holder.bind(mDataItems[position], position)
    }

    override fun getItemCount(): Int {
        return mDataItems.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun switchData(data: List<StargazerModel>?) {
        mDataItems.clear()
        if (data != null) {
            mAnimationId = mAnims.random()
            addData(data)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(data: List<StargazerModel>) {
        mDataItems.addAll(data)
        notifyDataSetChanged()
    }


    inner class StargazerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: StargazerModel, position: Int) {
            with(itemView) {
                isActivated = position.isPair()
                animation = AnimationUtils.loadAnimation(context, mAnimationId)
                avatarImage.loadImage(model.avatarUrl)
                ownerText.text = model.username
                htmlUrlText.text = model.htmlUrl
                setOnClickListener { mListener.invoke(model) }
            }
        }
    }

}