package ca.elina.recipesapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.elina.recipesapp.R
import ca.elina.recipesapp.databinding.FragmentAllRecipesBinding
import ca.elina.recipesapp.view.activities.AddUpdateRecipeActivity
import ca.elina.recipesapp.viewmodel.HomeViewModel

class AllRecipesFragment : Fragment() {

    private var _binding: FragmentAllRecipesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentAllRecipesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_recipes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) { // when "+" is clicked
            R.id.action_add_dish -> {
                startActivity(Intent(requireActivity(), AddUpdateRecipeActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}