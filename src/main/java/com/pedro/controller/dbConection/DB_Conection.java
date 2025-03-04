package com.pedro.controller.dbConection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * Esta clase sirve para crear la conexión con la base de datos no relacional MongoDB
 * y/o para crear la propia base de datos.
 */
public class DB_Conection {
    // Atributos
    private String host;
    private String port;
    private String dbName;
    private String user;
    private String password;
    private String url;

    // Constructores
    public DB_Conection() {
        this.host = "localhost";
        this.port = "27017";
        this.dbName = "presidente";
        this.user = "admin";
        this.password = "admin";
        this.url = "mongodb://" + user + ":" + password + "@" + host + ":" + port;
    }

    public DB_Conection(String user, String password) {
        this("localhost", "27017", "presidente", user, password);
    }

    public DB_Conection(String host, String port, String dbName, String user, String password) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
        this.url = "mongodb://" + user + ":" + password + "@" + host + ":" + port;
    }

    public MongoClient createConnection() {
        System.out.println("Conectando a MongoDB en: " + url); // Depuración
        return MongoClients.create(url);
    }
}