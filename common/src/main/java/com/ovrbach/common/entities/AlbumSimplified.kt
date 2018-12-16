package com.ovrbach.common.entities

//todo rename property names
data class AlbumSimplified(
        val album_type: String = "",
        val artists: Array<ArtistSimplified> = arrayOf(),
        val href: String = "",
        val id: String = "",
        val images: Array<Image> = arrayOf(),
        val name: String = "",
        val release_date: String = "",
        val type: String = "",
        val uri: String = ""
) {


    fun imagesUrls(): List<String> = images.map { it.url }
    fun firstImageUrl() = if (images.isNotEmpty()) images[0].url else null
    fun artistsNames(): String = artists.joinToString { it.name }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlbumSimplified

        if (album_type != other.album_type) return false
        if (!artists.contentEquals(other.artists)) return false
        if (href != other.href) return false
        if (id != other.id) return false
        if (!images.contentEquals(other.images)) return false
        if (name != other.name) return false
        if (release_date != other.release_date) return false
        if (type != other.type) return false
        if (uri != other.uri) return false

        return true
    }

    override fun hashCode(): Int {
        var result = album_type.hashCode()
        result = 31 * result + artists.contentHashCode()
        result = 31 * result + href.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + images.contentHashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + release_date.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + uri.hashCode()
        return result
    }

}
