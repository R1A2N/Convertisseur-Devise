package com.example.individualproject_currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends AppCompatActivity {

    TextView convert_from_dropdown_menu, convert_to_dropdown_menu, conversion_rate;
    EditText edit_amount_to_convert_value;
    Button conversion, exit;
    String convert_from_value, convert_to_value, conversion_value;
    String[] currency = {"MAD", "USD", "EUR", "GBP", "JPY", "AUD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        convert_from_dropdown_menu = findViewById(R.id.convert_from_dropdown_menu);
        convert_to_dropdown_menu = findViewById(R.id.convert_to_dropdown_menu);
        conversion_rate = findViewById(R.id.conversion_rate);
        conversion = findViewById(R.id.conversion);
        exit = findViewById(R.id.exit);

        edit_amount_to_convert_value = findViewById(R.id.edit_amount_to_convert_value);

        convert_from_dropdown_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, convert_from_dropdown_menu);
            }
        });

        convert_to_dropdown_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, convert_to_dropdown_menu);
            }
        });

        conversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double edit_amount_to_convert_value = Double.valueOf(MainActivity.this.edit_amount_to_convert_value.getText().toString());
                    getConversionRate(convert_from_value, convert_to_value, edit_amount_to_convert_value);
                } catch (Exception e) {

                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMainActivity();
            }
        });
    }

    private void showPopupMenu(View view, final TextView dropdownMenu) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        for (String currencyCode : currency) {
            popupMenu.getMenu().add(currencyCode);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dropdownMenu.setText(item.getTitle());
                if (dropdownMenu == convert_from_dropdown_menu) {
                    convert_from_value = item.getTitle().toString();
                } else if (dropdownMenu == convert_to_dropdown_menu) {
                    convert_to_value = item.getTitle().toString();
                }
                return true;
            }
        });

        popupMenu.show();
    }

    private void getConversionRate(String convert_from_value, String convert_to_value, Double edit_amount_to_convert_value) {
        double conversion_rate_value = getStaticConversionRate(convert_from_value, convert_to_value);
        conversion_value = String.valueOf(round((conversion_rate_value * edit_amount_to_convert_value), 2));
        conversion_rate.setText(conversion_value);
    }

    private double getStaticConversionRate(String convert_from_value, String convert_to_value) {
          switch (convert_from_value) {
            case "MAD":
                switch (convert_to_value) {
                    case "USD":
                        return 0.11;
                    case "EUR":
                        return 0.10;
                    case "GBP":
                        return 0.09;
                    case "JPY":
                        return 12.12;
                    case "AUD":
                        return 0.15;
                    default:
                        return 1.0;
                }

            default:
                return 1.0;
        }
    }

    private double round(double value, int currency) {
        if (currency < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(currency, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void closeMainActivity() {
        MainActivity.this.finish();
        System.exit(0);
    }
}
