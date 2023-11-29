package com.kabunov.showcase.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
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

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels(extrasProducer = {
        MutableCreationExtras(defaultViewModelCreationExtras).apply { // https://github.com/google/dagger/issues/2287
            set(DEFAULT_ARGS_KEY, bundleOf(DetailsViewModel.ID_KEY to arguments?.getString("id")))
        }
    })
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    }
                }
        }
    }
}