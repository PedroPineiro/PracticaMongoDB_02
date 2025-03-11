package com.pedro.service;

import com.pedro.model.Pais;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.IOException;

/**
 * Clase con los métodos CRUD para la colección Pais en MongoDB.
 */
public class PaisService {
    /**
     * Inserta un nuevo país en la colección.
     * @param pais Objeto Pais a insertar.
     * @param collection Colección MongoDB.
     */
    public static void insertarPais(Pais pais, MongoCollection<Document> collection) {
        Document insert = new Document("nome", pais.getNome())
                .append("organizacion", pais.getOrganizacion())
                .append("partidos", pais.getPartidos())
                .append("id_presidente", pais.getId_presidente()); // ID del presidente
        collection.insertOne(insert);
    }

    /**
     * Elimina un país de la colección.
     * @param nome Nombre del país a eliminar.
     * @param collection Colección MongoDB.
     */
    public static void eliminarPais(String nome, MongoCollection<Document> collection) {
        Document delete = new Document("nome", nome);
        collection.deleteOne(delete);
    }

    /**
     * Guarda un objeto Pais en un archivo JSON.
     * @param pais Objeto Pais a guardar.
     * @param directorio Ruta del directorio donde se guardará el JSON.
     */
    public static void guardarPaisJson(Pais pais, String directorio) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File dir = new File(directorio);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File archivo = new File(dir, pais.getNome() + ".json");
            objectMapper.writeValue(archivo, pais);
            System.out.println("🌍 País guardado en JSON: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    /**
     * Muestra todos los países almacenados en la base de datos.
     */
    public static void mostrarPaises(MongoCollection<Document> collection) {
        FindIterable<Document> paises = collection.find();
        for (Document pais : paises) {
            System.out.println(pais.toJson());
        }
    }

    /**
     * Modifica un país en la base de datos.
     * @param id ID del país a modificar.
     * @param nuevaOrganizacion Nueva organización política del país.
     * @param nuevosPartidos Nuevo array de partidos políticos.
     * @param collection Colección MongoDB.
     */
    public static void actualizarPais(ObjectId id, String nuevaOrganizacion, String[] nuevosPartidos, MongoCollection<Document> collection) {
        Document query = new Document("_id", id);
        Document update = new Document("$set", new Document("organizacion", nuevaOrganizacion)
                .append("partidos", nuevosPartidos));
        collection.updateOne(query, update);
        System.out.println("✅ País actualizado correctamente.");
    }
}
