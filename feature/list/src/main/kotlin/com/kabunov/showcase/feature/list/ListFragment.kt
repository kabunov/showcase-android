package com.kabunov.showcase.feature.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.kabunov.showcase.feature.list.databinding.FragmentListBinding
import com.kabunov.showcase.feature.list.databinding.LayoutListItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listItemsAdapter = ListItemsAdapter(null)

        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listItemsAdapter

            //fix for blinking https://stackoverflow.com/a/32227316/2219237
            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        lifecycleScope.launch {
            viewModel.listUiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    if (it is ListUiState.Success) {
                        listItemsAdapter.submitList(it.irregularVerbs)
                    }
                }
        }
    }
}


class ListItemsAdapter(private val listener: IrregularVerbAdapterListener?) :
    ListAdapter<IrregularVerbViewData, ListItemsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: LayoutListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IrregularVerbViewData) {
            binding.title.text = item.title
            binding.subtitle.text = item.subtitle
//TODO            binding.root.setOnClickListener { listener?.onClick(item) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<IrregularVerbViewData>() {
        override fun areItemsTheSame(oldItem: IrregularVerbViewData, newItem: IrregularVerbViewData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: IrregularVerbViewData, newItem: IrregularVerbViewData): Boolean {
            return oldItem == newItem
        }
    }

    interface IrregularVerbAdapterListener {
        fun onClick(item: IrregularVerbViewData)
    }
}