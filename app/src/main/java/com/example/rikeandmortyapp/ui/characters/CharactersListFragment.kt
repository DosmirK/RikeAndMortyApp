package com.example.rikeandmortyapp.ui.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rikeandmortyapp.utils.Resource
import com.example.rikeandmortyapp.databinding.FragmentCharactersListBinding
import com.example.rikeandmortyapp.ui.characters.adapter.CharacterAdapter
import com.example.rikeandmortyapp.ui.characters.adapter.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharactersListFragment : Fragment() {

    private val viewModel: CharacterViewModel by viewModels()

    lateinit var charactersAdapter: CharacterAdapter

    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charactersAdapter = CharacterAdapter(this::onClicker)
        setCharactersRV()
        observe()
    }



    private fun setCharactersRV() = with(binding.characterRv) {
        adapter = charactersAdapter
        layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun observe() {
        viewModel.giveCharacters().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.massage, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    binding.animLoading.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    charactersAdapter.submitList(it.data)

                }
            }
            if (it !is Resource.Loading) {
                binding.animLoading.visibility = View.GONE
            }
        }
    }

    private fun onClicker(character: String){
        findNavController().navigate(CharactersListFragmentDirections.actionCharactersListFragmentToCharacterDetailFragment(character))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}