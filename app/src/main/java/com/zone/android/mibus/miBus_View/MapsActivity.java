package com.zone.android.mibus.miBus_View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.zone.android.mibus.GeoApiContext;
import com.zone.android.mibus.R;
import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;
import com.zone.android.mibus.miBus_Presenter.mapPresClass;
import com.zone.android.mibus.miBus_Presenter.mapPresInterface;
import com.zone.android.mibus.miBus_Utility.Constants;
import com.zone.android.mibus.miBus_Utility.ContentFragment;
import com.zone.android.mibus.miBus_Utility.DirectionsJSONParser;
import com.zone.android.mibus.miBus_Utility.Methods;
import com.zone.android.mibus.miBus_Utility.updateServices;

import org.angmarch.views.NiceSpinner;
import org.json.JSONObject;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends AppCompatActivity implements mapViewInterface, OnMapReadyCallback {

    private GoogleMap mMap;

    private LatLng curBusPosition;
    private int index, next;
    private LatLng startPosition, endPosition;
    private float v;
    private double lat, lng;
    TextView textcurrPlace,prevVal,nextVal;

    private RadioGroup radioGroup;

    boolean isBusLocCalled ;

    boolean isBusMode,isRouteMode;


    boolean isBuscalledNew=false;

    SharedPreferences  loginPreference;
    SharedPreferences routePreferences,isRouteSelectedPreference,lastIndexPreference;
    SharedPreferences studentPreference,nextRoutePreference;
    NiceSpinner spinner1;

    RelativeLayout relRoute;

    public static int routePointNumber;




    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    static String route;

    static List<RoutePoint> routePointsStatic;

    static List<RoutePoint> routePointsList;


    private static final String TAG = MapsActivity.class.getSimpleName();
    public static ProgressDialog progressBar;


    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private Location mLastKnownLocation;
    private CameraPosition mCameraPosition;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 18;
    public static final int RequestPermissionCode = 1;

    //initializing request object
    LocationRequest mLocationRequest = new LocationRequest();
    protected Boolean mRequestingLocationUpdates;

    private LocationCallback mLocationCallback;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";


    //REQUEST_CHECK_SETTINGS
    private static final int REQUEST_CHECK_SETTINGS = 2;
    Marker marker_device, marker_routes,marker_bus,marker_all,marker_specific,marker_previous;
    List<Address> addresses;

    ArrayList<LatLng> markerPoints;
    private List<LatLng> polyLineList;
    private PolylineOptions polylineOptions, blackPolylineOptions;
    private Polyline blackPolyline, greyPolyLine;

    IntentFilter statusIntentFilter,locationchangeFilter;

    // Instantiates a new DownloadStateReceiver

    //intiating intent request

    Intent mServiceIntent;

    SharedPreferences locationPreferences,updatePreferences;
    Toolbar toolbar;
    ActionBar actionBar;
    private Handler handler =new Handler();


    TextView txtLat,txtLon, driverName;
    Button destination_button;
    EditText edittext_place;
    private String destination;
    Double finalPosLat,finalPosLong;

    String radioText;

    boolean onRoute=false;
    Runnable runnable;

    Geocoder geocoder;
    Timer timer;
    TimerTask timerTask;

    final Handler handler1 = new Handler();

    TextView btn_stop,textdate;

    boolean isFromButtonStop=false;


    //yalantis side menu

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music;
    private LinearLayout linearLayout;


    TextView routeVal;

    mapPresInterface mapPresInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }


        setContentView(R.layout.activity_mapbus);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        isBusMode=true;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

      /*  actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.homenew);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Inbox");*/

        getSupportActionBar().setTitle("Tracking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //getting yalatis side menu details

        routeVal=(TextView)findViewById(R.id.routeVal);
        relRoute=(RelativeLayout)findViewById(R.id.relRoute);
        driverName=(TextView)findViewById(R.id.driverName);

        nextVal=(TextView)findViewById(R.id.nextVal);
        prevVal=(TextView)findViewById(R.id.prevVal);

        contentFragment = ContentFragment.newInstance(R.drawable.content_music);
       /* getSupportFragmentManager().beginTransaction()
                .replace(R.id.sliding_layout, contentFragment)
                .commit();
*/
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        timer = new Timer();

        drawerLayout.setScrimColor(Color.TRANSPARENT);

        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

      //  setActionBar();
       // createMenuList();

//////////////////////////////ending yalantis menu here


        btn_stop=(TextView) findViewById(R.id.btn_stop);

       // textdate=(TextView)findViewById(R.id.textdate);
      //  textcurrPlace=(TextView)findViewById(R.id.textcurrPlace);

        routePreferences=getApplicationContext().getSharedPreferences(Constants.ROUTE_SEL_PREFERENCE, Context.MODE_PRIVATE);
        isRouteSelectedPreference=getApplicationContext().getSharedPreferences(Constants.IS_ROUTE_SEL, Context.MODE_PRIVATE);
        loginPreference = getApplicationContext().getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);

        lastIndexPreference=getApplicationContext().getSharedPreferences(Constants.LAST_INDEX, Context.MODE_PRIVATE);

        nextRoutePreference=getApplicationContext().getSharedPreferences(Constants.NEXT_ROUTE_PREFERENCE, Context.MODE_PRIVATE);


        String routeName=routePreferences.getString("routeName","");

        routeVal.setText("Route: "+routeName);

        mapPresInterface=new mapPresClass(this);

        geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());

        locationPreferences =getApplicationContext().getSharedPreferences(Constants.LOCATION_PREFERENCE,0);

        updatePreferences =getApplicationContext().getSharedPreferences("update_location_preference",0);

        studentPreference= getApplicationContext().getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);


        String studentName=studentPreference.getString("studentname","");

        String upperString = studentName.substring(0,1).toUpperCase() + studentName.substring(1);


        driverName.setText(upperString);

        double lastRouteEndLat,lastRouteEndLong;

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mRequestingLocationUpdates = false;
        markerPoints=new ArrayList<LatLng>();

        locationchangeFilter = new IntentFilter(BROADCAST_ACTION);



        getLocationPermission();

        getmLocationCallback();

        //   Methods.copyFile(getApplicationContext());

        mapPresInterface.getRoutePoints(getApplicationContext());

        int lastInt=lastIndexPreference.getInt("lastindex",0);


        mapPresInterface.getIndexValues(getApplicationContext(),lastInt);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //this is commented *impppppppp*
        mapFragment.getMapAsync(this);
        mapFragment.getView().setClickable(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton rb = (RadioButton) radioGroup.findViewById(i);

                if (null != rb && i>-1){


                   if(i==R.id.radioBus){
                       isBusMode=true;
                       isRouteMode=false;


                       getDeviceLocation(mLastKnownLocation);

                   }else if(i==R.id.radioRoute){
                       //get route location
                       isBusMode=false;
                       isRouteMode=true;

                       getRouteLocation();
                   }


                }


            }
        });





        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLocaUpdate();


            }
        });




        relRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapPresInterface.getRoutes(getApplicationContext());
            }
        });


       // getBusLocation();
    }




    public void startLocaUpdate(){



        if(mLocationPermissionGranted) {

            if (!isBuscalledNew) {

                isBuscalledNew = true;
                startTimer();
            }

            int lastInt = lastIndexPreference.getInt("lastindex", 0);

            Log.e("routePointNumberxxx ","routePointNumberxxx "+routePointNumber);

            if (lastInt < routePointNumber - 1) {

                SharedPreferences.Editor editor = lastIndexPreference.edit();
                editor.putInt("lastindex", lastInt + 1);
                editor.commit();

                int lastInt1 = lastIndexPreference.getInt("lastindex", 0);

                mapPresInterface.getIndexValues(getApplicationContext(), lastInt1);

            } else {
                Toast.makeText(getApplicationContext(), "Destin reched", Toast.LENGTH_SHORT).show();
            }

        }else{

            Toast.makeText(getApplicationContext(), "Enable location services", Toast.LENGTH_SHORT).show();
            getLocationPermission();
        }

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                finalPosLat=latLng.latitude;
                finalPosLong=latLng.longitude;

                Log.e("finalPosLat","finalPosLat "+finalPosLat+" finalPosLong "+finalPosLong);

            }
        });


        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/

        //  updateLocationUI();


        getLastKnownLocation();
        createLocationRequest();

    }



    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;


        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e("grantresults "," grantResults "+grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    //  updateLocationUI();
                    getLastKnownLocation();
                    createLocationRequest();


                    //  getmLocationCallback();
                // createLocationRequest();

                }

                else{
                    Toast.makeText(MapsActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                }
            }
        }
        //  updateLocationUI();
    }



    private void getmLocationCallback(){

        Log.e("mcallback","mcallbckmLocationPermissionGranted "+mLocationPermissionGranted);
        ////fgfgfg
    //    if(mLocationPermissionGranted) {
            mLocationCallback = new LocationCallback() {

                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);

                    for (Location location : locationResult.getLocations()) {

                        Log.e("mcallback","mcallbck "+location.getLatitude() +" "+location.getLongitude());

                        double currentLatitude = location.getLatitude();
                        double currentLongitude = location.getLongitude();



                        if (location != null) {
                            mLastKnownLocation=location;

                            getDeviceLocation(location);


                        } else {


                            getDeviceLocation(mLastKnownLocation);

                        }


                    }

                }
            };

       // }
        ///////////////////
    }

    void setLocation(){


        double deviceLat= mLastKnownLocation.getLatitude();
        double deviceLong = mLastKnownLocation.getLongitude();

        final String dir;

        String route=routePreferences.getString("routeName","");

        String url="https://identity.01.via.zone/Services/BusApp/Json/UpdatePositionWithToken/0001/"+route+"/"+
                lat+"/"+lng+"/"+deviceLat+"/"+deviceLong+"/"+deviceLat+"/"+deviceLong+"/";

        SharedPreferences.Editor editor = updatePreferences.edit();
        editor.putString("url", url); // Storing string
        editor.commit();



    }



    private void getDeviceLocation( Location location){
        if(mLocationPermissionGranted){
            if(location!=null){


                double deviceLat= location.getLatitude();
                double deviceLong = location.getLongitude();

                Log.e("loclat ","loclatpreference "+deviceLat+" "+deviceLong);


                curBusPosition=new LatLng(deviceLat,deviceLong);



                if(marker_device !=null){
                    marker_device.remove();
                }


              Log.e("isBusMode ","isBusMode "+isBusMode);

                if(isBusMode) {

                    final LatLng DESTINATION = new LatLng(deviceLat, deviceLong);

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(DESTINATION)      // Sets the center of the map to Mountain View
                            .zoom(15)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }


                MarkerOptions options = new MarkerOptions()
                        .position(new LatLng(deviceLat,
                                deviceLong))
                        // .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.busmarker))
                        // .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        .title("Bus!!!!");


                try {
                   SharedPreferences.Editor editor1 = locationPreferences.edit();
                   editor1.putString("latitude", String.valueOf(deviceLat));
                   editor1.putString("longitude", String.valueOf(deviceLong));
                   editor1.commit();

               }catch (Exception e){
                   e.printStackTrace();
               }

                marker_device = mMap.addMarker(options);
              //  marker_device.showInfoWindow();
            //    setLocation();

                try{
                    addresses = geocoder.getFromLocation(deviceLat, deviceLong, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5



                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); //

                  //  textcurrPlace.setText(city);


                }catch(Exception e){
                    e.printStackTrace();
                }



            }else{
                Toast.makeText(getApplicationContext(),"Bus details not available",Toast.LENGTH_SHORT).show();

            }

        }

    }


    private void getLastKnownLocation() {

        Log.e("locationpermission","locationpermission");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.

                        if (location != null) {
                            mLastKnownLocation =location;
                            Log.e("Latlonglastknown "," Latlonglastknown "+ location.getLatitude()+ " "+location.getLongitude());

                            getDeviceLocation(location);


                        }
                    }
                });

    }



    protected void createLocationRequest(){

        try {

            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);

            //this line is initializing thelocayionrequest object and checking if the client
            //enable location settigs on..

            SettingsClient client = LocationServices.getSettingsClient(this);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {



                    mRequestingLocationUpdates = true;

                    startLocationUpdates();



                }
            });


            task.addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    int statusCode = ((ApiException) e).getStatusCode();
                    Log.e("statuscode","statuscode "+statusCode);


                    switch (statusCode) {
                        case CommonStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied, but this can be fixed
                            // by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(MapsActivity.this,
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sendEx) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way
                            // to fix the settings so we won't show the dialog.
                            break;


                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("onActivityResult() ", Integer.toString(resultCode)+ "onActivityResult() ");
        Log.e("onActivityResult() ", Integer.toString(resultCode) + " " + Integer.toString(requestCode));

        switch (requestCode) {

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK: {
                        Log.e("onActivitRESULT_OK()", Integer.toString(resultCode));
                        // All required changes were successfully made
                        Toast.makeText(MapsActivity.this, "Location enabled by user!", Toast.LENGTH_LONG).show();

                        mRequestingLocationUpdates = true;

                        startLocationUpdates();

                        break;
                    }
                    case RESULT_CANCELED: {
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(MapsActivity.this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;

        }

    }

    private void startLocationUpdates() {
        if (mLocationPermissionGranted) {

            try {
                mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                        mLocationCallback,
                        null

                );
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

    }



    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }


    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        geoApiContext.setApiKey("AIzaSyBiEZLnEvwWt_s5cySZHTstjGaSiXlITnY");
        geoApiContext.setQueryRateLimit(3);
        geoApiContext.setConnectTimeout(1);
        geoApiContext.setReadTimeout(1);
        geoApiContext.setWriteTimeout(1);

        return  geoApiContext;

    }

    private  void exitRouting(){
        onRoute=false;
        mMap.clear();
        getDeviceLocation(mLastKnownLocation);
        timer.purge();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    public void startTimer() {
        //set a new Timer

        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 0, 5000); //

    }

    public void stopTimer() {
        if (timer != null) {
            handler.removeCallbacks(runnable);
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }



    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                handler1.post(new Runnable() {
                    public void run() {

                        mServiceIntent=new Intent(MapsActivity.this, updateServices.class);
                        startService(mServiceIntent);
                    }

                });

            }

        };

    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);

        /*SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.SHOP, R.drawable.ic_account);
        list.add(menuItem5);

        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.ic_set);
        list.add(menuItem2);
     *//*   SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.PAINT, R.drawable.icn_3);
        list.add(menuItem3);*//*
*/
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.LOGOUT, R.drawable.icn_logout);
        list.add(menuItem);

        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.SWITCH_ROUTE, R.drawable.ic_ref);
        list.add(menuItem4);


    }




    @Override
    public void setRoutes(final List<Route> routeList, final Context context) {

        MapsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

               // changeRoute(routeList,context);
                Intent intent = new Intent(MapsActivity.this, routeActivity.class);
                //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
               // progressBar.dismiss();
                startActivity(intent);
            }
        });






    }

    @Override
    public void setRoutePoints(final List<RoutePoint> routePointList) {


     /*   String url = getDirectionsUrl(routePointList);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
*/

  MapsActivity.this.runOnUiThread(new Runnable() {
      @Override
      public void run() {

          routePointNumber=routePointList.size();

          routePointsList=routePointList;

          if(isRouteMode) {

              addMarkerPoints();
          }
      }
  });

    }

    @Override
    public void setIndexValues(final List<RoutePoint> routePointList) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                    routePointsStatic=routePointList;

                    int lastInt = lastIndexPreference.getInt("lastindex", 0);

                    int lastIndexNumber = 0;

                    Log.e("index number ","indexNumber "+lastInt+" "+" pointsNo "+routePointNumber);

                    /*if (routePointList.size() > 1) {

                        lastIndexNumber = routePointList.get(1).getRouteIndex();
                        prevVal.setText(routePointList.get(0).getRouteName().toString());
                        nextVal.setText(routePointList.get(1).getRouteName().toString());
                    } else {


                       //btn_stop.setText("START");
                        prevVal.setText("");
                        nextVal.setText(routePointList.get(0).getRouteName().toString());

                    }*/

                    if(lastInt<0){
                        btn_stop.setText("START");
                        prevVal.setText("");
                        nextVal.setText(routePointList.get(0).getRouteName().toString());

                    }else if(lastInt==routePointNumber-2){
                        prevVal.setText(routePointList.get(0).getRouteName().toString());
                        nextVal.setText(routePointList.get(1).getRouteName().toString());
                        btn_stop.setText("FINISH");
                    }else if(lastInt>=routePointNumber-1){
                        prevVal.setText("Desination reached");
                        nextVal.setText("Desination reached");
                        btn_stop.setText("DESTINATION");

                    } else{

                        prevVal.setText(routePointList.get(0).getRouteName().toString());
                        nextVal.setText(routePointList.get(1).getRouteName().toString());
                        btn_stop.setText("NEXT");


                    }



                    getRouteLocation();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }


    public  void addMarkerPoints() {

        try {

            ArrayList<LatLng> points = new ArrayList<LatLng>();



            //  LatLng destLatLong = new LatLng(destLat, destLong);

            int lastInt = lastIndexPreference.getInt("lastindex", 0);

            //lastInt>=routePointNumber-1



            Double nextLat = Double.parseDouble(nextRoutePreference.getString("latitude", routePointsList.get(0).getRouteLat().toString()));

            Double nextLon = Double.parseDouble(nextRoutePreference.getString("longitude", routePointsList.get(0).getRouteLong().toString()));

            LatLng nextLatLon = new LatLng(nextLat, nextLon);


            Double prevLat = Double.parseDouble(nextRoutePreference.getString("prevlat", routePointsList.get(0).getRouteLat().toString()));

            Double prevLon = Double.parseDouble(nextRoutePreference.getString("prevlong", routePointsList.get(0).getRouteLong().toString()));

            LatLng prevLatLon = new LatLng(prevLat, prevLon);



            Log.e("inside ", "prevLat "+prevLat+"nextLat "+nextLat );

            if(marker_all!=null){
                marker_all.remove();
            }


            if(marker_specific!=null){
                marker_specific.remove();
            }


            if(marker_previous!=null){

                marker_previous.remove();
            }

            for (int i = 0; i < routePointsList.size(); i++) {


                Double currLat = Double.parseDouble(routePointsList.get(i).getRouteLat());
                Double currLong = Double.parseDouble(routePointsList.get(i).getRouteLong());

                LatLng point = new LatLng(currLat, currLong);

                Log.e("inside ", "prevLat1 "+prevLat+" currLat "+currLat );


             if (nextLatLon .equals(point) ) {

                // marker_all.remove();


                 if(lastInt>=routePointNumber-1 ||lastInt==routePointNumber-2 ) {

                     marker_specific = mMap.addMarker(new MarkerOptions().position(new LatLng(currLat, currLong)).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue)).title("DESTINATION"));

                     marker_specific.showInfoWindow();

                     //lastInt==routePointNumber-2
                 }

                 else {
                     marker_specific = mMap.addMarker(new MarkerOptions().position(new LatLng(currLat, currLong)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinkmarker)).title("NEXT"));

                     marker_specific.showInfoWindow();

                 }
                }
                else if(prevLatLon.equals(point)){

                  marker_previous= mMap.addMarker(new MarkerOptions().position(new LatLng(currLat, currLong)).icon(BitmapDescriptorFactory.fromResource(R.drawable.greyline)).title("PREVIOUS"));

                  Log.e("marker_previous ","marker_previous ");
                 marker_previous.showInfoWindow();


             }


                else {

                 //marker_specific.remove();
                   marker_all = mMap.addMarker(new MarkerOptions().position(new LatLng(currLat, currLong)).icon(BitmapDescriptorFactory.fromResource(R.drawable.destin)));

/*
                    MarkerOptions options2 = new MarkerOptions()
                            .position(new LatLng(currLat,
                                    currLong))
                            // .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.destin))
                            // .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            ;

                     mMap.addMarker(options2);*/

                }



            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void logOut() {

        progressBar.dismiss();
        SharedPreferences.Editor editor = loginPreference.edit();
        editor.putBoolean("islogin", false);
        editor.commit();

        SharedPreferences.Editor editor1=isRouteSelectedPreference.edit();
        editor1.putBoolean("IsRouteSel",false);
        editor1.commit();

       if(timer!=null) {
           timer.cancel();
           timer.purge();
       }
        Log.e("logoutcalled","logoutcalled ");
        Intent intent = new Intent(MapsActivity.this, loginViewClass.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public void changeRoute(final List<Route> routeList, final Context context){

try {

    // custom dialog    NiceSpinner spinner1; Button btnSave;
    final Dialog dialog = new Dialog(MapsActivity.this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.route_dialogue);

    NiceSpinner  spinner1 = (NiceSpinner) dialog.findViewById(R.id.nice_spinner);
    Button btnSave = (Button) dialog.findViewById(R.id.btnSave);
    Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

    dialog.show();
    dialog.setCancelable(false);

    Log.e("routeList ", "routeList " + routeList.size());
    List routes = new ArrayList();

    for (int i = 0; i < routeList.size(); i++) {
        routes.add(routeList.get(i).getRouteName());
    }


    List<String> dataset = new LinkedList<>(Arrays.asList("Perumbavoor", "Kolenchery", "Muvattupuzha", "Aluva", "Kothamangalam"));
    spinner1.attachDataSource(routes);



    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            SharedPreferences.Editor editor = routePreferences.edit();
            editor.putString("routeName", route);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        }
    });


    btnCancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    });


}catch (Exception e){
    e.printStackTrace();
}
            }

        ////////////end of UI thread

    public void confirmLogout(){

// custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_logout);

        TextView textName = (TextView) dialog.findViewById(R.id.txtNme);
        ImageView image = (ImageView) dialog.findViewById(R.id.imgstudent);
        image.setImageResource(R.drawable.imgb);

        TextView btnLogout = (TextView) dialog.findViewById(R.id.btnLogout);
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
        dialog.show();
        dialog.setCancelable(false);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Methods.testLogOut(getApplicationContext(),MapsActivity.this);

                progressBar = new ProgressDialog(MapsActivity.this);
                progressBar.setCancelable(true);//you can cancel it by pressing back button
                progressBar.setMessage("Logging out");
                progressBar.setIndeterminate(true);

                progressBar.show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //   mainViewClass.this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

        timer.cancel();
        timer.purge();
    }


    private String getDirectionsUrl(List<RoutePoint> routePointList){

            // ArrayList<LatLng> markerPoints;



        Double orgLat=Double.parseDouble(routePointList.get(0).getRouteLat());
        Double orgLong=Double.parseDouble(routePointList.get(0).getRouteLong());

        LatLng orgLatLong= new LatLng(orgLat,orgLong);


        Double destLat=Double.parseDouble(routePointList.get(routePointList.size()-1).getRouteLat());
        Double destLong=Double.parseDouble(routePointList.get(routePointList.size()-1).getRouteLong());

        LatLng destLatLong= new LatLng(destLat,destLong);



        // Origin of route
        String str_origin = "origin="+orgLatLong.latitude+","+orgLatLong.longitude;


        // Destination of route
      String str_dest = "destination="+destLatLong.latitude+","+destLatLong.longitude;



        // Sensor enabled
        String sensor = "sensor=false"+"&key="+"AIzaSyBiEZLnEvwWt_s5cySZHTstjGaSiXlITnY";

        // Waypoints
        String waypoints = "";
        for(int i=1;i<routePointList.size();i++){

            Double currLat =Double.parseDouble(routePointList.get(i).getRouteLat());
            Double currLong=Double.parseDouble(routePointList.get(i).getRouteLong());



            LatLng point  = new LatLng(currLat,currLong);
            if(i==1)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+waypoints;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }




    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service

            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data

            Log.e("result","resultv "+result);
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route

            mMap.addPolyline(lineOptions);
        }
    }

    private void getBusLocation(){

        if(mLastKnownLocation!=null){

            final LatLng DESTINATION = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(DESTINATION)      // Sets the center of the map to Mountain View
                    .zoom(15)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }else{

            Toast.makeText(getApplicationContext(),"Bus details not available",Toast.LENGTH_SHORT).show();


        }
    }








   void  getRouteLocation(){

        try {


                    if(isRouteMode) {

                        //Log.e("")


                        Double orgLat = null;
                        Double orgLong = null;

                        int lastInt = lastIndexPreference.getInt("lastindex", 0);


                        if (lastInt < 0) {

                            SharedPreferences.Editor editor = nextRoutePreference.edit();

                            orgLat = Double.parseDouble(routePointsStatic.get(0).getRouteLat());
                            orgLong = Double.parseDouble(routePointsStatic.get(0).getRouteLong());


                            editor.putString("latitude", String.valueOf(orgLat));
                            editor.putString("longitude", String.valueOf(orgLong));

                            editor.putString("prevlat", String.valueOf("0.00"));
                            editor.putString("prevlong", String.valueOf("0.00"));

                            editor.commit();

                        } else if (lastInt == routePointNumber - 2) {

                            SharedPreferences.Editor editor = nextRoutePreference.edit();

                            orgLat = Double.parseDouble(routePointsStatic.get(1).getRouteLat());
                            orgLong = Double.parseDouble(routePointsStatic.get(1).getRouteLong());

                            Double prevLat = Double.parseDouble(routePointsStatic.get(0).getRouteLat());
                            Double prevLong = Double.parseDouble(routePointsStatic.get(0).getRouteLong());


                            editor.putString("latitude", String.valueOf(orgLat));
                            editor.putString("longitude", String.valueOf(orgLong));

                            editor.putString("prevlat", String.valueOf(prevLat));
                            editor.putString("prevlong", String.valueOf(prevLong));

                            editor.commit();

                        } else if (lastInt >= routePointNumber - 1) {
//routePointsList

                            SharedPreferences.Editor editor = nextRoutePreference.edit();

                            orgLat = Double.parseDouble(routePointsList.get(routePointNumber - 1).getRouteLat());
                            orgLong = Double.parseDouble(routePointsList.get(routePointNumber - 1).getRouteLong());

                            Double prevLat = Double.parseDouble(routePointsList.get(routePointNumber - 2).getRouteLat());
                            Double prevLong = Double.parseDouble(routePointsList.get(routePointNumber - 2).getRouteLong());

                            editor.putString("latitude", String.valueOf(orgLat));
                            editor.putString("longitude", String.valueOf(orgLong));

                            editor.putString("prevlat", String.valueOf(prevLat));
                            editor.putString("prevlong", String.valueOf(prevLong));


                            editor.commit();

                        } else {
                            SharedPreferences.Editor editor = nextRoutePreference.edit();

                            orgLat = Double.parseDouble(routePointsStatic.get(1).getRouteLat());
                            orgLong = Double.parseDouble(routePointsStatic.get(1).getRouteLong());


                            Double prevLat = Double.parseDouble(routePointsStatic.get(0).getRouteLat());
                            Double prevLong = Double.parseDouble(routePointsStatic.get(0).getRouteLong());


                            editor.putString("latitude", String.valueOf(orgLat));
                            editor.putString("longitude", String.valueOf(orgLong));

                            editor.putString("prevlat", String.valueOf(prevLat));
                            editor.putString("prevlong", String.valueOf(prevLong));

                            editor.commit();


                        }


                        final LatLng DESTINATION = new LatLng(orgLat, orgLong);

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(DESTINATION)      // Sets the center of the map to Mountain View
                                .zoom(18)                   // Sets the zoom
                                .bearing(90)                // Sets the orientation of the camera to east
                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                        addMarkerPoints();


                    }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getDefaultLocation(){



        final LatLng DESTINATION = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(DESTINATION)      // Sets the center of the map to Mountain View
                .zoom(18)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {


            case android.R.id.home:
                //   mDrawerLayout.openDrawer(GravityCompat.START);

                MapsActivity.this.finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}


