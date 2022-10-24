package com.example.idml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageView
    private lateinit var messageAdapter:MessageAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var mDbref:DatabaseReference

    var receiverRoom:String?=null  // unique room for sender to messanger
    var senderRoom:String?=null   // for privecy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val name=intent.getStringExtra("name")
        val receiveruid =intent.getStringExtra("uid")
        val senderuid=FirebaseAuth.getInstance().currentUser?.uid
        mDbref=FirebaseDatabase.getInstance().getReference()

        senderRoom= receiveruid + senderuid
        receiverRoom=senderuid+receiveruid

        supportActionBar?.title=name

        chatRecyclerView= findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messageBox)
        sendButton=findViewById(R.id.sentButton)
        messageList=ArrayList()
        messageAdapter=MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

          //logic for adding data to recycler view
         mDbref.child("chats").child(senderRoom!!).child("messages")
             .addValueEventListener(object :ValueEventListener{
                 override fun onDataChange(snapshot: DataSnapshot) {

                   messageList.clear()   //clear array list

                    for(postSnapshot in snapshot.children){
                        // get all childern
                        val message=postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                     messageAdapter.notifyDataSetChanged()
                 }

                 override fun onCancelled(error: DatabaseError) {

                 }

             })

        // add message to database
      sendButton.setOnClickListener(){

        //on click message goto database afer that different user
            val message=messageBox.text.toString()
          val messageObject=Message(message,senderuid)

          mDbref.child("chats").child(senderRoom!!).child("messages").push()
              .setValue(messageObject).addOnSuccessListener {
                  mDbref.child("chats").child(receiverRoom!!).child("messages").push()
                      .setValue(messageObject)
              }
          messageBox.setText("")
      }

    }
}