package br.com.igor.lyrics_finder.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import br.com.igor.lyrics_finder.R
import br.com.igor.lyrics_finder.databinding.ActivityMainBinding
import br.com.igor.lyrics_finder.domain.entities.LyricsEntity
import br.com.igor.lyrics_finder.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        observeViewModel()

        binding.searchImageButton.setOnClickListener {
            viewModel.fetchLyrics(binding.artistEditText.text.toString(), binding.songEditText.text.toString())
        }

        binding.songEditText.doOnTextChanged { _, _, _, _ ->
            binding.searchImageButton.isVisible = binding.songEditText.text.toString().isNotEmpty() && binding.artistEditText.text.toString().isNotEmpty()
        }

        binding.artistEditText.doOnTextChanged { _, _, _, _ ->
            binding.searchImageButton.isVisible = binding.songEditText.text.toString().isNotEmpty() && binding.artistEditText.text.toString().isNotEmpty()
        }
    }

    private fun observeViewModel() {
        viewModel.lyrics.observe(this) {
            navigateToLyricsActivity(it)
        }

        viewModel.loading.observe(this) {
            if (it)
                showLoading()
            else
                hideLoading()
        }

        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideLoading() {
        binding.progressMainActivity.visibility = View.GONE
        binding.searchImageButton.isEnabled = true
        binding.artistEditText.isEnabled = true
        binding.songEditText.isEnabled = true
    }

    private fun showLoading() {
        binding.progressMainActivity.visibility = View.VISIBLE
        binding.searchImageButton.isEnabled = false
        binding.artistEditText.isEnabled = false
        binding.songEditText.isEnabled = false
    }

    private fun navigateToLyricsActivity(lyrics: LyricsEntity?) {
        val intent = Intent(this, LyricsActivity::class.java)
        intent.putExtra("lyrics", lyrics)
        startActivity(intent)
        overridePendingTransition(R.anim.animate_zoom_enter, R.anim.animate_zoom_exit)
    }
}