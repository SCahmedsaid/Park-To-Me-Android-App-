package com.staresmiles.amracodes.amitproject.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.staresmiles.amracodes.amitproject.R;
import com.staresmiles.amracodes.amitproject.activities.MapsActivity;
import com.staresmiles.amracodes.amitproject.control.models.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amra on 5/7/2018.
 */

public class HomeFragment extends BaseFragment implements LocationListener {

    private View homeFragmentView;
    private Spinner type;
    private Spinner distance;
    private Button searchButton;
    private String apiUrl;
    private String lat;
    private String lng;
    private String dist;
    private String typeTxt;
    private ProgressDialog progressDialog;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public static String TAG = "maps_project";
    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    android.location.Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    ArrayList<Location> locations = new ArrayList<>();
    public static String LOCATIONS_LIST = "locations";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;
    private boolean mLocationPermissionGranted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeFragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        type = (Spinner) homeFragmentView.findViewById(R.id.type_spinner);
        distance = (Spinner) homeFragmentView.findViewById(R.id.distance_spinner);
        searchButton = (Button) homeFragmentView.findViewById(R.id.search_button);


        final ArrayList<String> distances = new ArrayList<>();
        distances.add("500");
        distances.add("1000");
        distances.add("1500");
        distances.add("2000");
        distances.add("2500");
        distances.add("3000");
        distances.add("3500");
        distances.add("4000");
        distances.add("4500");
        distances.add("5000");
        distances.add("5500");
        distances.add("6000");
        distances.add("6500");
        distances.add("7000");
        distances.add("7500");
        distances.add("8000");
        distances.add("8500");
        distances.add("9000");
        distances.add("9500");
        distances.add("10000");


        final ArrayList<String> locationTypes = new ArrayList<>();
        locationTypes.add("airport");
        locationTypes.add("atm");
        locationTypes.add("bakery");
        locationTypes.add("bank");
        locationTypes.add("book_store");
        locationTypes.add("cafe");
        locationTypes.add("casino");
        locationTypes.add("dentist");
        locationTypes.add("department_store");
        locationTypes.add("doctor");
        locationTypes.add("embassy");
        locationTypes.add("hardware_store");
        locationTypes.add("hospital");
        locationTypes.add("lawyer");
        locationTypes.add("library");
        locationTypes.add("mosque");
        locationTypes.add("museum");
        locationTypes.add("park");
        locationTypes.add("parking");
        locationTypes.add("pharmacy");
        locationTypes.add("police");
        locationTypes.add("school");
        locationTypes.add("stadium");
        locationTypes.add("supermarket");
        locationTypes.add("train_station");
        locationTypes.add("travel_agency");
        locationTypes.add("zoo");


        ArrayAdapter<String> locationTypesAdapter =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, locationTypes);

        locationTypesAdapter.
                setDropDownViewResource(android.R.layout.simple_list_item_1);

        type.setAdapter(locationTypesAdapter);

        ArrayAdapter<String> distancesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, distances);

        distancesAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        distance.setAdapter(distancesAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                location = getLocation();
                if(location!= null) {
                    lat = String.valueOf(location.getLatitude());
                    lng = String.valueOf(location.getLongitude());

                    new MyAsyncTasks().execute();
                }else{
                    Toast.makeText(getActivity(), "Please Eable Location", Toast.LENGTH_LONG).show();
                }
            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                typeTxt = locationTypes.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        distance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dist = distances.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return homeFragmentView;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    class MyAsyncTasks extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            // implement API in background and store the response in current variable
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {

                    apiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                            "location=" + lat + "," + lng + "&radius=" + dist + "&type=" + typeTxt + "&keyword=&key="
                            + getString(R.string.google_maps_key);
                    url = new URL(apiUrl);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        current = current + (char) data;
                        data = isw.read();
                        System.out.print(current);

                    }
                    // return the data to onPostExecute method
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

           s= s.replaceAll(" ", "");
           s= s.replaceAll("\n", "");
//            Log.d("data", s.toString());
            // dismiss the progress dialog after receiving data from API
            progressDialog.dismiss();
            try {
                // JSON Parsing of data

                JSONObject parentObject = new JSONObject(s);
                JSONArray jsonArray = parentObject.getJSONArray("results");
                if(jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        Location locationObject = new Location();
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        String name = object.getString("name");
                        JSONObject geometry = object.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        String lat = location.getString("lat");
                        String lng = location.getString("lng");
                        locationObject.setName(name);
                        locationObject.setLatitude(lat);
                        locationObject.setLogitude(lng);

                        locations.add(locationObject);
                    }


                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtra(LOCATIONS_LIST, locations);
                    startActivity(intent);

                }else{
                    Toast.makeText(getActivity(), "No near by places in this range", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "There ia error in network connection, please try again", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public android.location.Location getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {

                showSettingsAlert();
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isGPSEnabled) {
                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionGranted = true;


                        if (isGPSEnabled) {
                            if (location == null) {
                                locationManager.requestLocationUpdates(
                                        LocationManager.GPS_PROVIDER,
                                        MIN_TIME_BW_UPDATES,
                                        MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);

                                Log.d("GPS Enabled", "GPS Enabled");
                                if (locationManager != null) {
                                    location = locationManager
                                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                                    if (location != null) {
                                        latitude = location.getLatitude();
                                        longitude = location.getLongitude();
                                    }
                                }
                            }
                        }

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                }

                // if GPS Enabled get lat/long using GPS Services
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }


    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
