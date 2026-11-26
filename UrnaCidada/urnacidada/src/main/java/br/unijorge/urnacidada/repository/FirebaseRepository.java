package br.unijorge.urnacidada.repository;

import br.unijorge.urnacidada.model.Voto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.File;

public class FirebaseRepository {
    private Firestore db;
    private boolean conectado = false;

    public FirebaseRepository() {
        try {
            File keyFile = new File("firebase-key.json");
            if (keyFile.exists()) {
                FileInputStream serviceAccount = new FileInputStream(keyFile);
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }
                this.db = FirestoreClient.getFirestore();
                this.conectado = true;
                System.out.println("Firebase Cloud Firestore conectado com sucesso!");
            } else {
                System.out.println("Aviso: 'firebase-key.json' não encontrado. Rodando em modo local.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao Firebase: " + e.getMessage());
        }
    }

    public void salvarVoto(Voto voto) {
        if (conectado && db != null) {
            db.collection("votos_urna_cidada").add(voto.toMap());
        }
    }
}