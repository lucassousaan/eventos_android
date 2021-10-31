package com.example.eventos.data.remote.interfaces

import com.example.eventos.data.remote.response.EventDTO
import com.example.eventos.data.remote.response.EventResponse
import io.reactivex.Single
import retrofit2.http.*

interface EventsInterface {

    @GET("events")
    fun getEvents(): Single<List<EventResponse>>

    @DELETE("events/{id}")
    fun deleteEvent(@Path("id") id: String): Single<EventResponse>

    @POST("events")
    fun sendEvent(@Body eventsDTO: EventDTO): Single<EventResponse>

}