package com.ngoopy.academy.ui.reader.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ngoopy.academy.data.ModuleEntity
import com.ngoopy.academy.databinding.FragmentModuleContentBinding
import com.ngoopy.academy.ui.reader.CourseReaderViewModel
import com.ngoopy.academy.viewmodel.ViewModelFactory

class ModuleContentFragment : Fragment() {
    private var _binding: FragmentModuleContentBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG = ModuleContentFragment::class.java.simpleName
        fun newInstance(): ModuleContentFragment = ModuleContentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModuleContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            binding.progressBar.visibility = View.VISIBLE
            viewModel.getSelectedModule().observe(viewLifecycleOwner, { module ->
                binding.progressBar.visibility = View.GONE
                if (module != null) {
                    populateWebView(module)
                }
            })
        }
    }

    private fun populateWebView(module: ModuleEntity) {
        binding.webView.loadData(module.contentEntity?.content!!,"text/html", "UTF-8")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}