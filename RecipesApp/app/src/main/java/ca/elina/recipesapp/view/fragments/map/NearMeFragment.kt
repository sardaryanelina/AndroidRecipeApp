package ca.elina.recipesapp.view.fragments.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.elina.recipesapp.databinding.FragmentNearMeBinding
class NearMeFragment : Fragment() {

    private var _binding: FragmentNearMeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNearMeBinding.inflate(inflater, container, false)

        return binding.root
    }
}