package com.multazamgsd.storeez.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.multazamgsd.storeez.R
import com.multazamgsd.storeez.core.domain.model.Story
import com.multazamgsd.storeez.core.utils.GeneralHelper.toDateFormat
import com.multazamgsd.storeez.core.utils.onSuccess
import com.multazamgsd.storeez.databinding.ActivityDetailStoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private val viewModel: DetailStoryViewModel by viewModels()

    private lateinit var storyData: Story

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storyData = Gson().fromJson(
            intent.getStringExtra(EXTRA_STORY),
            Story::class.java
        ) ?: throw Exception("data must be passed as intent's extra")

        Glide.with(binding.root)
            .load(storyData.photoUrl)
            .into(binding.imageStory)

        binding.textAuthor.text = storyData.name
        binding.textDescription.text = storyData.description
        binding.textDate.text = storyData.createdAt.toDateFormat()

        binding.buttonFavorite.setImageDrawable(
            if (storyData.isFavorite) {
                ContextCompat.getDrawable(this, com.multazamgsd.storeez.core.R.drawable.ic_favorite_filled)
            } else {
                ContextCompat.getDrawable(this, com.multazamgsd.storeez.core.R.drawable.ic_favorite_border)
            }
        )
        binding.buttonFavorite.setOnClickListener {
            viewModel.toggleFavorite(storyData)

            lifecycleScope.launch {
                viewModel.toggleFavoriteResult.collect { result ->
                    result.onSuccess {
                        binding.buttonFavorite.setImageDrawable(
                            if (it?.isFavorite == true) {
                                ContextCompat.getDrawable(this@DetailStoryActivity, com.multazamgsd.storeez.core.R.drawable.ic_favorite_filled)
                            } else {
                                ContextCompat.getDrawable(this@DetailStoryActivity, com.multazamgsd.storeez.core.R.drawable.ic_favorite_border)
                            }
                        )
                        storyData = it!!
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_STORY = "EXTRA_STORY"
        const val EXTRA_POSITION = "EXTRA_POSITION"
    }
}