package com.vinnovations.voicenotesjava;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentNotesDescription extends BottomSheetDialogFragment {

    private TextView txt_title,txt_desc;
    String title, desc, position;
    ImageView img_edit,img_delete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_description, container, false);


        txt_title = view.findViewById(R.id.txt_title);
        txt_desc = view.findViewById(R.id.txt_desc);
        img_edit = view.findViewById(R.id.img_edit);
        img_delete = view.findViewById(R.id.img_delete);


        Bundle args = getArguments();
        if (args != null) {
            title = args.getString("title");
            desc = args.getString("desc");
            position = args.getString("position");

        }
        txt_title.setText(title);
        txt_desc.setText(desc);

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNotesDescription notesDesc = new FragmentNotesDescription();
                Bundle args = new Bundle();
                args.putString("title", txt_title.getText().toString() );
                args.putString("desc", txt_desc.getText().toString() );
                args.putString("position", position);
                args.putString("count", "1");
                notesDesc.setArguments(args);
                Intent i = new Intent(getActivity(), CreateNoteActivity.class);
                i.putExtras(args);
                startActivity(i);
                getActivity().finish();

            }
        });

        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNotesDescription notesDesc = new FragmentNotesDescription();
                Bundle args = new Bundle();
                args.putString("title", txt_title.getText().toString() );
                args.putString("desc", txt_desc.getText().toString() );
                args.putString("position", position);
                args.putString("count", "2");
                notesDesc.setArguments(args);
                Intent i = new Intent(getActivity(), CreateNoteActivity.class);
                i.putExtras(args);
                startActivity(i);
                getActivity().finish();
            }
        });


        return view;
    }

}
