package com.iamcodder.easyreminder.ui.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int selectedHour = calendar.get(Calendar.HOUR_OF_DAY);
        int selectedMinutes = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(requireContext(),
                (TimePickerDialog.OnTimeSetListener) getActivity(),
                selectedHour, selectedMinutes, true);
        return dialog;

    }
}
