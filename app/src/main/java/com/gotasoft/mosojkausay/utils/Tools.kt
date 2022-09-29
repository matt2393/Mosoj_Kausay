package com.gotasoft.mosojkausay.utils

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ConstantLocale")
object Tools {
    private val sm0 = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    private val sm1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val sm2 = SimpleDateFormat("EEE dd MMM yyyy", Locale.getDefault())
    private val sm3 = SimpleDateFormat("hh:mm aa - dd MMM yyyy", Locale.getDefault())

    fun formatDate(d: String): String {
        val aux = sm1.parse(d)
        return if(aux!=null) {
            sm2.format(aux)
        } else {
            d
        }
    }

    fun formatDateHour(d: String): String {
        val aux = sm0.parse(d)
        return if(aux!=null) {
            sm3.format(aux)
        } else {
            d
        }
    }


    fun getFCMToken(callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = FirebaseMessaging.getInstance().token.await()
                withContext(Dispatchers.Main) {
                    callback.invoke(token)
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    callback.invoke("")
                }
            }
        }
    }
}