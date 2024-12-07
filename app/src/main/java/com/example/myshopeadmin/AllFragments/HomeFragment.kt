package com.example.myshopeadmin.AllFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.myshopeadmin.AllAdapter.StatusAdapter
import com.example.myshopeadmin.AllAdapter.WomenProductsAdapter
import com.example.myshopeadmin.AllDataModel.StatusDataModel
import com.example.myshopeadmin.AllDataModel.womenProductsDataModel
import com.example.myshopeadmin.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    lateinit var statusRecyclerView: RecyclerView
    lateinit var secondRecyclerView: RecyclerView
    var statusList = ArrayList<StatusDataModel>()
    lateinit var statusAdapter: StatusAdapter
    lateinit var db: FirebaseDatabase
    lateinit var womenProductsAdapter: WomenProductsAdapter
    var womenProductsList = ArrayList<womenProductsDataModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        statusRecyclerView = view.findViewById(R.id.recyclerView)
        statusRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        db = FirebaseDatabase.getInstance()

        statusAdapter = StatusAdapter(statusList)
        statusRecyclerView.adapter = statusAdapter

        statusGetData()

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.slider, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.splash3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.fashion, ScaleTypes.FIT))
        imageSlider.setImageList(imageList)

        secondRecyclerView = view.findViewById(R.id.secondRecyclerView)
        secondRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        womenProductsAdapter=WomenProductsAdapter(womenProductsList)
        secondRecyclerView.adapter=womenProductsAdapter
                womenProductsGetData()

        return view
    }

    fun statusGetData() {

        db.getReference("users").child("status").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                statusList.clear()
                for (snap in snapshot.children) {
                    val status = snap.getValue(StatusDataModel::class.java)
                    statusList.add(status!!)
                }
                statusAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun womenProductsGetData(){
        db.getReference("users").child("womenProducts").addValueEventListener(object :ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                womenProductsList.clear()
                for (snap in snapshot.children){
                    val womenProducts=snap.getValue(womenProductsDataModel::class.java)
                    womenProductsList.add(womenProducts!!)
                }
                womenProductsAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}