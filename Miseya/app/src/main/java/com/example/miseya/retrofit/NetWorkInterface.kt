package com.example.miseya.retrofit

import com.example.miseya.Dust
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("getCtprvnRltmMesureDnsty") //시도별 실시간 측정 정보 조회 주소 - 요청 주소 맨 마지막 부분
    suspend fun getDust(@QueryMap param: HashMap<String, String>): Dust
    //String 안에는 요청 값이 들어감 (KEY 값이랑 VALUE)
}