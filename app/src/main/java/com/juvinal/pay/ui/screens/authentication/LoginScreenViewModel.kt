package com.juvinal.pay.ui.screens.authentication

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.UserLoginRequestBody
import com.juvinal.pay.model.dbModel.Member
import com.juvinal.pay.model.dbModel.User
import com.juvinal.pay.model.dbModel.UserDetails
import com.juvinal.pay.network.ApiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginScreenUiState(
    val document_no: String = "",
    val password: String = "",
    val loginMessage: String = "",
    val userRegistered: Boolean = false,
    val loginButtonEnabled: Boolean = false,
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class LoginScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val savedStateHandle: SavedStateHandle,
    private val dbRepository: DBRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState: StateFlow<LoginScreenUiState> = _uiState.asStateFlow()

    val documentNo: String? = savedStateHandle[LoginScreenDestination.documentNo]
    val password: String? = savedStateHandle[LoginScreenDestination.password]

    private var users = mutableStateListOf<User>()
    private val members = mutableStateListOf<Member>()
    private var loginSuccess = false

    fun loadStartUpData() {
        if(documentNo != null && password != null) {
            _uiState.update {
                it.copy(
                    document_no = documentNo,
                    password = password
                )
            }
        }
    }

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
        var user: UserDetails
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
                val response = apiRepository.loginUser(uiState.value.password, userLoginRequestBody)
                if(response.isSuccessful) {
                    val id = response.body()?.user?.id!!
                    user = dbRepository.getUserDetails(id).first()

                    Log.d("USER_DETAILS", user.toString())

                    when(user.user.user_id !=0 && !loginSuccess) {
                        true -> {
                            loginSuccess = true
                            _uiState.update {
                                it.copy(
                                    loadingStatus = LoadingStatus.SUCCESS,
                                    userRegistered = user.member.mem_no != null
                                )
                            }
                        }
                        false -> {
                            delay(2000L)
                            users.addAll(dbRepository.getUsers().first())
                            members.addAll(dbRepository.getMembers().first())
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loginMessage = "Invalid credentials",
                            loadingStatus = LoadingStatus.FAIL
                        )
                    }
                    Log.e("USER_LOGIN_FAIL_ERROR_RESPONSE", response.toString())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loginMessage = "Failed. Try again later",
                        loadingStatus = LoadingStatus.FAIL
                    )
                }
                Log.e("USER_LOGIN_FAIL_EXCEPTION", e.toString())
            }
        }
    }

    fun resetLoadingStatus() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.INITIAL
            )
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

    init {
        loadStartUpData()
    }

}