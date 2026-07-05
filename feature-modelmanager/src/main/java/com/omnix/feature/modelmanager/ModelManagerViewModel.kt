package com.omnix.feature.modelmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omnix.core.data.repository.ModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelManagerViewModel @Inject constructor(
    private val modelRepository: ModelRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelManagerUiState())
    val uiState: StateFlow<ModelManagerUiState> = _uiState.asStateFlow()

    init {
        loadDeviceInfo()
        observeModels()
    }

    private fun loadDeviceInfo() {
        viewModelScope.launch {
            try {
                val profile = modelRepository.getDeviceProfile()
                val recommendation = modelRepository.getRecommendation()
                _uiState.update {
                    it.copy(
                        isLoadingDevice = false,
                        deviceProfile = profile,
                        recommendation = recommendation
                    )
                }
            } catch (t: Throwable) {
                _uiState.update {
                    it.copy(isLoadingDevice = false, errorMessage = t.message)
                }
            }
        }
    }

    private fun observeModels() {
        viewModelScope.launch {
            combine(
                modelRepository.observeAllModels(),
                modelRepository.observeActiveModel()
            ) { all, active -> Pair(all, active) }
                .collect { (all, active) ->
                    _uiState.update {
                        it.copy(
                            isLoadingModels = false,
                            allModels = all,
                            activeModel = active
                        )
                    }
                }
        }
    }

    fun downloadModel(modelId: String) {
        if (_uiState.value.downloadingModelId != null) return
        _uiState.update { it.copy(downloadingModelId = modelId) }
        viewModelScope.launch {
            try {
                modelRepository.downloadModel(modelId)
            } catch (t: Throwable) {
                _uiState.update { it.copy(errorMessage = t.message) }
            } finally {
                _uiState.update { it.copy(downloadingModelId = null) }
            }
        }
    }

    fun selectModel(modelId: String) {
        viewModelScope.launch {
            try {
                modelRepository.selectModel(modelId)
            } catch (t: Throwable) {
                _uiState.update { it.copy(errorMessage = t.message) }
            }
        }
    }

    fun deleteModel(modelId: String) {
        viewModelScope.launch {
            try {
                modelRepository.deleteModel(modelId)
            } catch (t: Throwable) {
                _uiState.update { it.copy(errorMessage = t.message) }
            }
        }
    }

    fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
