package com.example.eventos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.eventos.data.remote.response.EventDTO
import com.example.eventos.viewmodel.EventsViewModel
import com.example.eventos.viewmodel.SendEventViewModel
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_events.*

class AboutFragment : Fragment() {

    private lateinit var viewModel: SendEventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SendEventViewModel::class.java)

        btSend.setOnClickListener {
            val eventDTO = EventDTO(
                eventName = et_eventTitle.text.toString(),
                eventDate = et_eventDate.text.toString(),
                eventDescription = et_eventDescription.text.toString(),
                eventImage = et_eventImage.text.toString(),
                eventEmail = et_eventEmail.text.toString(),
                eventPrice = et_eventPrice.text.toString().toDouble()
            )
            viewModel.sendEvent(eventDTO)
        }

        initObservers()
    }

    private fun initObservers() {
        observerLoading()
        observerError()
        observerEvent()
    }

    private fun observerLoading() {
        viewModel.loadLiveData.observe(viewLifecycleOwner, {
            if (it) {
                tv_loadingAbout.visibility = View.VISIBLE
                nsvEvent.visibility = View.GONE
            } else {
                tv_loadingAbout.visibility = View.GONE
                nsvEvent.visibility = View.VISIBLE
            }
        })
    }

    private fun observerError() {
        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun observerEvent() {
        viewModel.sendEventLiveData.observe(viewLifecycleOwner, {
            et_eventTitle.text?.clear()
            et_eventDate.text?.clear()
            et_eventDescription.text?.clear()
            et_eventImage.text?.clear()
            et_eventEmail.text?.clear()
            et_eventPrice.text?.clear()
        })
    }
}