package com.example.phone_database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdditionPhoneActivity extends AppCompatActivity {
    private boolean isEditMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition_phone);
        Intent intent = getIntent();

        EditText manufacturerEditText = findViewById(R.id.educja1);
        EditText modelEditText = findViewById(R.id.educja2);
        EditText androidVersionEditText = findViewById(R.id.educja3);
        EditText websiteEditText = findViewById(R.id.educja4);

        long elementId = intent.getLongExtra("ELEMENT_ID", -1);
        if (intent != null && intent.hasExtra("ELEMENT_ID")) {
            elementId = intent.getLongExtra("ELEMENT_ID", -1);
            if (elementId != -1) {
                ElementViewModel viewModel = new ViewModelProvider(this).get(ElementViewModel.class);
                viewModel.getElementById(elementId).observe(this, new Observer<Element>() {
                    @Override
                    public void onChanged(Element element) {
                        if (element != null) {
                            manufacturerEditText.setText(element.getManufacturer());
                            modelEditText.setText(element.getModel());
                            androidVersionEditText.setText(element.getAndroidVersion());
                            websiteEditText.setText(element.getWebsite());
                        }
                    }
                });
            }
        }

        long finalElementId = elementId;
        Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manufacturer = manufacturerEditText.getText().toString();
                String model = modelEditText.getText().toString();
                String androidVersion = androidVersionEditText.getText().toString();
                String website = websiteEditText.getText().toString();

                if (manufacturer.isEmpty()) {
                    manufacturerEditText.setError("Please enter the manufacturer");
                    return;
                }

                if (model.isEmpty()) {
                    modelEditText.setError("Please enter the model");
                    return;
                }

                if (androidVersion.isEmpty()) {
                    androidVersionEditText.setError("Please enter the Android version");
                    return;
                }

                if (website.isEmpty()) {
                    websiteEditText.setError("Please enter the website");
                    return;
                }

                ElementViewModel viewModel = new ElementViewModel(getApplication());

                if (finalElementId == -1) {
                    viewModel.insert(new Element(manufacturer, model, androidVersion, website));
                } else {
                    Element editElement = new Element(finalElementId, manufacturer, model, androidVersion, website);
                    viewModel.update(editElement);
                }

                Intent intent = new Intent(AdditionPhoneActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                finish();
            }
        });
        Button cancelButton = findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button websiteButton = findViewById(R.id.web_site);
        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String websiteUrl = websiteEditText.getText().toString();

                if (!websiteUrl.isEmpty()) {
                    if (!websiteUrl.startsWith("http://") && !websiteUrl.startsWith("https://")) {
                        websiteUrl = "http://" + websiteUrl;
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(AdditionPhoneActivity.this, "No app to open browser", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdditionPhoneActivity.this, "Website field is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
