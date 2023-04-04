package com.example.mediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.example.mediaplayer.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var runnable: Runnable
    private var handler = Handler()
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mediaplayer: MediaPlayer = MediaPlayer.create(this, R.raw.music_instance)
        binding.playBtn.setOnClickListener {
           if(!mediaplayer.isPlaying){
               mediaplayer.start()
               binding.playBtn.setImageResource(R.drawable.ic_baseline_pause_24)
           }
           else{
               mediaplayer.stop()
               binding.playBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
           }
       }
        binding.musicSeekBar.progress = 0
        binding.musicSeekBar.max = mediaplayer.duration
        binding.musicSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, position: Int, changed: Boolean) {
                if(changed){
                    mediaplayer.seekTo(position)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        runnable = Runnable {
            binding.musicSeekBar.progress = mediaplayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable,1000)
        mediaplayer.setOnCompletionListener {
            binding.playBtn.setImageResource(R.drawable.ic_baseline_pause_24)
            binding.musicSeekBar.progress=0
        }
    }
}