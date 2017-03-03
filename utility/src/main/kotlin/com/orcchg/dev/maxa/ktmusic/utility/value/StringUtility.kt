package com.orcchg.dev.maxa.ktmusic.utility.value

import android.text.TextUtils
import timber.log.Timber
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class StringUtility {

    fun encodeLink(xlink: String, enc: String): String {
        var link = xlink.toLowerCase()
        if (isAsciiPrintable(link)) return link

        val index = link.indexOf("http")
        var prefix = ""
        if (index >= 0) {
            link = link.substring(index + 4)
            prefix = "http"
        }
        try {
            val output = prefix + URLEncoder.encode(link, enc)
            Timber.v("Output link: %s", output)
            return output
        } catch (e: UnsupportedEncodingException) {
            Timber.w(e, "Link wasn't encoded properly!")
        }

        return link
    }

    private fun isAsciiPrintable(str: String): Boolean {
        if (TextUtils.isEmpty(str)) return false

        val sz = str.length
        for (i in 0..sz - 1) {
            if (!isAsciiPrintable(str[i])) return false
        }
        return true
    }

    private fun isAsciiPrintable(ch: Char): Boolean {
        return ch.toInt() >= 32 && ch.toInt() < 127
    }
}
