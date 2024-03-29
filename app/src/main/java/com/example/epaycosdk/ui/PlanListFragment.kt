package com.example.epaycosdk.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.epayco.android.Epayco
import co.epayco.android.models.Authentication
import co.epayco.android.util.EpaycoCallback
import com.example.epaycosdk.PrincipalFragment
import com.example.epaycosdk.R
import com.example.epaycosdk.databinding.FragmentHomeBinding
import com.example.epaycosdk.ui.home.HomeViewModel
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlanListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanListFragment : PrincipalFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val epayco = Epayco(auth)
        val textViewBank: TextView = binding.textHome
        epayco.getPlanList(object : EpaycoCallback {
            @Throws(JSONException::class)
            override fun onSuccess(data: JSONObject) {
                System.out.println("Result: ")
                System.out.println(data)

                textViewBank.text = data.toString();
            }

            override fun onError(error: Exception) {
                System.out.println("Error")
                System.out.println(error)
                textViewBank.text = error.toString()
            }
        })

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}