package com.haru.android.login.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.haru.android.databinding.ActivityLoginBinding
import com.haru.android.login.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* pull request 잘 됐는지 확인 부탁드립니다 */


        initGmailLoginButton()
        initKakaoLoginButton()
        initNaverLoginButton()

    }

    private fun initGmailLoginButton() {
        binding.gmailLoginButton.setOnClickListener {  }
    }

    private fun initKakaoLoginButton() {
        binding.kakaoLoginButton.setOnClickListener {  }
    }

    private fun initNaverLoginButton() {
        binding.naverLoginButton.setOnClickListener {  }
    }

}