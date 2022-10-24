package com.example.idml

import android.content.IntentSender

class Message {
    var message:String?=null
    var senderId: String?=null

    constructor(){} //default

    constructor(message: String?, senderId:String?){
        this.message=message
        this.senderId=senderId
    }
}