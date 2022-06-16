package com.nexthotel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nexthotel.databinding.FragmentFacilityBinding

class FacilityFragment : Fragment() {

    private var _binding: FragmentFacilityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacilityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hotel = FacilityFragmentArgs.fromBundle(arguments as Bundle).hotel

        binding.apply {
            backButton.setOnClickListener { activity?.onBackPressed() }

            placesNearbyTextView.text = hotel.placesNearby
            foodDrinkTextView.text = hotel.foodAndDrinksList
            hotelServicesTextView.text = hotel.hotelServicesList
            transportationTextView.text = hotel.transportationList
            generalTextView.text = hotel.generalList
            accessibilityTextView.text = hotel.accessibiltyList
            businessTextView.text = hotel.businessFacilitiesList
            nearbyTextView.text = hotel.nearbyFacilitiesList
            sportsTextView.text = hotel.sportsAndRecreationsList
            kidsTextView.text = hotel.kidsAndPetsList
            thingsTextView.text = hotel.thingsToDoList
            connectivityTextView.text = hotel.connectivityList
            publicTextView.text = hotel.publicFacilitiesList
            shuttleTextView.text = hotel.shuttleServiceList
            inRoomTextView.text = hotel.inRoomFacilitiesList
        }
    }
}