package com.example.student.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.student.R
import com.example.student.databinding.FragmentStudentListBinding
import com.example.student.viewmodel.ListViewModel

class StudentListFragment : Fragment() {
    private lateinit var binding:FragmentStudentListBinding
    private val adapter = StudentListAdapter(arrayListOf())
    private lateinit var  viewModel: ListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(ListViewModel::class.java)
        viewModel.refresh()

        //tes 123

        //BUG FIX

        //BUG FIX2

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            adapter.updateStudentList(it)
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer{
            if(it == true){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.errorLD.observe(viewLifecycleOwner, Observer{
            if(it == true){
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.GONE
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentListBinding.inflate(inflater,
            container,false)
        return binding.root
    }
}