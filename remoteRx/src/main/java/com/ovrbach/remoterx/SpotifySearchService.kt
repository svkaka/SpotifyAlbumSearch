package com.ovrbach.remoterx

import com.ovrbach.common.entities.AlbumFull
import com.ovrbach.common.response.AlbumSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val BASE_URL = "https://api.spotify.com/v1/"

interface SpotifySearchService {

    @GET("search")
    fun getAlbumsForQuery(
            @Query("q") query: String,
            @Query("limit") limit: Int = REQUEST_LIMIT,
            @Query("type") type: String = REQUEST_ALBUM_TYPE
    ): Single<AlbumSearchResponse>

    @GET("albums/{id}")
    fun getAlbumDetails(
            @Path("id") id: String
    ): Single<AlbumFull>
}

const val REQUEST_LIMIT = 20
const val REQUEST_ALBUM_TYPE = "album"