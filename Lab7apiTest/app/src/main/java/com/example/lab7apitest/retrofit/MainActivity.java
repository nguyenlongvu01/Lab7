package com.example.lab7apitest.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab7apitest.R;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName, editTextPrice, editTextDescription;
    private Button buttonInsert;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kích hoạt Edge-to-Edge

        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonInsert = findViewById(R.id.buttonInsert);
        textViewResult = findViewById(R.id.textViewResult);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String priceStr = editTextPrice.getText().toString();
                String description = editTextDescription.getText().toString();

                if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
                    Snackbar.make(v, "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                int price = Integer.parseInt(priceStr);

                Product product = new Product(name, price, description);
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<Void> call = apiService.insertProduct(product);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("MainActivity", "Insert success");
                            Snackbar.make(v, "Insert success", Snackbar.LENGTH_SHORT).show();
                            textViewResult.setText("Insert success"); // Cập nhật TextView
                            // Xử lý khi chèn thành công
                        } else {
                            Log.e("MainActivity", "Insert failed: " + response.errorBody().toString());
                            Snackbar.make(v, "Insert failed", Snackbar.LENGTH_SHORT).show();
                            textViewResult.setText("Insert failed"); // Cập nhật TextView
                            // Xử lý khi chèn thất bại
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("MainActivity", "Insert failed: " + t.getMessage());
                        Snackbar.make(v, "Insert failed", Snackbar.LENGTH_SHORT).show();
                        textViewResult.setText("Insert failed: " + t.getMessage()); // Cập nhật TextView
                        // Xử lý khi gặp lỗi trong quá trình gửi yêu cầu
                    }
                });
            }
        });
    }
}
