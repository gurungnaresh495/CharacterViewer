package com.example.simpsonsviewer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpsonsviewer.MainActivity
import com.example.simpsonsviewer.adapter.CharacterListAdapter
import com.example.simpsonsviewer.databinding.FragmentItemListBinding
import com.example.simpsonsviewer.viewModel.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ItemListFragment : Fragment() {

    private val characterListViewModel by viewModels<CharacterListViewModel>()
    private lateinit var binding: FragmentItemListBinding
    private lateinit var adapter : CharacterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CharacterListAdapter(requireActivity() as MainActivity)
        binding.rvCharacterNameList.adapter = adapter
        binding.rvCharacterNameList.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch{
            characterListViewModel.characterList.collect{
                adapter.updateList(it)
                Log.d("CharacterList xo" , it.toString())
            }
        }

        lifecycleScope.launch{
            characterListViewModel.error.collect{
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        // Note: We set the Fragment title when we extract the Bundle arguments
        super.onViewCreated(view, savedInstanceState)
    }


    fun searchProducts(searchText: String){
        adapter.filterList(searchText)
    }

    companion object {
        const val LIST_FRAGMENT_TAG = "Character List Fragment tag"
    }
}