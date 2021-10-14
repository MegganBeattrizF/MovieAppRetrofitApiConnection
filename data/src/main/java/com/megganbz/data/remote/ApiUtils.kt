package com.megganbz.data.remote

import okhttp3.internal.and
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


const val publicKey = "0c0f5aa7e78457217f6f1d1f0361bdfb"
private const val privateKey = "b2558048888842d12f182cbefa9fea13e961b12e"
private var timeStamp: String? = null

fun getHash(): String {
    return try {
        val timestamp = getTimeStamp()
        timeStamp = null
        val value: String = timestamp + privateKey + publicKey
        val md5Encoder: MessageDigest = MessageDigest.getInstance("MD5")
        val md5Bytes: ByteArray = md5Encoder.digest(value.toByteArray())
        val md5 = StringBuilder()
        for (i in md5Bytes.indices) {
            md5.append(Integer.toHexString(md5Bytes[i] and 0xFF or 0x100).substring(1, 3))
        }
        md5.toString()
    } catch (e: NoSuchAlgorithmException) {
        throw Throwable("Cannot generate the api key", e)
    }
}

fun getTimeStamp(): String {
    if (timeStamp == null) {
        timeStamp = System.currentTimeMillis().toString()
    }
    return timeStamp as String
}