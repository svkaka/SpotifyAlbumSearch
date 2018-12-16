package com.ovrbach.common.response

class TokenResponse(
    private var access_token: String,
    private var token_type: String,
    private var expires_in: String
) {

    fun apifyToken(): String {
        return "$token_type $access_token"
    }

}