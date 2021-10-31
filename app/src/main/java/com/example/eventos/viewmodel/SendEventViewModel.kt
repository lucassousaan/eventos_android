package com.example.eventos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventos.data.remote.ApiService
import com.example.eventos.data.remote.interfaces.EventsInterface
import com.example.eventos.data.remote.response.EventDTO
import com.example.eventos.data.remote.response.EventResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SendEventViewModel: ViewModel() {

    private val disposables = CompositeDisposable()
    private val api = ApiService.client!!.create(EventsInterface::class.java)

    private val _sendEventLiveData: MutableLiveData<EventResponse> = MutableLiveData()
    val sendEventLiveData: LiveData<EventResponse> get() = _sendEventLiveData

    private val _loadLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val loadLiveData: LiveData<Boolean>
        get() = _loadLiveData

    private val _errorLiveData: MutableLiveData<String> =
        MutableLiveData()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun sendEvent(event: EventDTO) {
        _loadLiveData.value = true
        val observable = api.sendEvent(event).toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                _loadLiveData.value = false
            }
            .subscribe({
                _sendEventLiveData.value = it
            }, {
                _loadLiveData.value = false
                _errorLiveData.value = "Erro ao enviar evento"
            })
        disposables.add(observable)
    }

    override fun onCleared() {
        disposables.clear()
    }
}