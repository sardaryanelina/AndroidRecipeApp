package ca.elina.recipesapp.view.fragments.map

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import ca.elina.recipesapp.R
import ca.elina.recipesapp.databinding.FragmentNearMeBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.TravelMode
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class NearMeFragment : Fragment() {

    private var _binding: FragmentNearMeBinding? = null
    private val binding get() = _binding!!

    private var currentPosition: LatLng? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNearMeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserCurrentLocation()
    }

    private fun getUserCurrentLocation() {
        if (!isLocationEnable()) {
            Toast.makeText(
                context, "Your location provider is turned off. Please turn on",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        } else {
            Dexter.withContext(context)
                .withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            Toast.makeText(
                                context,
                                "Location permission is granted. Now you can get current location",
                                Toast.LENGTH_SHORT
                            ).show()
                            requestNewLocationData()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        list: List<PermissionRequest>,
                        permissionToken: PermissionToken
                    ) {
                        showRationalDialogForPermissions()
                    }
                }).onSameThread().check()
        }
    }

    private fun requestNewLocationData() {
        val locationCallBack: LocationCallback
        val fusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        val locationRequest: LocationRequest = LocationRequest.Builder(10)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMaxUpdates(1)
            .build()
        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val lastLocation = locationResult.lastLocation
                val currentLat = lastLocation!!.latitude
                Log.d("Latitude", "" + currentLat)
                val currentLong = lastLocation.longitude
                Log.d("Longitude", "" + currentLong)
                currentPosition = LatLng(currentLat, currentLong)
                callback =
                    OnMapReadyCallback { googleMap ->
                        googleMap.addMarker(MarkerOptions().position(currentPosition!!).title("You are here"))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition!!))

                        val newLatLongZoom = CameraUpdateFactory.newLatLngZoom(currentPosition!!, 14f)
                        googleMap.animateCamera(newLatLongZoom)}

                val mapsFragment =
                    childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
                mapsFragment?.getMapAsync(callback)

                super.onLocationResult(locationResult)
            }
        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper()
        )
    }

    private fun showRationalDialogForPermissions() {
        val alertDialogBuilder = AlertDialog.Builder(
            requireContext()
        )
            .setTitle("Location Access Permission Is Required")
            .setMessage("Enable location access in Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (exception: ActivityNotFoundException) {
                    exception.printStackTrace()
                }
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, _: Int ->
                // this method is called when user click on negative button.
                dialog.cancel()
            }
        alertDialogBuilder.show()
    }

    private fun isLocationEnable(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    var callback =
        OnMapReadyCallback { googleMap ->
            googleMap.addMarker(MarkerOptions().position(currentPosition!!).title("You are here"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition!!))

            val newLatLongZoom = CameraUpdateFactory.newLatLngZoom(currentPosition!!, 14f)
            googleMap.animateCamera(newLatLongZoom)
            googleMap.setOnMarkerClickListener(object : OnMarkerClickListener {
                override fun onMarkerClick(marker: Marker): Boolean {
                    val location = marker.tag as LatLng?
                    drawRoute(location)
                    return true
                }

                private fun drawRoute(location: LatLng?) {
                    try {
                        // get the directions between the two locations using the Google Maps Directions API
                        val geoApiContext = GeoApiContext.Builder()
                            .apiKey(getString(R.string.google_maps_key))
                            .build()
                        val directionsResult = DirectionsApi.newRequest(geoApiContext)
                            .mode(TravelMode.DRIVING)
                            .origin(
                                com.google.maps.model.LatLng(
                                    currentPosition!!.latitude,
                                    currentPosition!!.longitude
                                )
                            )
                            .destination(
                                com.google.maps.model.LatLng(
                                    location!!.latitude,
                                    location.longitude
                                )
                            )
                            .await()

                        // extract the route polyline from the DirectionsResult and add it to the map
                        val directionsRoute = directionsResult.routes[0]
                        val polylineOptions = PolylineOptions()
                        for (latLng in directionsRoute.overviewPolyline.decodePath()) {
                            polylineOptions.add(LatLng(latLng.lat, latLng.lng))
                        }
                        polylineOptions.width(10f).color(R.color.green);

                        // Invoke .shutdown() after your application is done making requests
                        geoApiContext.shutdown()
                    } catch (e: Exception) {
                    }
                }
            })
        }
}