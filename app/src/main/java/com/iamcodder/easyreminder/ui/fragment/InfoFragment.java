package com.iamcodder.easyreminder.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;

import com.iamcodder.easyreminder.R;
import com.iamcodder.easyreminder.data.local.model.InfoModel;

public class InfoFragment extends DialogFragment {

    private final InfoModel infoModel;

    public InfoFragment(InfoModel infoModel) {
        this.infoModel = infoModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatTextView txtTitle = view.findViewById(R.id.txtTitle);
        AppCompatTextView txtContent = view.findViewById(R.id.txtContent);
        AppCompatTextView txtTime = view.findViewById(R.id.txtTime);

        txtTitle.setText(infoModel.getNotificationTitle());
        txtContent.setText(infoModel.getNotificationContent());
        txtTime.setText(infoModel.getCalendarHours() + ":" + infoModel.getCalendarMinute());
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window;
        if (super.getDialog() != null) {
            window = super.getDialog().getWindow();
            if (window != null) {
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
            }
        }
    }
}
