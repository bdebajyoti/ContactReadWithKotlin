package com.example.debajyotibhattacharjee

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.childcontact.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contact_List.layoutManager=LinearLayoutManager(this)
        val button=findViewById<Button>(R.id.addContacts)
        button.setOnClickListener{
            val intent= Intent(this,AddContact::class.java)
            //this.finish()
            startActivity(intent)
        }
        PermiMethod()
    }

    private fun PermiMethod() {

        val contactList:MutableList<contactDataTransfer> = ArrayList()

        val contacts=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
        while(contacts?.moveToNext()!!){
            val name=contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number=contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val obj=contactDataTransfer()
            obj.name=name
            obj.number=number
            contactList.add(obj)
        }
        contact_List.adapter=ContactAdapter(contactList,this)
        contacts.close()
    }

    class ContactAdapter(items:List<contactDataTransfer>,ctx: Context):RecyclerView.Adapter<ContactAdapter.ViewHolder>(){
        private var list=items
        private var context =ctx
        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
            holder.name.text=list[position].name
            holder.name.text=list[position].number

        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ContactAdapter.ViewHolder {
           return ViewHolder(LayoutInflater.from(context).inflate(R.layout.childcontact,parent,false))
        }
        class ViewHolder(v: View): RecyclerView.ViewHolder(v){
            val name: TextView =v.cname!!
            val number:TextView=v.cnumber!!
        }
    }
}