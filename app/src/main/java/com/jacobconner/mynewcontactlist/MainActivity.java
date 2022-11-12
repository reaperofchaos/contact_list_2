package com.jacobconner.mynewcontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListButton();
        initMapButton();
        initSettingsButton();
        initToggleButton();
        setForEditing(false);
        initChangeDateButton();
        initSaveButton();
    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMapButton() {
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initToggleButton() {
        final ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setForEditing(editToggle.isChecked());
            }
        });
    }

    private void setForEditing(boolean enabled) {
        EditText editName = findViewById(R.id.editName);
        EditText editAddress = findViewById(R.id.editAddress);
        EditText editCity = findViewById(R.id.editCity);
        EditText editState = findViewById(R.id.editState);
        EditText editZipCode = findViewById(R.id.editZipcode);
        EditText editPhone = findViewById(R.id.editHome);
        EditText editCell = findViewById(R.id.editCell);
        EditText editEmail = findViewById(R.id.editEMail);
        Button buttonChange = findViewById(R.id.buttonBirthday);
        Button buttonSave = findViewById(R.id.buttonSave);

        editName.setEnabled(enabled);
        editAddress.setEnabled(enabled);
        editCity.setEnabled(enabled);
        editState.setEnabled(enabled);
        editZipCode.setEnabled(enabled);
        editPhone.setEnabled(enabled);
        editCell.setEnabled(enabled);
        editEmail.setEnabled(enabled);
        buttonChange.setEnabled(enabled);
        buttonSave.setEnabled(enabled);

        if (enabled) {
            editName.requestFocus();
        }

    }

    private void initChangeDateButton() {
        Button changeDate = findViewById(R.id.buttonBirthday);
        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                //DatePickerDialog datePickerDialog = new DatePickerDialog();
                //datePickerDialog.show(fm, "DatePick");
            }
        });
    }

    public static int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the values from the form
                EditText editName = findViewById(R.id.editName);
                EditText editAddress = findViewById(R.id.editAddress);
                EditText editCity = findViewById(R.id.editCity);
                EditText editState = findViewById(R.id.editState);
                EditText editZipCode = findViewById(R.id.editZipcode);
                EditText editPhone = findViewById(R.id.editHome);
                EditText editCell = findViewById(R.id.editCell);
                EditText editEmail = findViewById(R.id.editEMail);

                String name = editName.getText().toString();
                String address = editAddress.getText().toString();
                String city = editCity.getText().toString();
                String state = editState.getText().toString();
                int zipCode = tryParseInt(editZipCode.getText().toString());
                String phone = editPhone.getText().toString();
                String cellPhone = editCell.getText().toString();
                String email = editEmail.getText().toString();

                //Build a contact
                Contact newContact = new Contact();
                newContact.setId(0);
                newContact.setName(name);
                newContact.setAddress(address);
                newContact.setCity(city);
                newContact.setState(state);
                newContact.setZipCode(zipCode);
                newContact.setHomePhone(phone);
                newContact.setCellPhone(cellPhone);
                newContact.setEmail(email);

                boolean nameIsEmpty = editName.getText().toString().trim().length() == 0;
                if (!nameIsEmpty) {
                    //Create contact
                    try {
                        ContactDataSource ds = new ContactDataSource(MainActivity.this);
                        ds.open();
                        ds.insertContact(newContact);
                        ds.close();

                        Toast.makeText(
                                getApplicationContext(),
                                "The contact has been added to your address book",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception exception) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Error: The contact was not added. \n" + exception,
                                Toast.LENGTH_LONG).show();
                    }

                    //clear the form
                    editName.getText().clear();
                    editAddress.getText().clear();
                    editCity.getText().clear();
                    editState.getText().clear();
                    editZipCode.getText().clear();
                    editPhone.getText().clear();
                    editCell.getText().clear();
                    editEmail.getText().clear();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Could not submit. Please type a name for your contact.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}