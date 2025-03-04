package com.pedro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.pedro.model.Pais;
import org.bson.Document;

import java.io.File;
import java.io.IOException;

/**
 * Clase con los metodos CRUD del pais
 */
public class PaisService {
    /**
     * Metodo para insertar un pais
     * @param pais
     * @param collection
     */
    public static void insertPais(Pais pais, MongoCollection<Document> collection) {
        Document insert = new Document("nome", pais.getNome())
                .append("organizacion", pais.getOrganizacion())
                .append("partidos", pais.getPartidos())
                .append("id_presidente", pais.getId_presidente());
        collection.insertOne(insert);
    }

    /**
     * Metodo para eliminar un pais
     * @param nome
     * @param collection
     */
    public static void deleteCountry(String nome, MongoCollection<Document> collection) {
        Document delete = new Document("nome", nome);
        collection.deleteOne(delete);
    }

    /**
     * Metodo que guarda un Pais en un archivo JSON en el directorio JSON
     * @param pais
     * @param directoryPath
     */
    public static void savePaisToJson(Pais pais, String directoryPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, pais.getNome() + ".json");
            objectMapper.writeValue(file, pais);
            System.out.println("Pais saved to JSON file: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Metodo que muestra todos los paises por consola
     */
    public static void showAllPaises(MongoCollection<Document> collection) {
        FindIterable<Document> paises = collection.find();
        for (Document pais : paises) {
            System.out.println(pais.toJson());
        }
    }


    public static void updatePais(Pais pais, String newNome, String newOrganizacion, String newPartidos, MongoCollection<Document> collection) {
        Document query = new Document("nome", newNome);
        Document update = new Document("$set", new Document("organizacion", newOrganizacion)
                .append("partidos", newPartidos)
                .append("id_presidente", pais.getId_presidente()));
        collection.updateOne(query, update);
        System.out.println("Pais updated: " + pais.getNome());
    }

}