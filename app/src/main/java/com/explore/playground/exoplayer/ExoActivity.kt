package com.explore.playground.exoplayer

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.explore.playground.R
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_exo.*
import java.io.File

class ExoActivity : AppCompatActivity() {
    val sourceURL: String = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    private lateinit var fileSource: String
    fun getFromRawFile(): ProgressiveMediaSource {
        val dataSource = DefaultDataSourceFactory(this, Util.getUserAgent(this, "EXO"))
        val rawSource = RawResourceDataSource(this)
        rawSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.undergroundstars)))
        return ProgressiveMediaSource.Factory(dataSource).createMediaSource(rawSource.uri)
    }

    fun getFromURL(url: String): ProgressiveMediaSource {
        val dataSource = DefaultDataSourceFactory(this, Util.getUserAgent(this, "EXO"))
        return ProgressiveMediaSource.Factory(dataSource).createMediaSource(Uri.parse(url))
    }

    fun getFromStorage(): ProgressiveMediaSource {
        val dataSource = DefaultDataSourceFactory(this, Util.getUserAgent(this, "EXO"))
        val file = File(fileSource)
        val uri = Uri.fromFile(file)
        return ProgressiveMediaSource.Factory(dataSource).createMediaSource(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo)

        fileSource = "${externalCacheDir?.absolutePath}/audiorecordtest2.mp4"

        val player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player
        playerView.showTimeoutMs = -1 //biar control ga ilang

        player.playWhenReady = false //initial player stop
//        player.prepare(getFromURL(sourceURL))
//        player.prepare(getFromRawFile())
        player.prepare(getFromStorage())

        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_IDLE -> Log.d("LOG", "IDLE")
                    Player.STATE_BUFFERING -> Log.d("LOG", "BUFFER")
                    Player.STATE_READY -> Log.d("LOG", "READY")
                    Player.STATE_ENDED -> player.release()
                }
            }

            override fun onPlayerError(error: ExoPlaybackException) {
                if (error.type == ExoPlaybackException.TYPE_SOURCE) {
                    val cause = error.sourceException
                    if (cause is HttpDataSource.HttpDataSourceException) {
                        val exception = cause as HttpDataSource.HttpDataSourceException
                        val spec = exception.dataSpec
                        if (exception is HttpDataSource.InvalidResponseCodeException) {
                            val errCode = exception.responseCode
                            val errMessage = exception.responseMessage
                            Log.d("MESG", "$errCode $errMessage")
                        } else {
                            val str = exception.cause.toString()
                            Log.d("MESG", "value $str")
                        }
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {

            }
        })
    }
}
