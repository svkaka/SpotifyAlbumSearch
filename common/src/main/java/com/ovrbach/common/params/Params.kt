package com.ovrbach.common.params

import com.ovrbach.common.entities.AlbumFull
import com.ovrbach.common.entities.AlbumSimplified

abstract class Params<T>(val params: Map<String, Any>) {
    override fun toString(): String {
        return "Params(params=$params)"
    }
}

const val PARAMS_ALBUMS_QUERY = "query"
const val PARAMS_ALBUM_ID = "albumId"

class AlbumsParams(params: Map<String, Any>) : Params<List<AlbumSimplified>>(params) {

    fun getQuery() = params[PARAMS_ALBUMS_QUERY] as String
}

class AlbumDetailsParams(params: Map<String, Any>) : Params<AlbumFull>(params) {
    fun getAlbumId() = params[PARAMS_ALBUM_ID] as String
}