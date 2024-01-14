package com.haru.android.login.DTO
/**
 * kakao token login & signup request DTO
 * only required kakao token (Oauth2)
 * **/
data class LoginWithKakaoRequest(
    val token: String,
    val kakao_account: KakaoAccount

)

data class KakaoAccount(
    val profile: Profile
)

data class Profile(
    val nickname: String
)