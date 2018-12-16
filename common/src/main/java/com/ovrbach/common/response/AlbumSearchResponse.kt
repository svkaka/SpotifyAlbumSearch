package com.ovrbach.common.response;

import com.ovrbach.common.entities.AlbumSimplified
import java.util.*

class AlbumSearchResponse(
    val albums: AlbumResponseData = AlbumResponseData()

) {
    override fun toString(): String {
        return "AlbumSearchResponse(albums=$albums)"
    }

    fun getItems(): List<AlbumSimplified> = albums.items.toList()
}


class AlbumResponseData(
        val href: String = "",
        val items: Array<AlbumSimplified> = arrayOf(),
        val total: Long = -1
) {
    override fun toString(): String {
        return "AlbumResponseData(href='$href', items=${Arrays.toString(items)}, total=$total)"
    }
}