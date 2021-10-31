package com.example.eventos.data.remote.response

data class EventResponse(
    val _id: String,
    val eventName: String,
    val eventDate: String,
    val eventDescription: String,
    val eventImage: String,
    val eventEmail: String,
    val eventPrice: Double,
)

data class EventDTO(
    val eventName: String,
    val eventDate: String,
    val eventDescription: String,
    val eventImage: String,
    val eventEmail: String,
    val eventPrice: Double,
)