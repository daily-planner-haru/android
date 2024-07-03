package com.haru.android

import android.app.Application
import com.haru.android.Dailiy.DTO.Event
import com.haru.android.mockup.MockEventQueue
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // kakako
        KakaoSdk.init(this, getString(R.string.kakaoNativeKey))
        // naver
        NaverIdLoginSDK.initialize(this.applicationContext, getString(R.string.naverClinetID),getString(R.string.naverSecretKey) ,getString(R.string.naverAppName))

        MockEventQueue
            .pushEvent(
                Event(1, "대철이 때리기", "늦은 대철이 때리기", "대철이네 집", false, "약속")
            )
        MockEventQueue
            .pushEvent(
                Event(2, "하루 정기 투자 시간", "매주 수요일 8시", "재택", false, "일")
            )
        MockEventQueue
            .pushEvent(
                Event(3, "새우 요리 해먹기", "냉동실 새우살 짬처리", "집", false, "약속")
            )
        MockEventQueue
            .pushEvent(
                Event(4, "출근", "주중 9시 30분까지", "회사", false, "일")
            )
        MockEventQueue
            .pushEvent(
                Event(5, "퇴근", "주중 5시 30분", "회사", false, "일")
            )
        // init Mock
//        var keyHash = Utility.getKeyHash(this)
//        Log.e("key", keyHash);
    }


}