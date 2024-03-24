package com.example.ladygame;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HuitDamesActivity extends AppCompatActivity {

    private Echiquier echiquier;
    private GridLayout gridLayout;
    private ImageView[][] cases;
    private static final int DELAI_ATTENTE = 200; // 200 millisecondes
    private long dernierTouch = 0;
    private int dameplace = 0;
    private int wrong = 0;

    private int texture = 0;
    /*
    0 => black/white
    1 => marbre
    2 => wood
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huit_dames);

        echiquier = new Echiquier(8);

        gridLayout = findViewById(R.id.grid_layout);
        mettreAJourAffichage();

        gridLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                // trouver la case touchée
                int column = -1;
                int row = -1;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ImageView imageView = cases[i][j];
                        int left = imageView.getLeft();
                        int top = imageView.getTop();
                        int right = left + imageView.getWidth();
                        int bottom = top + imageView.getHeight();

                        if (x >= left && x <= right && y >= top && y <= bottom) {
                            column = j;
                            row = i;
                            break;
                        }
                    }
                    if (column != -1) {
                        break;
                    }
                }

                // si une case a été touchée, essayer d'y placer une dame
                if (column != -1 && row != -1) {
                    long tempsActuel = System.currentTimeMillis();
                    if (tempsActuel - dernierTouch < DELAI_ATTENTE) {
                        return false;
                    }
                    dernierTouch = tempsActuel;
                    if (echiquier.getCase(row, column).estOccupee()) {
                        echiquier.enleverDame(row, column);
                        mettreAJourAffichage();
                        dameplace--;
                        TextView compteurCoupsTextView = findViewById(R.id.compteur_dame);
                        compteurCoupsTextView.setText("Nombre de dame placé : " + dameplace);
                    }else if (echiquier.placerDame(row, column)) {
                        mettreAJourAffichage();
                        dameplace++;
                        TextView compteurCoupsTextView = findViewById(R.id.compteur_dame);
                        compteurCoupsTextView.setText("Nombre de dame placé : " + dameplace);

                        if (echiquier.estResolu()) {
                            Toast.makeText(HuitDamesActivity.this, "Félicitations ! Vous avez gagné !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        wrong++;
                        TextView compteurCoupsTextView = findViewById(R.id.compteur_wrong);
                        compteurCoupsTextView.setText("Nombre d'essaie infructueux : " + wrong + " / 10");
                        if (wrong == 10){
                            Toast.makeText(HuitDamesActivity.this, "PERDU!!!!!", Toast.LENGTH_SHORT).show();
                        }else if (wrong > 10){
                            Toast.makeText(HuitDamesActivity.this, "tu est mauvais réinitialise stp ^^", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                return true;
            }
        });

        Button buttonReinitialiser = findViewById(R.id.button_reinitialiser);
        buttonReinitialiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                echiquier.reinitialiser();
                mettreAJourAffichage();
                dameplace = 0;
                TextView compteurCoupsTextView = findViewById(R.id.compteur_dame);
                compteurCoupsTextView.setText("Nombre de dame placé : " + dameplace);
                wrong = 0;
                TextView compteurCoupsTextViewwrong = findViewById(R.id.compteur_wrong);
                compteurCoupsTextViewwrong.setText("Nombre d'essai infructueux : " + wrong + " / 10");
            }
        });

        Button changerTextureButton = findViewById(R.id.changer_texture_button);
        changerTextureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (texture == 0){
                    texture = 1;
                }else if (texture == 1){
                    texture = 2;
                }else if (texture == 2) {
                    texture = 0;
                }
                mettreAJourAffichage();
            }
        });
    }

    private void mettreAJourAffichage() {
        gridLayout.removeAllViews();
        cases = new ImageView[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ImageView imageView = new ImageView(this);

                // définir la couleur de fond de la case en fonction de sa position
                if ((i + j) % 2 == 0) {
                    if (texture == 0){
                        imageView.setBackgroundResource(R.drawable.white_square);
                    }else if (texture == 1){
                        imageView.setBackgroundResource(R.drawable.white_marble);
                    }else{
                    imageView.setBackgroundResource(R.drawable.white_wood);
                }
                } else {
                    if (texture == 0){
                        imageView.setBackgroundResource(R.drawable.black_square);
                    }else if (texture == 1){
                        imageView.setBackgroundResource(R.drawable.black_marble);
                    }else{
                        imageView.setBackgroundResource(R.drawable.black_wood);
                    }
                }

                // définir l'image de la case en fonction de son état
                if (echiquier.getCase(i, j).estOccupee()) {
                    imageView.setImageResource(R.drawable.dame);
                }

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = dpToPx(45); // largeur de 45 dp
                params.height = dpToPx(45); // hauteur de 45 dp
                imageView.setLayoutParams(params);

                cases[i][j] = imageView;
                gridLayout.addView(imageView);
            }
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}
