package com.multazamgsd.storeez.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.multazamgsd.storeez.R
import com.multazamgsd.storeez.core.domain.model.Story
import com.multazamgsd.storeez.core.presentation.StoryListAdapter
import com.multazamgsd.storeez.core.utils.onFailure
import com.multazamgsd.storeez.core.utils.onLoading
import com.multazamgsd.storeez.core.utils.onSuccess
import com.multazamgsd.storeez.databinding.ActivityStoryListBinding
import com.multazamgsd.storeez.presentation.detail.DetailStoryActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoryListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryListBinding
    private val viewModel: StoryListViewModel by viewModels()

    private val adapter: StoryListAdapter by lazy { StoryListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        title = ""

        binding.rv.adapter = adapter
        adapter.setOnItemCallback(object : StoryListAdapter.ItemCallbacks {
            override fun onClick(story: Story, position: Int) {
                val intent = Intent(this@StoryListActivity, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.EXTRA_STORY, Gson().toJson(story))
                intent.putExtra(DetailStoryActivity.EXTRA_POSITION, position.toString())
                startActivity(intent)
            }

            override fun onFavoriteToggle(story: Story, position: Int) {
                viewModel.toggleFavorite(story)
                lifecycleScope.launch {
                    viewModel.toggleFavoriteResult.collect { result ->
                        result.onFailure {
                            Toast.makeText(this@StoryListActivity, "an error occurred", Toast.LENGTH_LONG).show()
                        }

                        result.onSuccess {
                            adapter.notifyItemChanged(position, it)
                        }
                    }
                }
            }
        })

        lifecycleScope.launch {
            viewModel.stories.collect { result ->
                result.onLoading {
                    binding.rv.visibility = View.GONE
                    binding.loadingState.visibility = View.VISIBLE
                }

                result.onFailure {
                    binding.rv.visibility = View.GONE
                    binding.loadingState.visibility = View.GONE

                    Toast.makeText(this@StoryListActivity, it, Toast.LENGTH_LONG).show()
                }

                result.onSuccess {
                    binding.rv.visibility = View.VISIBLE
                    binding.loadingState.visibility = View.GONE

                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getStories()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.favorite -> {
                val uri = Uri.parse("storeez://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}