package com.example.eventos

import com.example.eventos.data.remote.response.EventResponse

interface DeleteEventListener {
    fun onClickDeleteEvent(event: EventResponse, position: Int)
}