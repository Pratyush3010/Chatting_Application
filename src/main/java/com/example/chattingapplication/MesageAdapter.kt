package com.example.chattingapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class MesageAdapter(val context: Context, val messageList:ArrayList<Message>): RecyclerView.Adapter<ViewHolder>() {

    val ITEM_RECEIVE = 1
     val ITEM_SENT = 2

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       if (viewType == 1){
           // inflate receieve
           val view:View = LayoutInflater.from(context).inflate(R.layout.received,parent,false)
           return ReceieveViewHolder(view)

       }else{
           // inflate sent
           val view:View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
           return SentViewHolder(view)
       }

     }

     override fun getItemViewType(position: Int): Int {
         val currentMessage = messageList[position]

         if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
             return ITEM_SENT
         }else{
             return ITEM_RECEIVE
         }
     }
     override fun getItemCount(): Int {
      return messageList.size
     }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  currentMessage = messageList[position]
         if (holder.javaClass == SentViewHolder::class.java){
             // do the stuff for sent view holder
             val viewHolder = holder as SentViewHolder
             holder.sentMessage.text = currentMessage.message
         }
         else{
             // do the stuff for receive view holder
             val viewHolder = holder as ReceieveViewHolder
             holder.receiveMessage.text = currentMessage.message
         }
     }

     class SentViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
            val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
     }

     class ReceieveViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
         val receiveMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)
     }
 }