package sv.edu.itca.notipush;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "myapp_channel"; // ID del canal
    private static final String CHANNEL_NAME = "My App Notifications"; // Nombre del canal
    private static final String CHANNEL_DESCRIPTION = "Notifications desde My App"; // Descripción del canal

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        if (message.getNotification() != null) {
            String title = message.getNotification().getTitle();
            String body = message.getNotification().getBody();

            // Guardar el mensaje en SharedPreferences
            SharedPreferences preferences = getSharedPreferences("messague", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("me", body);  // Aquí guardas el cuerpo del mensaje
            editor.apply();

            // Mostrar la notificación
            showmessague(message);  // Llama al método para mostrar la notificación
        }
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Crear el canal solo si está en una versión compatible (Android O o superior)
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Registrar el canal con el sistema
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void showmessague(RemoteMessage message) {

        String title = message.getNotification() != null ? message.getNotification().getTitle() : "Título no disponible";
        String body = message.getNotification() != null ? message.getNotification().getBody() : "Cuerpo no disponible";

        // Construir el texto de la notificación con los datos recibidos
        String notificationContent = "Título: " + title + "\n" +
                "Cuerpo: " + body + "\n" ;

        // Construir la notificación
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_circle_notifications_24) // Asegúrate de tener un icono para la notificación
                .setContentTitle("Nuevo Mensaje")
                .setContentText(notificationContent) // Muestra toda la información
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true); // La notificación se cancela cuando el usuario la toca

        // Obtener el NotificationManager y mostrar la notificación
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(0, notificationBuilder.build()); // El 0 es el ID de la notificación
        }
    }

}