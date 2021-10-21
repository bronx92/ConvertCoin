package br.com.dio.coinconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.coinconverter.data.model.ExchangeResponseValue
import br.com.dio.coinconverter.domain.GetExchangeValueUseCase
import br.com.dio.coinconverter.domain.SaveSearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class MainViewModel(
    private val saveSearchUseCase: SaveSearchUseCase,
    private val getExchangeValueUseCase: GetExchangeValueUseCase
): ViewModel() {


    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun getExchangeValue(coin: String){
            viewModelScope.launch {
                getExchangeValueUseCase(coin)
                    .flowOn(Dispatchers.Main)
                    .onStart {
                        _state.value = State.Loading
                    }
                    .catch {
                        _state.value = State.Error(it)
                    }
                    .collect {
                        _state.value = State.Success(it)
                    }
        }
    }

    fun saveSearch(exchange: ExchangeResponseValue) {
        viewModelScope.launch {
            saveSearchUseCase(exchange)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Saved
                }
        }
    }


    sealed class State {
        object Loading: State()
        object Saved: State()
        data class Success(val exchange: ExchangeResponseValue): State()
        data class Error(val error: Throwable): State()
    }
}