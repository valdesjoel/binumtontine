package com.example.binumtontine.activity.adherent;

public class Record {
    public String age;
    public String name;
    public String position;
    public String address;
    public String nomAffilier;

    public Record(String age, String name, String position, String address) {
        this.age = age;
        this.name = name;
        this.position = position;
        this.address = address;

    }

    public Record(String age, String name, String position, String address, String nomAffilier) {
        this.age = age;
        this.name = name;
        this.position = position;
        this.address = address;
        this.nomAffilier = nomAffilier;
    }
}
