package com.example.myuniversity.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myuniversity.Result
import com.example.myuniversity.databinding.FragmentUniversityBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UniversityFragment : Fragment() {

    private var tabName: String? = null

    private var _binding: FragmentUniversityBinding? = null
    private val binding get() = _binding

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUniversityBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)

        //inisiasi viewModel dan menampilkan data
        val factory:ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel : UnivViewModel by viewModels {factory}

        //membaca data bookmark ketika ikon ditekan
        val univAdapter = UniversityAdapter { univ ->
            if (univ.isBookmarked) {
                viewModel.deleteUniv(univ)
            } else {
                viewModel.saveUniv(univ)
            }
        }

        //inisiasi ViewModel dan menampilkan data
        if (tabName  == TAB_UNIV) {
            viewModel.getHeadlineUniv().observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            val univData = result.data
                            univAdapter.submitList(univData)
                        }
                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi Kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } else if (tabName == TAB_BOOKMARK) {
            viewModel.getBookmarkedUniv().observe(viewLifecycleOwner) { bookmarkedUniv ->
                binding?.progressBar?.visibility = View.GONE
                univAdapter.submitList(bookmarkedUniv)
            }
        }

        binding?.rvUniversity?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = univAdapter
            setHasFixedSize(true)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_UNIV = "univ"
        const val TAB_BOOKMARK = "bookmark"
    }
}