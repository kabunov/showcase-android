package com.kabunov.showcase.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import com.kabunov.showcase.feature.details.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels(extrasProducer = {
        MutableCreationExtras(defaultViewModelCreationExtras).apply { // https://github.com/google/dagger/issues/2287
            set(DEFAULT_ARGS_KEY, bundleOf(DetailsViewModel.ID_KEY to id))
        }
    })

    private lateinit var binding: FragmentDetailsBinding
    private val id by lazy { arguments?.getString("id") ?: throw IllegalStateException("Incorrect id") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu(requireActivity())

        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(binding.toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            it.title = null
        }
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        lifecycleScope.launch {
            viewModel.detailsUiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    if (it is DetailsUiState.Success) {
                        binding.infinitive.text = it.irregularVerb.presentSimple
                        binding.pastSimple.text = it.irregularVerb.pastSimple
                        binding.pastParticiple.text = it.irregularVerb.pastParticiple
                        binding.definition.text = it.irregularVerb.definition
                        bookmarked = it.irregularVerb.bookmarked
                        (activity as? AppCompatActivity)?.invalidateOptionsMenu()
                    }
                }
        }
    }

    private var bookmarked = false
    private fun initMenu(menuHost: MenuHost) {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.details, menu)
            }

            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.menu_bookmark)
                    ?.apply {
                        setIcon(if (bookmarked) R.drawable.ic_star else R.drawable.ic_star_outline)
                        setTitle(if (bookmarked) R.string.menu_unbookmark else R.string.menu_bookmark)
                    }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_bookmark -> {
                        viewModel.toggleBookmark(id, !bookmarked)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}