package com.example.todoapp;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private FirebaseAuth auth;
    ImageView imageView;
    // Button button;
    //private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    TextView usernameText, emailText;
    String username, email;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        auth = FirebaseAuth.getInstance();
        loadUserInfo();
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

        usernameText.setText(username);
        emailText.setText(email);

       /*
        button = (Button) view.findViewById(R.id.button);
        imageView = (ImageView) view.findViewById(R.id.profileImage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });
        */
        return view;
    }
    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                imageView.setImageBitmap(bitmap);

            }
        }
    }
    */

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

    private void loadUserInfo() {
        FirebaseUser user = auth.getCurrentUser();
        username = user.getDisplayName();
        email = user.getEmail();
    }


}