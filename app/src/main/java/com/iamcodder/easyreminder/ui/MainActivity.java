package com.iamcodder.easyreminder.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.iamcodder.easyreminder.databinding.ActivityMainBinding;
import com.iamcodder.easyreminder.ui.fragment.TimePickerFragment;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View mView = binding.getRoot();
        setContentView(mView);

        binding.fab.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "TimePickerFragment");
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        binding.text.setText(hourOfDay + ":" + minute);
    }
}