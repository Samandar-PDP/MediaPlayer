package com.sdk.mediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.core.view.isVisible
import com.sdk.mediaplayer.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {
    // https://www.soundhelix.com/audio-examples
    private var musicUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3"
    private var musicUrl2 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
    private var mediaPlayer: MediaPlayer? = null
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        handler = Handler(mainLooper)

        binding.playBtn.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.still)
//                mediaPlayer?.setDataSource(musicUrl2)
//                mediaPlayer?.setOnPreparedListener(this)
//                mediaPlayer?.prepareAsync()
                handler.postDelayed(runnable, 100)
                mediaPlayer?.start()
                binding.seekBar.max = mediaPlayer?.duration!!
                binding.textMax.text = setCurPlayTimeToTextView(mediaPlayer?.duration!!.toLong())
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
            binding.seekBar.progress = 0
            binding.textCurrent.text = "00:00"
        }
        binding.forward.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition?.plus(3000)!!)
        }
        binding.backward.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition?.minus(3000)!!)
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.textCurrent.text = setCurPlayTimeToTextView(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    mediaPlayer?.seekTo(it.progress)
                }
            }
        })
    }

    private var runnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, 100)
            try {
                binding.seekBar.progress = mediaPlayer?.currentPosition!!
                binding.textCurrent.text = setCurPlayTimeToTextView(mediaPlayer?.currentPosition!!.toLong())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        binding.progressbar.isVisible = false
        mp?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (mediaPlayer != null) {
                mediaPlayer?.release()
                mediaPlayer = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setCurPlayTimeToTextView(ms: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(ms)
    }
}