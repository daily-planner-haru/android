package com.haru.android.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.haru.android.databinding.ActivityLoginBinding
import com.haru.android.login.viewmodel.KakaoViewModel
import com.haru.android.login.viewmodel.NaverViewModel
import com.haru.android.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var kakaoViewModel: KakaoViewModel
    private lateinit var naverViewModel: NaverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* pull request 잘 됐는지 확인 부탁드립니다 */
        /* init view model */
        // login
        initKakakoViewModel()
        initNaverViewModel()
        initGmailLoginButton()
        initKakaoLoginButton()
        initNaverLoginButton()

    }

    private fun initKakakoViewModel(){
        kakaoViewModel = ViewModelProvider(this)[KakaoViewModel::class.java]
        kakaoViewModel.init()
    }

    private fun initNaverViewModel(){
        naverViewModel = ViewModelProvider(this)[NaverViewModel::class.java]
    }
    private fun initGmailLoginButton() {
        binding.gmailLoginButton.setOnClickListener {  }
    }

    private fun initKakaoLoginButton() {
        binding.kakaoLoginButton.setOnClickListener { loginWithKakao() }
    }

    private fun initNaverLoginButton() {
        binding.naverLoginButton.setOnClickListener { loginWithNaver() }
    }
    private fun loginWithKakao(){
        kakaoViewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                // 로그인 성공 시 처리, 예: 메인 화면으로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // LoginActivity 종료
            } else {
                // 로그인 실패 시 처리
                kakaoViewModel.statusCode.value?.let { handleErrorCode(it) }
            }
        }
        kakaoViewModel.login(this)
    }
    private fun loginWithNaver(){
        naverViewModel.login(this)
    }
    fun handleErrorCode(statusCode: Int) {
        when (statusCode) {
            400 -> {
                // 잘못된 요청 처리
            }
            401 -> {
                // 인증 오류 처리
            }
            403 -> {
                // 접근 금지 오류 처리
            }
            404 -> {
                // 리소스를 찾을 수 없음 처리
            }
            // 기타 상태 코드에 대한 처리
            else -> {
                // 알 수 없는 오류 처리
            }
        }
    }

}