package com.ngoopy.academy.ui.reader.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ngoopy.academy.data.ModuleEntity
import com.ngoopy.academy.databinding.FragmentModuleListBinding
import com.ngoopy.academy.ui.reader.CourseReaderActivity
import com.ngoopy.academy.ui.reader.CourseReaderCallback
import com.ngoopy.academy.ui.reader.CourseReaderViewModel
import com.ngoopy.academy.viewmodel.ViewModelFactory

class ModuleListFragment : Fragment(), MyAdapterClickListener {
    private var _binding: FragmentModuleListBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG = ModuleListFragment::class.java.simpleName

        fun newInstance(): ModuleListFragment = ModuleListFragment()
    }

    private lateinit var adapter: ModuleListAdapter
    private lateinit var courseReaderCallback: CourseReaderCallback

    private lateinit var viewModel: CourseReaderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentModuleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]
        adapter = ModuleListAdapter(this)

        binding.progressBar.visibility = View.VISIBLE
        viewModel.getModules().observe(viewLifecycleOwner, { modules ->
            binding.progressBar.visibility = View.GONE
            populateRecyclerView(modules)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        courseReaderCallback = context as CourseReaderActivity
    }

    override fun onItemClicked(position: Int, moduleId: String) {
        courseReaderCallback.moveTo(position, moduleId)
        viewModel.setSelectedModule(moduleId)
    }

    private fun populateRecyclerView(modules: List<ModuleEntity>) {
        binding.progressBar.visibility = View.GONE
        adapter.setModules(modules)
        with(binding.rvModule) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        binding.rvModule.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(binding.rvModule.context, DividerItemDecoration.VERTICAL)
        binding.rvModule.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}