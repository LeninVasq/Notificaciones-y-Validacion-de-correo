package sv.edu.itca.notipush;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.ktx.Firebase;
import com.google.firebase.messaging.FirebaseMessaging;

public class myapp extends Application {





    @Override
    public void onCreate() {
        super.onCreate();


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        String errorMessage = "Error al obtener el token: " + task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                        Log.e("FirebaseToken", errorMessage); // Muestra el error en la consola
                        return;
                    }

                    // Obtener el token
                    String token = task.getResult();

                    // Guardar el token en SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("MiAppPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("token", token);
                    editor.apply();

                    // Aquí podrías llamar al método para enviar notificaciones si es necesario
//                    sendNotification("¡Bienvenido a mi app!"); // Ejemplo de mensaje
                });
    }
}
