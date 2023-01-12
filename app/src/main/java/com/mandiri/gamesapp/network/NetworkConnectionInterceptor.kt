package com.mandiri.gamesapp.network

import android.content.Context
import android.net.ConnectivityManager
import com.mandiri.gamesapp.common.NetworkConstants
import com.mandiri.gamesapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectivityException()
        } else if (!isInternetAvailable()) {
            throw NoInternetException()
        }

        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(NetworkConstants.KEY, BuildConfig.API_KEY)
            .build()

        val requestBuilder = original.newBuilder().url(url)
        return chain.proceed(requestBuilder.build())
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return capabilities != null
        } else false
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            Socket().run {
                connect(
                    InetSocketAddress(
                        NetworkConstants.HOSTNAME_TESTER,
                        NetworkConstants.PORT_TESTER
                    ), NetworkConstants.TIMEOUT_TESTER
                )
                close()
            }
            true
        } catch (e: IOException) {
            false
        }
    }
}

class NoConnectivityException : IOException() {
    override val message: String
        get() = NetworkConstants.NO_CONNECTIVITY_MESSAGE
}

class NoInternetException : IOException() {
    override val message: String
        get() = NetworkConstants.NO_INTERNET_MESSAGE
}