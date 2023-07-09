package com.vinnovations.voicenotesjava;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NotesDescription extends BottomSheetDialogFragment {

    private TextView txt_title,txt_desc;
    String title, desc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_description, container, false);


        txt_title = view.findViewById(R.id.txt_title);
        txt_desc = view.findViewById(R.id.txt_desc);

        Bundle args = getArguments();
        if (args != null) {
            title = args.getString("title");
            desc = args.getString("desc");

        }
        txt_title.setText(title);
        txt_desc.setText(desc);


        return view;
    }

}
