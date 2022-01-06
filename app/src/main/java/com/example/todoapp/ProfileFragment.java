package com.example.todoapp;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.io.ByteArrayOutputStream;


public class ProfileFragment extends Fragment {
    private FirebaseAuth auth;
    ImageView imageView;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    TextView usernameText, emailText;
    Button photoButtonCamera,photoButtonGallery;
    String username, email;
    private static final String SHARED_PREF_NAME = "username";
    private static final String KEY_NAME = "key_username";
    private static final int FILE_SELECT_REQUEST_CODE_1 = 1001;
    private static final int GALLERY_PERMISSION_REQUEST_CODE = 154;
    static ProfileFragment instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        MainActivity.getInstance().bottomNavigationView.setVisibility(View.VISIBLE);
        usernameText = view.findViewById(R.id.usernameText);
        emailText = view.findViewById(R.id.emailText);
        displayEmailAndName();
        instance = this;
        photoButtonCamera = (Button) view.findViewById(R.id.photoButtonCamera);
        photoButtonGallery= (Button) view.findViewById(R.id.photoButtonGallery);
        imageView = (ImageView) view.findViewById(R.id.profileImage);

        photoButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });

        photoButtonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {

                    if (checkRequestedGalleryPermission(GALLERY_PERMISSION_REQUEST_CODE)) {
                        openGallery(FILE_SELECT_REQUEST_CODE_1);
                    } else {
                        requestGalleryPermissionFromUser(GALLERY_PERMISSION_REQUEST_CODE);
                    }
                } else {
                    openGallery(FILE_SELECT_REQUEST_CODE_1);
                }
            }
        });
        return view;
    }

    private void displayEmailAndName() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        username = sharedPreferences.getString(KEY_NAME,null);
        System.out.println(username);
        if (username != null){
            usernameText.setText(username);
        }
        FirebaseUser user = auth.getCurrentUser();
        email = user.getEmail();
        emailText.setText(email);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestGalleryPermissionFromUser(int permission) {
        switch (permission) {
            case GALLERY_PERMISSION_REQUEST_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.CAMERA)) {
                    Toast.makeText(getActivity(), R.string.permission_for_gallery, Toast.LENGTH_LONG).show();
                        getActivity().requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_PERMISSION_REQUEST_CODE);
                } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_PERMISSION_REQUEST_CODE);
                }
                break;
        }
    }

    public void openGallery(int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try {
            MainActivity.getInstance().startActivityForResult(i, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkRequestedGalleryPermission(int permission) {
        boolean result = false;
        switch (permission) {
            case GALLERY_PERMISSION_REQUEST_CODE:
                int request_status = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
                if (request_status == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
                break;
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);
                imageView.setImageBitmap(bitmap);
            }
        }

        if(requestCode == FILE_SELECT_REQUEST_CODE_1 && resultCode == getActivity().RESULT_OK){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            try {
                cursor.moveToFirst();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 15;
                Bitmap bitmap1;
                bitmap1 = BitmapFactory.decodeFile(filePath, options);
                imageView.setImageBitmap(bitmap1);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.logout));
        builder.setMessage(getResources().getString(R.string.are_you_sure_logout));
        builder.setNegativeButton(getResources().getString(R.string.no), null);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                auth.signOut();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.show();
    }

    public static ProfileFragment getInstance(){
        return instance;
    }

}