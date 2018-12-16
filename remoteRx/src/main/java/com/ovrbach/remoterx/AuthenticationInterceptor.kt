package com.ovrbach.remoterx

import com.ovrbach.common.response.TokenResponse
import com.squareup.moshi.Moshi
import okhttp3.*
import java.net.HttpURLConnection
import java.net.URL
import java.io.*
import java.lang.IllegalStateException

const val SPOTIFY_AUTH_URL = "https://accounts.spotify.com/api/token/"

class AuthenticationInterceptor : Interceptor {

    var authToken: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        if (authToken == null) {
            refreshToken()
        }

        if (authToken==null) throw IllegalStateException("Please put secret key to\nServiceGenerator.DEFINITELY_NOT_SECRET")
        val builder = original.newBuilder()
            //fail early
            .header("Authorization", authToken)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
        val request = builder.build()
        return chain.proceed(request)
    }


    private fun refreshToken() {
        val connection = buildConnection()

        val wr = DataOutputStream(connection.outputStream)
        wr.flush()
        wr.close()

        if (connection.responseCode == 200) {
            val response = readResponse(connection.inputStream)
            val token = parseTokenFromResponse(response)
            authToken = token.apifyToken()
        } else {

            //we are fucked
        }
    }

    private fun readResponse(inputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuffer()

        var inputLine: String? = reader.readLine()
        while (inputLine != null) {
            response.append(inputLine)
            inputLine = reader.readLine()
        }
        inputStream.close()
        return response.toString()
    }

    private fun buildConnection(): HttpURLConnection {
        return (URL("$SPOTIFY_AUTH_URL?grant_type=client_credentials")
            .openConnection() as HttpURLConnection)
            .apply {
                requestMethod = "POST"
                addRequestProperty(
                    "access_token", "application/x-www-form-urlencoded"
                )
                addRequestProperty(
                    "Authorization",
                    Credentials.basic(ABSOLUTELY_NOT_CLIENT, DEFINITELY_NOT_SECRET)
                )
                println("$DEFINITELY_NOT_SECRET << $ABSOLUTELY_NOT_CLIENT")
                useCaches = false

                doInput = true
                doOutput = true
            }
    }

    private fun parseTokenFromResponse(response: String): TokenResponse {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(TokenResponse::class.java)
        return adapter.fromJson(response)
    }

}
