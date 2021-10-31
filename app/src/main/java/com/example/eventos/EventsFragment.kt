package com.example.eventos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventos.adapter.EventsAdapter
import com.example.eventos.data.remote.response.EventResponse
import com.example.eventos.viewmodel.EventsViewModel
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : Fragment(), DeleteEventListener {

    private lateinit var viewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabAddEvent.setOnClickListener {
            viewModel.getEvents()
        }

        viewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)
        initObservers()
        viewModel.getEvents()
    }

    private fun initObservers() {
        observerLoading()
        observerError()
        observerEvents()
    }

    private fun observerLoading() {
        viewModel.loadLiveData.observe(viewLifecycleOwner, {
            if (it) {
                tv_loading.visibility = View.VISIBLE
                eventsRecyclerView.visibility = View.GONE
            } else {
                tv_loading.visibility = View.GONE
                eventsRecyclerView.visibility = View.VISIBLE
            }
        })
    }

    private fun observerError() {
        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun observerEvents() {
        viewModel.eventsLiveData.observe(viewLifecycleOwner, {
            it?.let { eventsResponse ->
                eventsRecyclerView.adapter = EventsAdapter(eventsResponse, requireContext(), this)
                eventsRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            }
        })
    }

    override fun onClickDeleteEvent(event: EventResponse, position: Int) {
        viewModel.deleteEvent(event)
    }

}