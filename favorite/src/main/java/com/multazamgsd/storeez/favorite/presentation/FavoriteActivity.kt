package com.multazamgsd.storeez.favorite.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.multazamgsd.storeez.core.domain.model.Story
import com.multazamgsd.storeez.core.presentation.StoryListAdapter
import com.multazamgsd.storeez.core.utils.onFailure
import com.multazamgsd.storeez.core.utils.onLoading
import com.multazamgsd.storeez.core.utils.onSuccess
import com.multazamgsd.storeez.di.FavoriteModuleDependency
import com.multazamgsd.storeez.favorite.DaggerFavoriteComponent
import com.multazamgsd.storeez.favorite.FavoriteViewModelFactory
import com.multazamgsd.storeez.favorite.databinding.ActivityFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    @Inject
    lateinit var factory: FavoriteViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels{ factory }

    private val adapter: StoryListAdapter by lazy { StoryListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(EntryPointAccessors.fromApplication(
                applicationContext, FavoriteModuleDependency::class.java
            ))
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        title = ""
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.rv.adapter = adapter
        adapter.setOnItemCallback(object : StoryListAdapter.ItemCallbacks {
            override fun onClick(story: Story, position: Int) {

            }

            override fun onFavoriteToggle(story: Story, position: Int) {
                viewModel.toggleFavorite(story)
                lifecycleScope.launch {
                    viewModel.toggleFavoriteResult.collect { result ->
                        result.onFailure {
                            Toast.makeText(this@FavoriteActivity, "an error occurred", Toast.LENGTH_LONG).show()
                        }

                        result.onSuccess {
                            viewModel.getFavorites()
                        }
                    }
                }
            }
        })
        lifecycleScope.launch {
            viewModel.favorites.collect { result ->
                result.onLoading {
                    binding.rv.visibility = View.GONE
                    binding.loadingState.visibility = View.VISIBLE
                }

                result.onFailure {
                    binding.rv.visibility = View.GONE
                    binding.loadingState.visibility = View.GONE

                    Toast.makeText(this@FavoriteActivity, it, Toast.LENGTH_LONG).show()
                }

                result.onSuccess {
                    binding.rv.visibility = View.VISIBLE
                    binding.loadingState.visibility = View.GONE

                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}