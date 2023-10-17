package com.nikhiljain.blockiesgenerator.sample.common

object StringUtil {

    fun shortenAddressString(address: String?, limit: Int = 10): String {
        var finalStr = ""
        address?.let {
            finalStr = if (address.length > limit) {
                val subSequenceStart: String = address.substring(0, 6)
                val subSequenceEnd: String =
                    address.substring(address.length - 4, address.length)
                val value = "$subSequenceStart***$subSequenceEnd"
                value
            } else {
                address
            }
        }
        return finalStr
    }
}