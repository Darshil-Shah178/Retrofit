package com.example.retrofit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [secondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class secondFragment : Fragment(R.layout.fragment_second) {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var  viewModel: MainViewModel


    private val changeObserver =
        Observer<String> {
                value -> value?.let { viewModel.hitCount }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt("pos")

        val president = GlobalModel.presidents[position]

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.query.observe(viewLifecycleOwner, changeObserver)

        viewModel.queryName(president.name)
        Log.d("apiCall", viewModel.query.value.toString())
        Log.d("apiCall", viewModel.hitCount.value.toString())
        viewModel.hitCount.observe(
            viewLifecycleOwner,{
                val hitCountString = it.query.searchinfo.totalhits.toString()
                view.findViewById<TextView>(R.id.PresidentHits).text = "Hits: ${hitCountString}"
            }
        )

        Log.d("USR", "Fragment 2, president $position")
        view.findViewById<TextView>(R.id.PresidentName).text = "Name: " + president.name
        view.findViewById<TextView>(R.id.PresidentDescription).text = "Position: " + president.description
        view.findViewById<TextView>(R.id.PresidentStartdate).text = "Starting Year: " + president.start.toString()
        view.findViewById<TextView>(R.id.PresidentEnddate).text = "Ending Year: " + president.end.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment secondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            secondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}