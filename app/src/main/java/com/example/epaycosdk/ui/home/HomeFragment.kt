package com.example.epaycosdk.ui.home

import android.os.Bundle
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
import com.example.epaycosdk.databinding.FragmentHomeBinding
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class HomeFragment : PrincipalFragment() {

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
        textViewBank.text = "Cargando .... ";
        epayco.getBanks(object : EpaycoCallback {
            @Throws(JSONException::class)
            override fun onSuccess(data: JSONObject) {
                System.out.println("Result: ")
                System.out.println(data)
                textViewBank.text = data.toString();
            }

            override fun onError(error: Exception) {
                System.out.println("Error")
                System.out.println(error)
                textViewBank.text = error.toString();
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