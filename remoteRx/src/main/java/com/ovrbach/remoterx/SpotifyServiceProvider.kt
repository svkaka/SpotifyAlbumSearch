package com.ovrbach.remoterx


object SpotifyServiceProvider {

    val searchService: SpotifySearchService by lazy {
        ServiceGenerator.createService(SpotifySearchService::class.java)
    }

}