package com.haru.android.login.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haru.android.login.DTO.KakaoAccount
import com.haru.android.login.DTO.LoginSuccessData
import com.haru.android.login.DTO.LoginWithKakaoRequest
import com.haru.android.login.DTO.LoginWithKakaoResponse
import com.haru.android.login.DTO.Profile
import com.haru.android.login.api.LoginRetrofit
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoViewModel : ViewModel() {
    lateinit var kakaoCallback: (OAuthToken?, Throwable?) -> Unit
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn
    private val _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int> = _statusCode

    fun init(){
        setKakaoCallback()
    }
    // TODO : get JWT from kakao
    fun login(context: Context){
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
                } else if (token != null) {
                    Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
        }
    }
    fun setKakaoCallback() {
        kakaoCallback = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Log.d("[카카오로그인]", "접근이 거부 됨(동의 취소)")
                    }

                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Log.d("[카카오로그인]", "유효하지 않은 앱")
                    }

                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Log.d("[카카오로그인]", "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    }

                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Log.d("[카카오로그인]", "요청 파라미터 오류")
                    }

                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Log.d("[카카오로그인]", "유효하지 않은 scope ID")
                    }

                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Log.d("[카카오로그인]", "설정이 올바르지 않음(android key hash)")
                    }

                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Log.d("[카카오로그인]", "서버 내부 에러")
                    }

                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Log.d("[카카오로그인]", "앱이 요청 권한이 없음")
                    }

                    else -> { // Unknown
                        Log.d("[카카오로그인]", error.toString());
                        Log.d("[카카오로그인]", "기타 에러")
                    }
                }
            } else if (token != null) {
                Log.d("[카카오로그인]", "로그인에 성공하였습니다.\n${token.accessToken}")
                UserApiClient.instance.accessTokenInfo { _, _ ->
                    UserApiClient.instance.me { user, _ ->
                        val userId = user?.id.toString()
                        val userNickname = user?.kakaoAccount?.profile?.nickname
                        if(userNickname != null) {
                            val profile = Profile(userNickname)
                            val kakaoAccount = KakaoAccount(profile)
                            performLogin(userId, kakaoAccount)
                        }
                    }
                }
            } else {
                Log.d("카카오로그인", "토큰==null error==null")
            }
        }
    }

    private fun performLogin(id: String, account: KakaoAccount) {
        val call = LoginRetrofit.apiService.login(LoginWithKakaoRequest(id, account))
        call.enqueue(object : Callback<LoginWithKakaoResponse> {
            override fun onResponse(
                call: Call<LoginWithKakaoResponse>,
                response: Response<LoginWithKakaoResponse>
            ) {
                val statusCode = response.code()
                _statusCode.value = statusCode
                if (response.isSuccessful && response.body() != null) {
                    val response  = response.body()!!
                    LoginSuccessData(
                        id = response.id,
                        nickname = response.nickname,
                        AccessToken = response.AccessToken
                    )
                    _isLoggedIn.value = true
                } else {
                    // 오류 처리
                    _isLoggedIn.value = false
                }
            }

            override fun onFailure(call: Call<LoginWithKakaoResponse>, t: Throwable) {
                // 네트워크 오류 처리
            }
        })
    }

}