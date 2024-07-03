package com.haru.android.main.ui.home.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haru.android.Dailiy.DTO.Daily
import com.haru.android.Dailiy.DTO.DailyResponse
import com.haru.android.Dailiy.repository.DailyRepository
import com.haru.android.Dailiy.repository.DailyRepositoryImpl
import com.haru.android.Dailiy.service.DailyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.lang.Exception

class DailySchedulerViewModel : ViewModel() {
    private val repository: DailyRepository
    private val dailyState: MutableLiveData<DailyResponse<Daily>> = MutableLiveData()
    private val dailyDayState: MutableLiveData<DailyResponse<List<Daily>>> = MutableLiveData()
    private val dailyRangeState: MutableLiveData<DailyResponse<List<List<Daily>>>> = MutableLiveData()

    private val _events = MutableLiveData<DailyResponse<List<Daily>>>()
    val events: LiveData<DailyResponse<List<Daily>>> = _events

    private val _dailyResponse = MutableLiveData<DailyResponse<Daily>>()
    val dailyResponse: LiveData<DailyResponse<Daily>> = _dailyResponse
    init {
        repository = DailyRepositoryImpl(DailyService.apiService)
        fetchEvents()
    }

    fun fetchEvents() {
        _events.value = DailyResponse.Loading()
        viewModelScope.launch {
            val response = repository.getDailyByDay(DateTime.now())
            _events.value = response
        }
    }

    fun createDaily(daily: Daily) {
        _dailyResponse.value = DailyResponse.Loading()
        viewModelScope.launch {
            val response = repository.createDaily(daily)
            _dailyResponse.value = response
        }
    }

//    fun createDaily(daily: Daily) {
//        dailyState.value = DailyResponse.Loading()
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = repository.createDaily(daily)
//            try {
//                if(response.data != null) {
//                    dailyState.postValue(response)
//                } else {
//                    dailyState.postValue(DailyResponse.Error(response.message.toString()))
//                }
//            } catch (e: Exception) {
//                dailyState.postValue(DailyResponse.Error(e.message.toString()))
//            }
//        }
//    }

    fun getDaily(id: Long) {
        dailyState.value = DailyResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDaily(id)
            try {
                if(response.data != null) {
                    dailyState.postValue(response)
                } else {
                    dailyState.postValue(DailyResponse.Error(response.message.toString()))
                }
            } catch (e: Exception) {
                dailyState.postValue(DailyResponse.Error(e.message.toString()))
            }
        }
    }

    fun getDailyByDay(date: DateTime) {
        dailyState.value = DailyResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDailyByDay(date)
            try {
                if(response.data != null) {
                    dailyDayState.postValue(response)
                } else {
                    dailyDayState.postValue(DailyResponse.Error(response.message.toString()))
                }
            } catch (e: Exception) {
                dailyDayState.postValue(DailyResponse.Error(e.message.toString()))
            }
        }
    }
    fun getDailyByRange(startData: DateTime, endDateTime: DateTime) {
        dailyRangeState.value = DailyResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDailyByRange(startData, endDateTime)
            try {
                if(response.data != null) {
                    dailyRangeState.postValue(response)
                } else {
                    dailyRangeState.postValue(DailyResponse.Error(response.message.toString()))
                }
            } catch (e: Exception) {
                dailyRangeState.postValue(DailyResponse.Error(e.message.toString()))
            }
        }
    }

}