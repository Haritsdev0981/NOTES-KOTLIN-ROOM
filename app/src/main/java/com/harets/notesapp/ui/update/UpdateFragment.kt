package com.harets.notesapp.ui.update

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.harets.notesapp.R
import com.harets.notesapp.data.entity.Notes
import com.harets.notesapp.databinding.FragmentUpdateBinding
import com.harets.notesapp.ui.NotesViewModel
import com.harets.notesapp.utils.ExtentionFunctions.setActionBar
import com.harets.notesapp.utils.HelperFunctions.parseToPriority
import com.harets.notesapp.utils.HelperFunctions.spinnerListener
import java.util.*


class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding as FragmentUpdateBinding

    private val savedArgs: UpdateFragmentArgs by navArgs()
    private val updateViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveArgs = savedArgs
        setHasOptionsMenu(true)

//        val navController = findNavController()
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//
//        binding.toolbarUpdate.apply {
//            (requireActivity() as MainActivity).setSupportActionBar(this)
//            setupWithNavController(navController, appBarConfiguration)
//            navController.addOnDestinationChangedListener{ _, destination, _ ->
//                when(destination.id){
//                    R.id.updateFragment -> setNavigationIcon(R.drawable.ic_left_arrow)
//                }
//            }
//        }

        binding.apply {
            toolbarUpdate.setActionBar(requireActivity())
            spinnerPrioritiesUpdate.onItemSelectedListener =
                spinnerListener(context, binding.priorityIndicator)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
        val action = menu.findItem(R.id.menu_save)
        action.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            updateNote()
        }
    }

//    private fun updateNote() {
//        val title = binding.edtTitleUpdate.text.toString()
//        val desc = binding.edtDescriptionUpdate.text.toString()
//        val priority = binding.spinnerPrioritiesUpdate.selectedItem.toString()
//        val date = Calendar.getInstance().time
//
//        val formatedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
//
//        findNavController().navigate(R.id.action_updateFragment_to_detailFragment)
//        Toast.makeText(context, "Note has been update", Toast.LENGTH_SHORT).show()
//
//        if (title.isEmpty()) {
//            binding.edtTitleUpdate.error = "Please Fill Field"
//        } else if (desc.isEmpty()) {
//            Toast.makeText(context, "Your Notes Is still empty", Toast.LENGTH_LONG).show()
//        } else {
//            updateViewModel.updateNote(
//                Notes(
//                    saveArgs.currentItem.id,
//                    title,
//                    parseToPriority(priority, context),
//                    desc,
//                    formatedDate
//                )
//            )
//            Toast.makeText(context, "successful add note.", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.action_updateFragment_to_detailFragment)
//        }
//    }

    private fun updateNote() {
        with(binding){
            val title = edtTitleUpdate.text.toString()
            val desc = edtDescriptionUpdate.text.toString()
            val priority = spinnerPrioritiesUpdate.selectedItem.toString()

            val calendar = Calendar.getInstance().time
            val date = SimpleDateFormat("dd MMMMM yyyy", Locale.getDefault()).format(calendar)

            val note = Notes(
                savedArgs.currentItem.id,
                title,
                parseToPriority(priority, context),
                desc,
                date
            )

            if (edtTitleUpdate.text.isEmpty() || edtDescriptionUpdate.text.isEmpty()){
                edtTitleUpdate.setError("Please fill field")
                edtDescriptionUpdate.error = "Please fill field"
            }else{
                updateViewModel.updateNote(note)
                val action = UpdateFragmentDirections.actionUpdateFragmentToDetailFragment(note)
                findNavController().navigate(action)
                Toast.makeText(context, "Succesfully update note.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    } //jgn lupa popup to di my_nav nya
}