package com.ambitious.parampara.Activity.others;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.ambitious.parampara.Model.nativePlace;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int PERMISSION_CALLBACK_CONSTANT = 100;
    public static LatLng ll = null;
    public static MapsActivity mapsActivity;
    static String[] permissionsRequired = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;
    public GoogleApiClient googleApiClient;
    ImageView ib_back, search;
    MarkerOptions marker;
    FloatingActionButton next;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private Location lastLocation;
    private int flag = 0;
    AlertDialog alert;
    private Context mContext;
    private ImageView pinImg;
    private TextView listView, txt_title;

    ArrayList<nativePlace.Location> task = new ArrayList<nativePlace.Location>();
    private AlertDialog deleteDialog;
    private ProgressBar mBarProgress;
    private TextView mNameProduct;
    private TextView mPriceProduct;
    private ImageView mDirection;
    private TextView mDistance;
    private LinearLayout mDisLy;
    private RelativeLayout mLy;
    private LinearLayout mLyMain;
    private ArrayList<nativePlace.Results> nativePlace = new ArrayList<>();
    private TextView text_search;
    private LinearLayout search_layout;
    View mapView;
    private ProgressDialog progressDialog;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionsRequired[0]) ||
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionsRequired[1])) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Access Location permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    alert = alertBuilder.create();

                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }
                return false;
            } else {

                return true;
            }
        } else {
            if (flag != 0) {
                getLastKnownLocation();

            }
            return true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyDILYQC5PYXYdvTCjttiKZj4bBF7LT7vtQ");

        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);

        this.mContext = this;

        mapsActivity = this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();


        ib_back = findViewById(R.id.ib_back);
        text_search = findViewById(R.id.text_search);
        search_layout = findViewById(R.id.search_layout);
        text_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(MapsActivity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);


            }
        });
        listView = findViewById(R.id.list_view);
        txt_title = findViewById(R.id.txt_title);
        next = (FloatingActionButton) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ll != null) {

                    Intent intent = new Intent();
                    intent.putExtra("latitude", ll.latitude);
                    intent.putExtra("longitude", ll.longitude);
                    intent.putExtra("latLong", ll);
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_CANCELED);
                }

                finish();

            }
        });

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();


            }
        });

        if (googleApiClient == null) {
            Log.d("APICLIENT", "createGoogleApi()");
            try {
                googleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
            } catch (Exception e) {
                Log.e("hello", e.getMessage());
            }
        }
        googleApiClient.connect();
        pinImg = findViewById(R.id.pinImg);
        pinImg.setVisibility(View.GONE);


        if (getIntent().getStringExtra("mapLoc") != null) {
            next.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            txt_title.setText("Tample");

            System.out.println("SAfasdf::::  " + task.toString());


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
        Animatoo.animateSlideRight(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        flag = 3;
        getLastKnownLocation();
        if (getIntent().getStringExtra("mapLoc") == null) {

            mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    mMap.clear();
                    pinImg.setVisibility(View.VISIBLE);

                }
            });
        } else {
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    Location location1 = new Location("");
                    location1.setLatitude(ll.latitude);
                    location1.setLongitude(ll.longitude);


                    Location location2 = new Location("");
                    location2.setLatitude(marker.getPosition().latitude);
                    location2.setLongitude(marker.getPosition().longitude);

                    float distance = location1.distanceTo(location2) / 1000;
                    System.out.println("DSafas:::  " + distance);
                    showRateDialog(marker.getPosition().latitude, marker.getPosition().longitude, marker.getTitle());
                    return true;
                }
            });

        }
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                ll = mMap.getCameraPosition().target;

            }
        });

        mMap.setOnCameraMoveCanceledListener(new GoogleMap.OnCameraMoveCanceledListener() {
            @Override
            public void onCameraMoveCanceled() {
                LatLng latLng = mMap.getCameraPosition().target;
                MarkerOptions marker1 = new MarkerOptions().position(latLng);
                mMap.addMarker(marker1);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 18), 1500, null);


            }
        });


        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,  RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT,  RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_END, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        rlp.setMarginStart(50);
        rlp.setMargins(100,0 , 100, 200);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        flag = 2;
        getLastKnownLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void getLastKnownLocation() {
        try {
            Log.d("Location", "getLastKnownLocation()");
            if (checkPermission(this)) {
                if (alert != null) {
                    if (alert.isShowing()) {
                        alert.dismiss();
                    }
                }
                if (!mMap.isMyLocationEnabled())
                    mMap.setMyLocationEnabled(true);
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

                fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {

                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            System.out.println("Location L:: " + location.getLatitude() + " " + location.getLongitude());

                            ll = new LatLng(location.getLatitude(), location.getLongitude());
                            marker = new MarkerOptions().position(ll)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            mMap.addMarker(marker);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 14), 1500, null);

                            getAddress(MapsActivity.this, location.getLatitude(), location.getLongitude());

                        }


                    }
                });

            } else {
                Log.w("Location", "No location retrieved yet");
            }

        } catch (Error error) {
            Log.d("Service Error :: ", error.getMessage());
        }

    }

    private void createMarker(double latitude, double longitude, String pos) {
        ll = new LatLng(latitude, longitude);
        marker = new MarkerOptions().position(ll)
                .title(pos)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 14), 1500, null);

    }


    private void getNearByPlace(final Double latitude, Double longitude, String type) {

        progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<nativePlace> call = AppConfig.loadInterface().getNearbyPlace("" + String.valueOf(latitude) + "," + String.valueOf(longitude), "" + type);
        call.enqueue(new Callback<nativePlace>() {
            @Override
            public void onResponse(Call<nativePlace> call, Response<nativePlace> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    System.out.println("safasdf::::  " + response.body().getResults().toString());

                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        nativePlace.addAll(response.body().getResults());
                        task.add(response.body().getResults().get(i).getGeometry().getLocation());
                        createMarker(response.body().getResults().get(i).getGeometry().getLocation().getLat(), response.body().getResults().get(i).getGeometry().getLocation().getLng(), String.valueOf(i));
                    }

                } else {
                    utlity.toast(MapsActivity.this,"Please check your internet connection!","e");


                }

            }

            @Override
            public void onFailure(Call<nativePlace> call, Throwable t) {
                progressDialog.dismiss();
                utlity.toast(MapsActivity.this,"Please check your internet connection!","e");
                t.printStackTrace();
            }
        });


    }


    private void showRateDialog(final Double latitude, final double longitude, String title) {

        try {
            LayoutInflater factory = LayoutInflater.from(this);
            final View itemView = factory.inflate(R.layout.dialog_map_item, null);
            deleteDialog = new AlertDialog.Builder(this).create();
            deleteDialog.setView(itemView);
            mBarProgress = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            mBarProgress.setVisibility(View.GONE);
            mNameProduct = (TextView) itemView.findViewById(R.id.product_name);
            mLyMain = (LinearLayout) itemView.findViewById(R.id.main_ly);
            mPriceProduct = (TextView) itemView.findViewById(R.id.product_price);
            mDistance = (TextView) itemView.findViewById(R.id.distance);
            mDistance.setVisibility(View.GONE);
            mDisLy = (LinearLayout) itemView.findViewById(R.id.ly_dis);
            mLy = itemView.findViewById(R.id.ly);
            mDirection = (ImageView) itemView.findViewById(R.id.direction);
            mDirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startNavigation(MapsActivity.this, "" + latitude + "," + longitude);
                }
            });
            mPriceProduct.setText(nativePlace.get(Integer.parseInt(title)).getVicinity());
            mNameProduct.setText(nativePlace.get(Integer.parseInt(title)).getName());

            deleteDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("afasdf:::  " + e.toString());
        }
    }


    private void startNavigation(Context context, String loc) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + loc);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }


    public List<Address> getAddress(Context context, double latitude, double longitude) {
        List<Address> addresses = null;
        String address1 = "";

        if (getIntent().getStringExtra("mapLoc") != null) {

            getNearByPlace(latitude, longitude, "temple");
        }

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address1 = addresses.get(0).getAddressLine(0);
            System.out.println("Dafasdf::::   " + addresses.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        text_search.setText("" + address1);
        return addresses;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                if (place.getLatLng() != null) {
                    ll = place.getLatLng();
                    System.out.println("dsfasf :" + ll.latitude + "     ...>>>" + ll.longitude);
                    mMap.clear();
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(place.getLatLng());
                    mMap.moveCamera(center);
                    mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
                    getAddress(MapsActivity.this, place.getLatLng().latitude, place.getLatLng().longitude);
                }
                Log.i("plmg", "Place: " + place.getLatLng() + ", " + place.getName());

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("mpi", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


}
