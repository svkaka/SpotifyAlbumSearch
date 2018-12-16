package com.ovrbach.volvomobility.detail

import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MediaPlayerProvider(
) : LifecycleObserver {
    private var mediaPlayer: MediaPlayer? = null

    private var playEnabled: Boolean = false
    private var currentTrack: String? = null
    private var loadingTrack = false

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        playEnabled = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        playEnabled = false
        stopTrack()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        currentTrack = null
    }


    fun addTrackToQueue(path: String) {
        currentTrack = path

        //        if (!isPlaying()) {
        //            playTrack()
        //        }
    }


    fun toggle(): Boolean {
        return if (isPlaying()) {
            stopTrack()
            false
        } else {
            playTrack()
            true
        }
    }

    fun playTrack() {
        if (playEnabled && currentTrack != null) {
            if (!isPlaying() && !loadingTrack) {

                loadingTrack = true
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(currentTrack)
                    setOnPreparedListener {
                        it.start()
                        loadingTrack = false
                    }
                    prepareAsync()
                }
            }
        }
    }

    private fun stopTrack() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun isPlaying() = mediaPlayer?.isPlaying == true
}