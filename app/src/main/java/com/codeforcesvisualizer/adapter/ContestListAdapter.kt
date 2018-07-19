package com.codeforcesvisualizer.adapter

import android.content.Context
import android.os.CountDownTimer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.model.Contest
import com.codeforcesvisualizer.util.getDateFromTimeStamp
import com.codeforcesvisualizer.util.hide
import com.codeforcesvisualizer.util.secToHourMins
import com.codeforcesvisualizer.util.show
import kotlinx.android.synthetic.main.row_contest.view.*

class ContestListAdapter(val context: Context,
                         var contests: List<Contest>)
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
        holder.itemView.tvName.text = contests[position].name
        holder.itemView.tvType.text = contests[position].type
        holder.itemView.tvFrozen.text = contests[position].frozen.toString()
        holder.itemView.tvDuration.text = secToHourMins(contests[position].durationSeconds.toLong())
        holder.itemView.tvStart.text = getDateFromTimeStamp(contests[position].startTimeSeconds, "EEE MMM dd,yyyy hh:mm:ss a")

        if (contests[position].phase == Contest.PHASE.BEFORE) {
            hide(holder.itemView.layoutPhase)
        } else {
            show(holder.itemView.layoutPhase)
            holder.itemView.tvPhase.text = contests[position].phase?.name
        }

        hide(holder.itemView.layoutStarting)

        /*if (contests[position].relativeTimeSeconds >= 0) {
            hide(holder.itemView.layoutStarting)
        } else {
            show(holder.itemView.layoutStarting)

            if (holder.timer != null) {
                holder.timer?.cancel()
                holder.timer = null
            }

            holder.timer = object : CountDownTimer(contests[position].relativeTimeSeconds * 1000*-1, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    holder.itemView.tvTimer.text = secToHourMins(millisUntilFinished/1000)
                }

                override fun onFinish() {
                    hide(holder.itemView.layoutStarting)
                }
            }.start()
        }*/

        //showing item appearing animation
        val animation = AnimationUtils.loadAnimation(context,
                if (position > lastPosition)
                    R.anim.top_from_bottom
                else
                    R.anim.down_from_top)
        holder.itemView.startAnimation(animation)
        lastPosition = position
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var timer: CountDownTimer? = null
    }
}