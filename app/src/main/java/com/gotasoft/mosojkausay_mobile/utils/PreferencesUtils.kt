package com.gotasoft.mosojkausay_mobile.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

const val DEFAULT_SERVER_URL = "http://127.0.0.1/"
private val PREF_KEY_SERVER_URL = stringPreferencesKey("PREF_KEY_SERVER_URL")
private val PREF_KEY_TOKEN = stringPreferencesKey("PREF_KEY_TOKEN")
private var PREF_KEY_AUTHORIZATION = stringPreferencesKey("PREF_KEY_AUTHORIZATION")

fun getServerUrl(context: Context): Flow<String> =
    getString(context = context, PREF_KEY_SERVER_URL).map {
        var url = it
        if(url == null) {
            url = DEFAULT_SERVER_URL
        }
        if(!url.endsWith("/")) {
            url += "/"
        }
        url
    }

suspend fun setServerUrl(context: Context, url: String) {
    setString(context = context, PREF_KEY_SERVER_URL, url)
}
fun getAuthorization(context: Context): Flow<String?> {
    return getString(context = context, PREF_KEY_AUTHORIZATION)
}
suspend fun setAuthorization(context: Context, authorization: String) {
    setString(context = context, PREF_KEY_AUTHORIZATION, authorization)
}


fun getToken(context: Context): Flow<String?> {
    return getString(context = context, PREF_KEY_TOKEN)
}
suspend fun setToken(context: Context, token: String) {
    setString(context = context, PREF_KEY_TOKEN, token)
}
suspend fun removeToken(context: Context) {
    setStringRemove(context = context)
}

private fun getString(context: Context, key: Preferences.Key<String>): Flow<String?> =
    context.dataStore.data.map {
        it[key]
    }
private suspend fun setString(context: Context, key: Preferences.Key<String>, value: String) {
    context.dataStore.edit {
        it[key] = value
    }
}
private suspend fun setStringRemove(context: Context) {
    context.dataStore.edit {
        it.clear()
    }
}

private fun getInt(context: Context, key: Preferences.Key<Int>): Flow<Int?> =
    context.dataStore.data.map {
        it[key]
    }

private suspend fun setInt(context: Context, key: Preferences.Key<Int>, value: Int) {
    context.dataStore.edit {
        it[key] = value
    }
}

private fun getLong(context: Context, key: Preferences.Key<Long>): Flow<Long?> =
    context.dataStore.data.map {
        it[key]
    }

private suspend fun setLong(context: Context, key: Preferences.Key<Long>, value: Long) {
    context.dataStore.edit {
        it[key] = value
    }
}

private fun getFloat(context: Context, key: Preferences.Key<Float>): Flow<Float?> =
    context.dataStore.data.map {
        it[key]
    }

private suspend fun setFloat(context: Context, key: Preferences.Key<Float>, value: Float) {
    context.dataStore.edit {
        it[key] = value
    }
}

private fun getDouble(context: Context, key: Preferences.Key<Double>): Flow<Double?> =
    context.dataStore.data.map {
        it[key]
    }

private suspend fun setDouble(context: Context, key: Preferences.Key<Double>, value: Double) {
    context.dataStore.edit {
        it[key] = value
    }
}

private fun getBoolean(context: Context, key: Preferences.Key<Boolean>): Flow<Boolean?> =
    context.dataStore.data.map {
        it[key]
    }

private suspend fun setBoolean(context: Context, key: Preferences.Key<Boolean>, value: Boolean) {
    context.dataStore.edit {
        it[key] = value
    }
}