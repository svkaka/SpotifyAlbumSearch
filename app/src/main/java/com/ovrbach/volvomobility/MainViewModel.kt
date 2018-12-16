package com.ovrbach.volvomobility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ovrbach.common.entities.AlbumSimplified
import com.ovrbach.common.params.AlbumsParams
import com.ovrbach.common.params.PARAMS_ALBUMS_QUERY
import com.ovrbach.common.response.AlbumSearchResponse
import com.ovrbach.remoterx.SpotifyServiceProvider
import com.ovrbach.volvomobility.util.RequestStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val MINIMUM_QUERY_LENGTH = 3


class MainViewModel : ViewModel() {
    private val api = SpotifyServiceProvider
    private val searchService = api.searchService
    private val compositeDisposable = CompositeDisposable()


    private val loadedAlbumsWrapper = MutableLiveData<RequestStatus<List<AlbumSimplified>>>()

    //a bit overkill I know but it should scale nice and view is super dumb and it's easy to use binding
    val isLoading: LiveData<Boolean> = Transformations.map(loadedAlbumsWrapper) { request ->
        request is RequestStatus.Requesting
    }

    val error: LiveData<String?> = Transformations.map(loadedAlbumsWrapper) { request ->
        when (request) {
            is RequestStatus.Success -> {
                if (request.data.isEmpty())
                    "No results found for \"${(request.params as AlbumsParams).getQuery()}\""
                else null
            }
            else -> {
                (request as? RequestStatus.Error)?.e?.localizedMessage
            }
        }
    }

    val result: LiveData<List<AlbumSimplified>> = Transformations.map(loadedAlbumsWrapper) { request ->
        (request  as? RequestStatus.Success)?.data
    }


    private fun getAlbums(query: String) {
        val params = AlbumsParams(hashMapOf(PARAMS_ALBUMS_QUERY to query))
        println("$params < params")
        if (query.length >= MINIMUM_QUERY_LENGTH) {
            fetchAlbums(params)
        } else {
            loadedAlbumsWrapper.postValue(RequestStatus.Error(params, Error("Very short query")))
        }
    }

    private fun fetchAlbums(params: AlbumsParams) {
        loadedAlbumsWrapper.postValue(RequestStatus.Requesting(params = params))

        val disposable = searchService.getAlbumsForQuery(params.getQuery())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess { response: AlbumSearchResponse ->
                    loadedAlbumsWrapper.postValue(
                            RequestStatus.Success(params,
                                    response.getItems()))
                }
                .doOnError { error: Throwable ->
                    error.printStackTrace()
                    loadedAlbumsWrapper.postValue(
                            RequestStatus.Error(
                                    params = params,
                                    e = error)
                    )
                }
                .subscribe { t1, t2 ->
                }

        compositeDisposable.add(disposable)
    }

    fun updateQuery(query: String) {
        getAlbums(query)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}