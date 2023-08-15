package com.haru.android.login.repository

import android.content.Context
import android.util.Log
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

object NaverRepository {

    private val profileCallback:NidProfileCallback<NidProfileResponse> = object : NidProfileCallback<NidProfileResponse> {
        override fun onSuccess(result: NidProfileResponse) {
            //val userId = result.profile?.id
            // TODO: save login success data

        }
        override fun onFailure(httpStatus: Int, message: String) {
            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
            // TODO: handle & send error code
            Log.d("naver login", "errorCode: ${errorCode}\n" +
                    "errorDescription: $errorDescription")
        }
        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }
    private val oauthLoginCallback:OAuthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
            val naverToken = NaverIdLoginSDK.getAccessToken()
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()
            Log.d("naver login", "token: $naverToken")
            //로그인 유저 정보 가져오기
            NidOAuthLogin().callProfileApi(profileCallback)
        }
        override fun onFailure(httpStatus: Int, message: String) {
            //val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            //val errorDescription = NaverIdLoginSDK.getLastErrorDescription()

        }
        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }
    /**
     * 로그인
     * authenticate() 메서드를 이용한 로그인 */
    fun login(context: Context){
        //token access login
        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }
}