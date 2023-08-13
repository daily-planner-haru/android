package com.haru.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // kakako
        KakaoSdk.init(this, getString(R.string.kakaoNativeKey))
        // naver
        NaverIdLoginSDK.initialize(this.applicationContext, getString(R.string.naverClinetID),getString(R.string.naverSecretKey) ,getString(R.string.naverAppName))
    }
}