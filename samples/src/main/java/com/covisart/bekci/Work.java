package com.covisart.bekci;

public class Work {
    private String id, hektar, litre, nozzle, aralik;

    public Work() {
    }

    public Work(String id, String hektar, String litre, String nozzle, String aralik) {
        this.id = id;
        this.hektar = hektar;
        this.litre = litre;
        this.nozzle = nozzle;
        this.aralik = aralik;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getHektar() {
        return hektar;
    }

    public void setHektar(String hektar) {
        this.hektar = hektar;
    }

    public String getNozzle() {
        return nozzle;
    }

    public void setNozzle(String nozzle) {
        this.nozzle = nozzle;
    }

    public String getLitre() {
        return litre;
    }

    public void setLitre(String litre) {
        this.litre = litre;
    }

    public String getAralik() {
        return aralik;
    }

    public void setAralik(String aralik) {
        this.aralik = aralik;
    }
}
