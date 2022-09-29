package com.sdk.mediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.sdk.mediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {
    // https://www.soundhelix.com/audio-examples
    private var musicUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3"
    private var musicUrl2 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
    private var mediaPlayer: MediaPlayer? = null
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.playBtn.setOnClickListener {
            if (mediaPlayer == null) {
                binding.progressbar.isVisible = true
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(musicUrl2)
                mediaPlayer?.setOnPreparedListener(this)
                mediaPlayer?.prepareAsync()
            }
        }
        binding.resBtn.setOnClickListener {
            if (!mediaPlayer?.isPlaying!!) {
                mediaPlayer?.start()
            }
        }
        binding.pauseBtn.setOnClickListener {
            if (mediaPlayer?.isPlaying!!) {
                mediaPlayer?.pause()
            }
        }
        binding.stopBtn.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer = null
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        binding.progressbar.isVisible = false
        mp?.start()
    }
}