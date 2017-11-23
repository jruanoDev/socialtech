package com.github.jruanodev.socialtech;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CreateUserFragment extends Fragment implements View.OnClickListener {
    View inflatedView;
    TextView titulo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_createuser, container, false);
        titulo = inflatedView.findViewById(R.id.cTitulo);

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BreeSerif-Regular.ttf");
        titulo.setTypeface(typeFace);

        return inflatedView;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClose:

                break;
        }
    }
}
