package com.wladeq.ltracker.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.wladeq.ltracker.R
import com.wladeq.ltracker.dto.SelectedTrackDTO
import com.wladeq.ltracker.dto.Tracks
import com.wladeq.ltracker.dto.contextDTO
import kotlinx.android.synthetic.main.recycler_item_track.view.*

class TracksAdapter(val items: MutableList<Tracks>, val context: Context, val intent: Intent) : RecyclerView.Adapter<ViewHolder>() {
    var selectedTrack
        get() = contextDTO<SelectedTrackDTO>().selectedTrack
        set(value) {
            contextDTO<SelectedTrackDTO>().selectedTrack = value
        }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_track, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.trackDate.text = "Date of ride" + items.get(position).dateOfRide
        holder.trackImage.setImageDrawable(context.getDrawable(R.drawable.ic_location))
        holder.instructorId.text = "Instructor " + items.get(position).instructorID
        holder.trackElement.setOnClickListener {
            selectedTrack = items.get(position)
            startActivity(context, intent, Bundle())
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val trackElement = view.track_item
    val trackDate = view.track_item.date
    val trackImage = view.track_item.trackImage
    val instructorId = view.track_item.description

}