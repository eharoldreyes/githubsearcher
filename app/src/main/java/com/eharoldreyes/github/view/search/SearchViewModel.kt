package com.eharoldreyes.github.view.search

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eharoldreyes.github.data.model.Repository
import com.eharoldreyes.github.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _showErrorLiveData = MutableLiveData<String>()
    val showErrorLiveData: LiveData<String> = _showErrorLiveData

    private val _githubRepositories = MutableLiveData<List<Repository>>()
    val githubRepositories: LiveData<List<Repository>> = _githubRepositories

    fun searchRepositories(queryString: String){
        launchRequestWithErrorHandling {
            _githubRepositories.value = repository.searchRepositories(queryString)
        }
    }

    private fun launchRequestWithErrorHandling(
        context: CoroutineContext = EmptyCoroutineContext,
        apiCall: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context) {
        try {
            apiCall()
        } catch (e: Exception) {
            when (e) {
                is NetworkErrorException,
                is SocketTimeoutException,
                is UnknownHostException ->
                    _showErrorLiveData.value = "No connection available"
                is HttpException ->
                    _showErrorLiveData.value = "An API error has occured"
                else ->
                    _showErrorLiveData.value = "Something went wrong. Please try again."
            }
        }
    }
}