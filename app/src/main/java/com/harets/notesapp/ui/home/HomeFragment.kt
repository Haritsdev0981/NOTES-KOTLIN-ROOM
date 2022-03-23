package com.harets.notesapp.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.harets.notesapp.R
import com.harets.notesapp.data.entity.Notes
import com.harets.notesapp.databinding.FragmentHomeBinding
import com.harets.notesapp.ui.NotesViewModel
import com.harets.notesapp.utils.ExtentionFunctions.setActionBar

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val homeViewModel by viewModels<NotesViewModel>()

    private val homeAdapter by lazy { HomeAdapter() }

    private var _currentData: List<Notes>? = null
    private val currentData get() = _currentData as List<Notes>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

//        val navController = findNavController()
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//
//        binding.toolbarHome.apply {
//            (requireActivity() as MainActivity).setSupportActionBar(this)
//            setupWithNavController(navController, appBarConfiguration)
//        }

        binding.apply {
            toolbarHome.setActionBar(requireActivity())

            fab.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addFragment)
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvNotes.apply {
            homeViewModel.getAllData().observe(viewLifecycleOwner) {
                checkIsDataEmpty(it)
                homeAdapter.setData(it)
                _currentData = it
            }
            adapter = homeAdapter
            // staggeredGridLayout = mengisi letak kosong terlebih dahulu pada layout
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }
    }

    private fun checkIsDataEmpty(data: List<Notes>) {
        // menghubungkan dengan si imageView
        binding.apply {
            if (data.isEmpty()) {
                imgNoData.visibility = View.VISIBLE
                rvNotes.visibility = View.INVISIBLE
            } else {
                imgNoData.visibility = View.INVISIBLE
                rvNotes.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val search = menu.findItem(R.id.menu_search)
        val searchAction = search.actionView as? SearchView
        searchAction?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_prioriti_high -> homeViewModel.sortByHighPriority()
                .observe(this) { dataHigh ->
                    homeAdapter.setData(dataHigh)
                }
            R.id.menu_prioriti_low -> homeViewModel.sortByLowPriority().observe(this) {
                homeAdapter.setData(it)
            }
            R.id.menu_delete_all -> confirmDelete()
        }
//        homeViewModel.deleteAllData()
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDelete() {
        if (currentData.isEmpty()) {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.no_notes)) // cara manggil string
                .setMessage("Gaada data sama sekali !")
                .setPositiveButton("Closed") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete All Your Notes ?")
                .setMessage("Are You Sure want clear all of this data ?")
                .setPositiveButton("Yes") { _, _ ->
                    homeViewModel.deleteAllData()
                    Toast.makeText(
                        requireContext(),
                        "Successfully deleted data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
    }

    // ketika dicari
    override fun onQueryTextSubmit(query: String?): Boolean {
        val querySearch = "%$query%"
        query?.let {
            homeViewModel.searchByQuery(querySearch).observe(this){ dataSearch ->
                homeAdapter.setData(dataSearch)
            }
        }
        return true
    }

    // ketika diketik
    override fun onQueryTextChange(newText: String?): Boolean {
        val querySearch = "%$newText%"
        newText?.let {
            homeViewModel.searchByQuery(querySearch).observe(this){ dataSearch ->
                homeAdapter.setData(dataSearch)
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}