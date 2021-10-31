package com.example.eventos.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.eventos.DeleteEventListener
import com.example.eventos.R
import com.example.eventos.data.remote.response.EventResponse

class EventsAdapter(val events: List<EventResponse>, val context: Context, val listener: DeleteEventListener) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.card_event,
                parent,
                false
            )
        return EventsViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.setModel(events[position], position)
    }

    inner class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivEvent: ImageView
        val tvTitle: TextView
        val tvDate: TextView
        val tvValue: TextView
        val tvDescription: TextView
        val btDelete: AppCompatButton

        init {
            ivEvent = itemView.findViewById(R.id.iv_event)
            tvTitle = itemView.findViewById(R.id.eventName)
            tvDate = itemView.findViewById(R.id.eventDate)
            tvValue = itemView.findViewById(R.id.eventValue)
            tvDescription = itemView.findViewById(R.id.eventDescription)
            btDelete = itemView.findViewById(R.id.btDelete)
        }

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun setModel(event: EventResponse, position: Int) {
            val text = String.format("%.2f", event.eventPrice).toDouble()
            ivEvent.load(event.eventImage) {
                crossfade(true)
            }
            tvTitle.text = event.eventName
            tvDate.text = event.eventDate
            tvValue.text = "R$ $text"
            tvDescription.text = event.eventDescription
            btDelete.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Apagar evento")
                builder.setMessage("Deseja mesmo apagar \"${event.eventName}\"?")
                builder.setPositiveButton("Sim") { dialog, which ->
                    listener.onClickDeleteEvent(event, position)
                }
                builder.setNegativeButton("Não") { dialog, which ->

                }
                builder.show()
            }

        }
    }
}
