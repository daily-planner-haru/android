package com.haru.android.login.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.haru.android.databinding.ActivityLoginBinding
import com.haru.android.login.viewmodel.KakaoViewModel
import com.haru.android.login.viewmodel.NaverViewModel

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
        kakaoViewModel.login(this)
    }
    private fun loginWithNaver(){
        naverViewModel.login()
    }

}