package com.example.myshopeadmin.AllFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.myshopeadmin.R
import com.example.myshopeadmin.StatusProductUploadActivity
import com.example.myshopeadmin.WomenProductsUploadActivity


class UploadProductsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_uplode_products, container, false)

        val button=view.findViewById<ImageView>(R.id.uploadStatusProductsImageView)
        button.setOnClickListener {
            startActivity(Intent(requireContext(), StatusProductUploadActivity::class.java))
        }

        val womenProductsButton=view.findViewById<ImageView>(R.id.uploadProductsSecondListImageView)
        womenProductsButton.setOnClickListener {
            startActivity(Intent(requireContext(), WomenProductsUploadActivity::class.java))
        }

        return view
    }

}