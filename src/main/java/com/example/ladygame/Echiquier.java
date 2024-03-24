package com.example.ladygame;

import com.example.ladygame.Case;

public class Echiquier {
    private Case[][] matrice;
    private int taille;

    public Echiquier(int taille) {
        this.taille = taille;
        matrice = new Case[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                matrice[i][j] = new Case();
            }
        }
    }

    public boolean placerDame(int ligne, int colonne) {
        if (estValide(ligne, colonne)) {
            matrice[ligne][colonne].placerDame();
            return true;
        }
        return false;
    }

    public void enleverDame(int ligne, int colonne) {
        matrice[ligne][colonne].enleverDame();
    }

    public boolean estValide(int ligne, int colonne) {
        // Vérifie si une dame se trouve dans la même ligne
        for (int j = 0; j < taille; j++) {
            if (matrice[ligne][j].estOccupee()) {
                return false;
            }
        }

        // Vérifie si une dame se trouve dans la même colonne
        for (int i = 0; i < taille; i++) {
            if (matrice[i][colonne].estOccupee()) {
                return false;
            }
        }

        // Vérifie si une dame se trouve sur la diagonale gauche
        for (int i = ligne - 1, j = colonne - 1; i >= 0 && j >= 0; i--, j--) {
            if (matrice[i][j].estOccupee()) {
                return false;
            }
        }

        // Vérifie si une dame se trouve sur la diagonale droite
        for (int i = ligne - 1, j = colonne + 1; i >= 0 && j < taille; i--, j++) {
            if (matrice[i][j].estOccupee()) {
                return false;
            }
        }

        // Vérifie si une dame se trouve sur la diagonale gauche en haut
        for (int i = ligne + 1, j = colonne - 1; i < taille && j >= 0; i++, j--) {
            if (matrice[i][j].estOccupee()) {
                return false;
            }
        }

        // Vérifie si une dame se trouve sur la diagonale droite en bas
        for (int i = ligne + 1, j = colonne + 1; i < taille && j < taille; i++, j++) {
            if (matrice[i][j].estOccupee()) {
                return false;
            }
        }

        return true;
    }

    public int getTaille() {
        return taille;
    }

    public boolean estResolu() {
        int compteur = 0;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (matrice[i][j].estOccupee()) {
                    compteur++;
                }
            }
        }
        return compteur == taille;
    }

    public void reinitialiser() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                matrice[i][j].enleverDame();
            }
        }
    }

    public Case getCase(int i, int j) {
        if (i >= 0 && i < taille && j >= 0 && j < taille) {
            return matrice[i][j];
        } else {
            throw new IndexOutOfBoundsException("Les indices doivent être compris entre 0 et " + (taille - 1));
        }
    }

}