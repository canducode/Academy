package com.ngoopy.academy.ui.reader.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ngoopy.academy.data.source.local.entity.ModuleEntity
import com.ngoopy.academy.databinding.FragmentModuleContentBinding
import com.ngoopy.academy.ui.reader.CourseReaderViewModel
import com.ngoopy.academy.viewmodel.ViewModelFactory
import com.ngoopy.academy.vo.Status

class ModuleContentFragment : Fragment() {
    private lateinit var viewModel: CourseReaderViewModel
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
            viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            viewModel.selectedModule.observe(viewLifecycleOwner, { moduleEntity ->
                if (moduleEntity != null) {
                    when (moduleEntity.status) {
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> if (moduleEntity.data != null) {
                            binding.progressBar.visibility = View.GONE
                            if (moduleEntity.data.contentEntity != null) {
                                populateWebView(moduleEntity.data)
                            }
                            setButtonNextPrevState(moduleEntity.data)
                            if (!moduleEntity.data.read) {
                                viewModel.readContent(moduleEntity.data)
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                    binding.btnNext.setOnClickListener { viewModel.setNextPage() }
                    binding.btnPrev.setOnClickListener { viewModel.setPrefPage() }
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

    private fun setButtonNextPrevState(module: ModuleEntity) {
        if (activity != null) {
            when (module.position) {
                0 -> {
                    binding.btnPrev.isEnabled = false
                    binding.btnNext.isEnabled = true
                }
                viewModel.getModuleSize() - 1 -> {
                    binding.btnPrev.isEnabled = true
                    binding.btnNext.isEnabled = false
                }
                else -> {
                    binding.btnPrev.isEnabled = true
                    binding.btnNext.isEnabled = true
                }
            }
        }
    }
}