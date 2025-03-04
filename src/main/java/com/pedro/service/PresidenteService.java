package com.pedro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import com.pedro.model.Presidente;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.IOException;

/**
 * Clase con los metodos CRUD del presidente
 */
public class PresidenteService {
    /**
     * Metodo para insertar un presidente
     * @param presidente
     */
    public static void insertPresidente(Presidente presidente, MongoCollection<Document> collection) {
        Document insert = new Document("_id", presidente.getId())
                .append("nome", presidente.getNome())
                .append("idade", presidente.getIdade())
                .append("partido", presidente.getPartido());
        collection.insertOne(insert);
    }

    public static ObjectId getPresidentIdByName(String nome, MongoCollection<Document> collection) {
        Document query = new Document("nome", nome);
        Document result = collection.find(query).first();
        if (result != null) {
            return result.getObjectId("_id");
        }
        return null;
    }

    public static Presidente getPresidenteById(ObjectId id, MongoCollection<Document> collection) {
        Document query = new Document("_id", id);
        Document result = collection.find(query).first();
        if (result != null) {
            return new Presidente(result.getString("nome"), result.getInteger("idade"), result.getString("partido"));
        }
        return null;
    }

    /**
     * Metodo para eliminar un presidente
     * @param nome
     */
    public static void deletePresident(String nome, MongoCollection<Document> collection) {
        Document delete = new Document("nome", nome);
        collection.deleteOne(delete);
    }

    /**
     * Metodo para guardar un presidente en un archivo JSON
     * @param presidente
     */
    public static void savePresidenteToJson(Presidente presidente, String directoryPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, presidente.getNome() + ".json");
            objectMapper.writeValue(file, presidente);
            System.out.println("President saved to JSON file: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());;
        }
    }

    /**
     * Metodo que muestra todos los presidentes por consola
     */
    public static void showAllPresidentes(MongoCollection<Document> collection) {
        FindIterable<Document> presidentes = collection.find();
        for (Document presidente : presidentes) {
            System.out.println(presidente.toJson());
        }
    }

    public static void updatePresidente(Presidente presidente, String newNome, int newIdade, String newPartido, MongoCollection<Document> collection) {
        Document query = new Document("_id", presidente.getId());
        Document update = new Document("$set", new Document("nome", newNome)
                .append("idade", newIdade)
                .append("partido", newPartido));
        collection.updateOne(query, update);
        System.out.println("Presidente actualizado: " + presidente.getNome());
    }


}