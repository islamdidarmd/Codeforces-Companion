package com.codeforcesvisualizer.adapter

import android.content.Context
import android.os.CountDownTimer
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.model.Contest
import com.codeforcesvisualizer.util.*

class ContestListAdapter(val context: Context,
                         var contests: List<Contest>,
                         val clickListener: ClickListener<Contest>)
    : RecyclerView.Adapter<ContestListAdapter.ViewHolder>() {

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_contest, parent, false))
    }

    fun updateDataset(newContests: List<Contest>) {
        contests = newContests
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return contests.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       /* holder.itemView.tvName.text = contests[position].name
        holder.itemView.tvType.text = contests[position].type
        holder.itemView.tvFrozen.text = contests[position].frozen.toString()
        holder.itemView.tvDuration.text = secToHourMins(contests[position].durationSeconds.toLong())
        holder.itemView.tvStart.text = getDateFromTimeStamp(contests[position].startTimeSeconds, "EEE MMM dd,yyyy hh:mm:ss a")

        holder.itemView.layoutStarting.hide()

        //showing item appearing animation
        val animation = AnimationUtils.loadAnimation(context,
                if (position > lastPosition)
                    R.anim.top_from_bottom
                else
                    R.anim.down_from_top)
        holder.itemView.startAnimation(animation)
        lastPosition = position

        holder.itemView.setOnClickListener { clickListener.onClicked(contests[position], position) }*/
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
}