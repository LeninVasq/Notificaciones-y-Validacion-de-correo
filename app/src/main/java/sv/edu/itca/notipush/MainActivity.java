package sv.edu.itca.notipush;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText T = findViewById(R.id.t);
        TextView r = findViewById(R.id.r);

        // Recuperar el token de SharedPreferences
        SharedPreferences preferences1 = getSharedPreferences("MiAppPreferences", MODE_PRIVATE);
        String savedToken = preferences1.getString("token", "No se encontró el token");
        SharedPreferences preferences = getSharedPreferences("messague", MODE_PRIVATE);
        String messague = preferences.getString("me", "No se encontró el token");



// Mostrar el token en un Toast
        T.setText(savedToken);
        r.setText(messague);
        Toast.makeText(getApplicationContext(), "Token recuperado: " + savedToken, Toast.LENGTH_SHORT).show();

    }
}