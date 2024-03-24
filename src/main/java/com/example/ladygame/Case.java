package com.example.ladygame;

public class Case {
    private boolean occupee;

    public Case() {
        occupee = false;
    }

    public boolean estOccupee() {
        return occupee;
    }

    public void placerDame() {
        occupee = true;
    }

    public void enleverDame() {
        occupee = false;
    }

}
