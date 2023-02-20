package ca.elina.recipesapp.view.fragments.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.elina.recipesapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [NearMeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NearMeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_near_me, container, false)
    }
}