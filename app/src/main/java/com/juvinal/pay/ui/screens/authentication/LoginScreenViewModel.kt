package com.juvinal.pay.ui.screens.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.datastore.UserDSModel
import com.juvinal.pay.model.UserLoginRequestBody
import com.juvinal.pay.network.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginScreenUiState(
    val document_no: String = "",
    val password: String = "",
    val loginButtonEnabled: Boolean = false,
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class LoginScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState: StateFlow<LoginScreenUiState> = _uiState.asStateFlow()

    fun updateDocumentNo(documentNo: String) {
        _uiState.update {
            it.copy(
                document_no = documentNo
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    fun login() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.LOADING
            )
        }
        val userLoginRequestBody = UserLoginRequestBody(
            document_no = uiState.value.document_no,
            password = uiState.value.password
        )
        viewModelScope.launch {
            try {
                val response = apiRepository.loginUser(userLoginRequestBody)
                if(response.isSuccessful) {
                    val dsUserDSModel = UserDSModel(
                        id = response.body()?.user?.id!!,
                        name = response.body()?.user?.name!!,
                        uid = response.body()?.user?.uid!!,
                        surname = response.body()?.user?.surname!!,
                        fname = response.body()?.user?.fname!!,
                        lname = response.body()?.user?.lname!!,
                        document_type = response.body()?.user?.document_type!!,
                        document_no = response.body()?.user?.document_no!!,
                        email = response.body()?.user?.email!!,
                        phone_no = response.body()?.user?.phone_no!!,
                        password = uiState.value.password,
                        created_at = response.body()?.user?.created_at!!,
                        updated_at = response.body()?.user?.updated_at!!
                    )
                    dsRepository.saveUserDetails(dsUserDSModel)
                    _uiState.update {
                        it.copy(
                            loadingStatus = LoadingStatus.SUCCESS
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loadingStatus = LoadingStatus.FAIL
                        )
                    }
                    Log.e("USER_LOGIN_FAIL_ERROR_RESPONSE", response.toString())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loadingStatus = LoadingStatus.FAIL
                    )
                }
                Log.e("USER_LOGIN_FAIL_EXCEPTION", e.toString())
            }
        }
    }

    fun checkIfAllFieldsAreFilled() {
        _uiState.update {
            it.copy(
                loginButtonEnabled = uiState.value.document_no.isNotEmpty() &&
                        uiState.value.password.isNotEmpty()
            )
        }
    }

}