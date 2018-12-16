package com.ovrbach.volvomobility.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ovrbach.common.entities.AlbumFull
import com.ovrbach.common.params.AlbumDetailsParams
import com.ovrbach.common.params.PARAMS_ALBUM_ID
import com.ovrbach.common.response.AlbumSearchResponse
import com.ovrbach.remoterx.SpotifyServiceProvider
import com.ovrbach.volvomobility.util.RequestStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {
    private val api = SpotifyServiceProvider
    private val searchService = api.searchService
    private val compositeDisposable = CompositeDisposable()


    private val albumDetails: MutableLiveData<RequestStatus<AlbumFull>> = MutableLiveData()
    val result: LiveData<AlbumFull?> = Transformations.map(albumDetails) { request ->
        (request as? RequestStatus.Success)?.data
    }

    val error: LiveData<String?> = Transformations.map(albumDetails) { request ->
        (request as? RequestStatus.Error)?.e?.localizedMessage
    }

    val isLoading: LiveData<Boolean?> = Transformations.map(albumDetails) { request ->
        request is RequestStatus.Requesting
    }

    fun getAlbumDetails(id: String?) {
        if (id == null) {
            //todo show error
            return
        }
        val params = AlbumDetailsParams(params = hashMapOf(PARAMS_ALBUM_ID to id))
        fetchAlbumDetails(params)
    }

    private fun fetchAlbumDetails(params: AlbumDetailsParams) {
        albumDetails.postValue(RequestStatus.Requesting(params = params))

        val disposable = searchService.getAlbumDetails(params.getAlbumId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess { response: AlbumFull ->
                    albumDetails.postValue(
                            RequestStatus.Success(params,
                                    response))
                }
                .doOnError { error: Throwable ->
                    error.printStackTrace()
                    albumDetails.postValue(
                            RequestStatus.Error(
                                    params = params,
                                    e = error)
                    )
                }
                .subscribe { t1, t2 ->
                }

        compositeDisposable.add(disposable)
    }
}