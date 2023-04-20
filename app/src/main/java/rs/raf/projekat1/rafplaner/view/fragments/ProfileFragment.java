package rs.raf.projekat1.rafplaner.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.HelperFunctions;
import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.databinding.FragmentProfileBinding;
import rs.raf.projekat1.rafplaner.model.User;
import rs.raf.projekat1.rafplaner.view.activities.LoginActivity;
import rs.raf.projekat1.rafplaner.databinding.FragmentCalendarBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private SharedPreferences sharedPreferences;

    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Activity activity = getActivity();
        sharedPreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);
        String email = sharedPreferences.getString("email", "email@email.com");

        binding.tvProfileUsername.setText(username);
        binding.tvProfileEmail.setText(email);

        binding.btnLogout.setOnClickListener(v -> {
            sharedPreferences
                    .edit()
                    .remove("username")
                    .apply();
            Intent mainIntent = new Intent(activity, LoginActivity.class);
            this.startActivity(mainIntent);
            activity.finish();

        });

        binding.btnChangePassword.setOnClickListener(v -> {
            showChangePasswordAlert();
        });

        binding.btnEn.setOnClickListener(v -> {
            changeLocale("en");
            activity.recreate();
        });

        binding.btnSr.setOnClickListener(v -> {
            changeLocale("sr");
            activity.recreate();
        });

        return root;
    }

    private void showChangePasswordAlert() {
        Context context = getContext();

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText oldPassword = new EditText(getContext());
        final EditText newPassword = new EditText(getContext());
        final EditText newPassword2 = new EditText(getContext());



        alert.setTitle(context.getString(R.string.change_password));

        LinearLayout lp = new LinearLayout(context);
        lp.setOrientation(LinearLayout.VERTICAL);
        lp.setPadding(15,15,15,15);
        lp.addView(oldPassword);
        lp.addView(newPassword);
        lp.addView(newPassword2);
        oldPassword.setHint(context.getString(R.string.old_password));
        newPassword.setHint(context.getString(R.string.new_password));
        newPassword2.setHint(context.getString(R.string.new_password));

        oldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());

        alert.setView(lp);

        alert.setCancelable(false);
        alert.setPositiveButton(context.getString(R.string.save), (dialog, whichButton) -> {});
        alert.setNegativeButton(R.string.cancel, (dialog, whichButton) -> {});

        final AlertDialog dialog = alert.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (changePassword(oldPassword.getText().toString(), newPassword.getText().toString(), newPassword2.getText().toString(), lp))
            {
                dialog.dismiss();
                makeToast(getString(R.string.password_changed), binding.profileRoot);
            }
        });

    }

    private boolean changePassword(String oldPassword, String newPassword, String newPassword2, View view) {

        if(!newPassword.equals(newPassword2)) {
            makeToast(getString(R.string.passwords_dont_match), view);
            return false;
        }
        if(!HelperFunctions.checkPasswordRequirements(newPassword)) {
            makeToast(getString(R.string.invalid_password), view);
            return false;
        }
        String realOldPassword = AppModule.getInstance(getActivity().getApplication()).getDb().getUser(username).getPassword();
        Log.d("TEST", "changePassword: "+realOldPassword+" "+oldPassword+" "+newPassword+" "+newPassword2);
        if(!realOldPassword.equals(oldPassword)) {
            makeToast(getString(R.string.invalid_old_password), view);
            return false;
        }
        AppModule.getInstance(getActivity().getApplication()).getDb().updateUserPassword(username, newPassword);
        return true;
    }


    private void makeToast(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(null, null).setTextMaxLines(10).show();
    }

    private void changeLocale(String  lang){

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        sharedPreferences
                .edit()
                .putString("language", lang)
                .apply();
    }
}