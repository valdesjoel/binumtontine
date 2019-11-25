package com.example.binumtontine.dao;

/**
 * Cette interface se charge d'éxécuter une méthode lorsqu'on a concrètement une reponse venant du serveur HTTP
 */
public interface AsyncResponse {
    void processFinish(String output);
}
