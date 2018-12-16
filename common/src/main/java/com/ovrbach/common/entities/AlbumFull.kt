package com.ovrbach.common.entities

data class AlbumFull(
        val album_type: String = "",
        val artists: Array<ArtistSimplified> = arrayOf(),
        val images: Array<Image> = arrayOf(),
        val label: String = "",
        val name: String = "",
        val release_date: String,
        val tracks: TracksWrapper = TracksWrapper(),
        val uri: String = ""
) {

    fun getTracksList() = tracks.items.toList()
    fun firstImageUrl() = if (images.isNotEmpty()) images[0].url else null
    fun artistsNames(): String = artists.joinToString { it.name }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlbumFull

        if (album_type != other.album_type) return false
        if (!artists.contentEquals(other.artists)) return false
        if (!images.contentEquals(other.images)) return false
        if (label != other.label) return false
        if (name != other.name) return false
        if (release_date != other.release_date) return false
        if (uri != other.uri) return false

        return true
    }

    override fun hashCode(): Int {
        var result = album_type.hashCode()
        result = 31 * result + artists.contentHashCode()
        result = 31 * result + images.contentHashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + release_date.hashCode()
        result = 31 * result + uri.hashCode()
        return result
    }
}

class TracksWrapper(
        val items: Array<TrackSimplified> = arrayOf()
)
