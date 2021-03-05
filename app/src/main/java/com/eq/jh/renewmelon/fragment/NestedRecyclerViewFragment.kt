package com.eq.jh.renewmelon.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.ext.enforceSingleScrollDirection
import java.util.*
import kotlin.collections.ArrayList


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 * 중복 리사이클러 뷰의 포커스 유지
 */
data class DataSet(
    //we will need an ID later on
    val id: String = UUID.randomUUID().toString(),
    val sectionTitle: String,
    val animals: ArrayList<Animal>
)

data class Animal(val name: String, val image: String)

@SuppressLint("LongLogTag")
class NestedRecyclerViewFragment : BaseFragment() {
    companion object {
        private const val TAG = "NestedRecyclerViewFragment"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var localAdapter: LocalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localAdapter = LocalAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nested_recyclerview, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = localAdapter
            setHasFixedSize(true)
            enforceSingleScrollDirection()
        }

        val list = mutableListOf<DataSet>()
        var animal = arrayListOf(
            Animal("Cat0_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat0_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat0_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat0_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        var dataSet = DataSet("0", "첫번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat1_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat1_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat1_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat1_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("1", "두번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat2_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat2_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat2_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat2_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("2", "세번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat3_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat3_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat3_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat3_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("3", "네번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat4_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat4_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat4_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat4_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("4", "다섯번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat5_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat5_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat5_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat5_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("5", "여섯번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat6_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat6_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat6_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat6_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("6", "일곱번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat7_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat7_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat7_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat7_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("7", "여덟번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat8_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat8_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat8_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat8_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("8", "아홉번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat9_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat9_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat9_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat9_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("9", "열번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat10_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat10_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat10_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat10_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("10", "열한번째 타이틀", animal)
        list.add(dataSet)

        animal = arrayListOf(
            Animal("Cat11_0", "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"),
            Animal(
                "Cat11_1",
                "https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=42132184edabf489cb379824f3da6f61"
            ),
            Animal(
                "Cat11_2",
                "https://static.scientificamerican.com/sciam/cache/file/32665E6F-8D90-4567-9769D59E11DB7F26_source.jpg?w=590&h=800&7E4B4CAD-CAE1-4726-93D6A160C2B068B2"
            ),
            Animal(
                "Cat11_3",
                "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
            )
        )
        dataSet = DataSet("11", "열두번째 타이틀", animal)
        list.add(dataSet)

        localAdapter.items = list
        localAdapter.notifyDataSetChanged()
    }

    inner class LocalAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()
        private val viewPool = RecyclerView.RecycledViewPool()
        private fun getSectionID(position: Int) = items[position].id

        override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
            super.onViewRecycled(holder)
            val vh = holder as ItemViewHolder
            //save horizontal scroll state
            val key = getSectionID(holder.layoutPosition)
            scrollStates[key] = vh.recyclerView.layoutManager?.onSaveInstanceState()
        }

        private val viewTypeItem = 1

        var items = mutableListOf<DataSet>()
            set(value) {
                field.clear()
                field = value
            }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemViewType(position: Int): Int {
            return viewTypeItem
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_nested, parent, false)
            )
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            when (viewHolder.itemViewType) {
                viewTypeItem -> {
                    val vh = viewHolder as ItemViewHolder
                    val data = items[position]

                    vh.sectionTitleTv.text = data.sectionTitle

                    vh.recyclerView.run {
                        setRecycledViewPool(viewPool)
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = AnimalAdapter(data.animals)
                    }

                    //restore horizontal scroll state
                    val key = getSectionID(viewHolder.layoutPosition)
                    val state = scrollStates[key]
                    if (state != null) {
                        vh.recyclerView.layoutManager?.onRestoreInstanceState(state)
                    } else {
                        vh.recyclerView.layoutManager?.scrollToPosition(0)
                    }
                }
            }
        }

        private inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val sectionTitleTv: TextView = view.findViewById(R.id.sectionTitleTv)
            val recyclerView: RecyclerView = view.findViewById(R.id.section_recyclerview)
        }
    }

    private inner class AnimalAdapter(private val animalList: List<Animal> = emptyList()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun getItemCount(): Int {
            return animalList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val vh = holder as ItemViewHolder
            val data = animalList[position]

            Log.d(TAG, "name : ${data.name}")
            vh.titleTextView.text = data.name
            Glide.with(vh.imageView.context).load(data.image).into(vh.imageView)
        }

        private inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.imageView)
            val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        }
    }
}