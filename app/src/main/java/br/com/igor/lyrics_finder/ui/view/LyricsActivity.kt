package br.com.igor.lyrics_finder.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.igor.lyrics_finder.databinding.ActivityLyricsBinding
import br.com.igor.lyrics_finder.domain.entities.LyricsEntity

class LyricsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLyricsBinding

    private var lyrics: LyricsEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLyricsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkExtras()

        binding.backLyricsImageView.setOnClickListener {
            onBackPressed()
        }
    }

    private fun checkExtras() {
        intent.extras?.let {
            if (it.containsKey("lyrics")) {
                lyrics = it.getSerializable("lyrics") as LyricsEntity
                lyrics?.let{ lyrics -> showLyrics(lyrics) }
            }
        }
    }

    private fun showLyrics(lyrics: LyricsEntity) {
        binding.songTitleTextView.text = lyrics.title
        binding.lyricsTextView.text = lyrics.lyrics
    }
}