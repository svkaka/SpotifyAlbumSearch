package com.ovrbach.common.entities

class TrackSimplified(
        val artists: Array<ArtistSimplified> = arrayOf(),
        val duration_ms: Int = -1,
        val id: String = "",
        val name: String = "",
        val preview_url: String? = null,
        val track_number: Int = -1,
        val uri: String = ""
)