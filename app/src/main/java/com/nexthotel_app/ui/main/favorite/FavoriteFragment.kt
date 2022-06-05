package com.nexthotel_app.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nexthotel_app.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

//    private val adapter = UserAdapter {
//        findNavController().navigate(
//            FavoriteFragmentDirections.actionFavoriteFragmentToDetailUserFragment(
//                it
//            )
//        )
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

//        binding.rvFavorite.adapter =
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}