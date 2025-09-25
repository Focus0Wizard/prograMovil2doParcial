package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class DollarHistoryViewModel(
    private val localDataSource: DollarLocalDataSource
) : ViewModel() {

    private val _historyState = MutableStateFlow<List<DollarModel>>(emptyList())
    val historyState: StateFlow<List<DollarModel>> = _historyState.asStateFlow()

    fun loadHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val history = localDataSource.getAllOrderedByDate()
                _historyState.value = history
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun deleteDollar(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localDataSource.deleteById(id)
                loadHistory() // Recargar
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}