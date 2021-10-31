package com.example.eventos.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventos.data.remote.ApiService
import com.example.eventos.data.remote.interfaces.EventsInterface
import com.example.eventos.data.remote.response.EventResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EventsViewModel : ViewModel() {

    private val disposables = CompositeDisposable()
    private val api = ApiService.client!!.create(EventsInterface::class.java)

    private val _eventsLiveData: MutableLiveData<List<EventResponse>> = MutableLiveData()
    val eventsLiveData: LiveData<List<EventResponse>> get() = _eventsLiveData
    private val _loadLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadLiveData: LiveData<Boolean> get() = _loadLiveData
    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun getEvents() {
        _loadLiveData.value = true
        val observable = api.getEvents().toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                _loadLiveData.value = false
            }
            .subscribe({
                _eventsLiveData.value = it
                Log.d("LISTA", "" + it.size)
            }, {
                Log.d("LISTA", "" + it.message)
                _loadLiveData.value = false
                _errorLiveData.value = "Tivemos um problema, tente novamente!"
            })
        disposables.add(observable)
    }

    fun deleteEvent(event: EventResponse) {
        _loadLiveData.value = true
        val observable = api.deleteEvent(event._id).toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                _loadLiveData.value = false
            }
            .subscribe({
                getEvents()
            }, {
                _loadLiveData.value = false
                _errorLiveData.value = "Erro"
            })
        disposables.add(observable)
    }

    override fun onCleared() {
        disposables.clear()
    }
}