package com.example.location

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

// OnMapReadyCallback이 추가 됐을 경우 override fun onMapReady가 들어가야됨.
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private  lateinit var googleMap: GoogleMap

    //위치 서비스가 gps를 사용해서 위치를 확인
    lateinit var fusedLocationClient: FusedLocationProviderClient

    //위치 값 요청에 대한 갱신 정보를 받는 변수
    lateinit var locationCallback: LocationCallback

    lateinit var locationPermission: ActivityResultLauncher<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { results ->
            // 모든 Permission을 가지고 있을 경우
            if (results.all{it.value}){
                //맵 뷰에 연결시켜서 onMapReady가 자동적으로 실행이 됨
            (supportFragmentManager.findFragmentById(R.id.mapView)as SupportMapFragment)!!.getMapAsync(this)
        } else { //문제가 발생했을 때
            Toast.makeText(this, "권한 승인이 필요합니다.", Toast.LENGTH_LONG).show()
        }
        }

        //권한 요청
        locationPermission.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    // 지도 객체를 이용할 수 있는 상황이 될 때
    override fun onMapReady(p0: GoogleMap) {

        val seoul = LatLng(37.566610, 126.978403)
        googleMap = p0
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL // default 노말 생략 가능
        googleMap.apply {
            val markerOptions = MarkerOptions()
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            markerOptions.position(seoul)
            markerOptions.title("서울시청")
            markerOptions.snippet("Tel:01-120")
            addMarker(markerOptions)
        }

        //위치 정보를 받기 위함
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        updateLocation()
    }

    //위치 받아 오기
    fun updateLocation(){

        val locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 500
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object :LocationCallback() {
            //1초에 한 번씩 변경된 위치 정보가 onLocationResult로 전달된다.
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult?.let{
                    for (location in it.locations){
                        Log.d("위치정보", "위도: ${location.latitude} 경도: ${location.longitude}")
                        setLastLocation(location) //계속 실시간으로 위치를 받아오고 있기 때문에 맵을 확대해도 다시 줄어든다.
                    }
                }
            }
        }

        // 권한 처리
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,
        Looper.myLooper()!!
        )
    }

    fun setLastLocation(lastLocation: Location){
        val LATLNG = LatLng(lastLocation.latitude, lastLocation.longitude)
        val makerOptions = MarkerOptions().position(LATLNG).title("I'M HERE!")
        val cameraPosition = CameraPosition.Builder().target(LATLNG).zoom(15.0f).build()

        googleMap.addMarker(makerOptions)
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}