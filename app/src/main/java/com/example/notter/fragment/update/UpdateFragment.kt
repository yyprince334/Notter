package com.example.notter.fragment.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notter.fragment.SharedViewModel
import com.example.notter.R
import com.example.notter.data.models.NotterData
import com.example.notter.data.viewModel.NotterViewModel
import com.example.notter.databinding.FragmentListBinding
import com.example.notter.databinding.FragmentUpdateBinding
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mNotterViewModel: NotterViewModel by viewModels()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUpdateBinding.inflate(inflater, container,false)
        binding.args = args

        setHasOptionsMenu(true)

        binding.currentPrioritySpinner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> deleteItemFromData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = current_title_et.text.toString()
        val description = current_description_et.text.toString()
        val priority = current_priority_spinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            val updateItem = NotterData(

                args.customInput.id,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )
            mNotterViewModel.updateData(updateItem)
            Toast.makeText(requireContext(), "Successfully Update", Toast.LENGTH_SHORT).show()
            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToListFragment())
        } else {
            Toast.makeText(requireContext(), "Please fill out all the fields", Toast.LENGTH_SHORT)
                .show()

        }
    }

    private fun deleteItemFromData() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mNotterViewModel.deleteItem(args.customInput)

            Toast.makeText(
                requireContext(),
                "Successfully Delete: ${args.customInput.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToListFragment())
        }
        builder.setNegativeButton("No") { _, _ ->}
            builder.setTitle("Delete '${args.customInput.title}?'")
            builder.setMessage("Are you sure you want to delete '${args.customInput.title}?'")
            builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}