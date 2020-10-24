package com.iamcodder.easyreminder.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.iamcodder.easyreminder.R;
import com.iamcodder.easyreminder.interfaces.SendData;

public class EnteringDataFragment extends DialogFragment {

    private final SendData sendData;

    public EnteringDataFragment(SendData sendData) {
        this.sendData = sendData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_with_data, container, false);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText edxContent = view.findViewById(R.id.edxContent);
        EditText edxTitle = view.findViewById(R.id.edxTitle);
        Button btnSave = view.findViewById(R.id.btnSub);
        btnSave.setOnClickListener(v -> {
            String title = edxTitle.getText().toString();
            String content = edxContent.getText().toString();
            if (!title.isEmpty() && !content.isEmpty()) {
                sendData.sendDialogData(title, content);
                if (getActivity() != null)
                    getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            } else {
                Toast.makeText(this.getContext(), "Başlık ve metin boş olamaz", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
