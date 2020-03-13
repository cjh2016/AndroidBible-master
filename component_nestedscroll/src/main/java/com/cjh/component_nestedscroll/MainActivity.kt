package com.cjh.component_nestedscroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.leakcanary.LeakTraceElement
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)

            adapter = object: RecyclerView.Adapter<Holder>() {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                    val item = LayoutInflater.from(this@MainActivity).inflate(
                        R.layout.recyclerview_item, parent, false)
                    return Holder(item)
                }

                override fun onBindViewHolder(holder: Holder, position: Int) {
                    holder.textView.text = "item $position"
                }

                override fun getItemCount(): Int {
                    return 100
                }

            }
        }
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textView: TextView = itemView.findViewById(R.id.text)

    }


}
