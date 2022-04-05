package com.harets.notesapp.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.harets.notesapp.R
import com.harets.notesapp.data.entity.Notes
import com.harets.notesapp.databinding.FragmentDetailBinding
import com.harets.notesapp.ui.NotesViewModel
import com.harets.notesapp.utils.ExtentionFunctions.setActionBar


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    private val navArgs by navArgs<DetailFragmentArgs>()

    private val detailViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

//        val navController = findNavController()
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//
//        binding.toolbarDetail.apply {
//            (requireActivity() as MainActivity).setSupportActionBar(this)
//            setupWithNavController(navController, appBarConfiguration)
//        }
        // Todo inisialisai variable saveArgs dari binding
        binding.safeArgs = navArgs
        binding.toolbarDetail.setActionBar(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit ->{
                val action = DetailFragmentDirections.actionDetailFragmentToUpdateFragment(
                    Notes(
                        navArgs.currentItem.id,
                        navArgs.currentItem.title,
                        navArgs.currentItem.priority,
                        navArgs.currentItem.description,
                        navArgs.currentItem.date
                    )
                )
                findNavController().navigate(action)
            }
            R.id.action_delete -> confirmDeleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteNote() {
        //alertDialog tidak membutuhkan layout hanya membutuhkan class
        AlertDialog.Builder(context).setTitle("Delete Note?")
            .setMessage("Are you sure want to remove '${navArgs.currentItem.title}'. it will be delete for permanent")
            .setPositiveButton("Yes"){ _,_ ->
                detailViewModel.deleteNote(navArgs.currentItem)
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                Toast.makeText(context, "Successfully delete note.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){_,_ ->}
//            .setNeutralButton("Cancel"){_,_ ->}
            .show()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}