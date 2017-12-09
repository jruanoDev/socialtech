package com.github.jruanodev.socialtech;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jruanodev.socialtech.dao.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactDetailActivity extends AppCompatActivity {

    @BindView(R.id.contactDetail_backarrow) ImageView backArrow;
    @BindView(R.id.contactDetailName) TextView contactDetailName;
    @BindView(R.id.contactDetailPhone) TextView contactDetailPhone;
    @BindView(R.id.contactDetailEmail) TextView contactDetailEmail;
    @BindView(R.id.contactDetailSex) TextView contactDetailSex;
    @BindView(R.id.contactDetailAge) TextView contactDetailAge;
    @BindView(R.id.contactDetailFormation) TextView contactDetailFormation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactdetail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        Contact contact = bundle.getParcelable("selectedContact");

        contactDetailName.setText(contact.getName());
        contactDetailPhone.setText(contact.getPhone());
        contactDetailEmail.setText(contact.getEmail());
        contactDetailSex.setText(contact.getSex());
        contactDetailAge.setText("" + contact.getAge() + " años");

        if(contact.getFormation().isEmpty())
            contactDetailFormation.setText("No hay formación conocida.");
        else
            contactDetailFormation.setText(contact.getFormation());

    }
}
