package com.example.idml

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class MessageAdapter(val context: Context,val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE=1
    val ITEM_SENT=2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      if (viewType==1){
          // inflate recive
          val view: View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
          return ReceiveViewHolder(view)

      }else{
          //inflite sent
          val view: View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
          return SentViewHolder(view)
      }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage=messageList[position]
        if(holder.javaClass==SentViewHolder::class.java){
            // do the stuff for sent view holder


             val viewHolder=holder as SentViewHolder  // type casting
            holder.sentMessage.text= currentMessage.message
        }else{
           // do the stuff for recive view holder
             val viewHolder=holder as ReceiveViewHolder
            holder.receiveMessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){

            return ITEM_SENT
        }
        else{

            return ITEM_RECEIVE
        }


    }

    override fun getItemCount(): Int {
       return messageList.size
    }
    // two view holder ,one for reciving and one for sending message

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
     val sentMessage=itemView.findViewById<TextView>(R.id.txt_sent_message)

    }
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveMessage=itemView.findViewById<TextView>(R.id.txt_Receive_message)
    }


}