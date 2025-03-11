package com.pedro.service;

import com.pedro.model.Presidente;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.IOException;

/**
 * Clase con los métodos CRUD para la colección Presidente en MongoDB.
 */
public class PresidenteService {
    /**
     * Inserta un nuevo presidente en la colección.
     * @param presidente Objeto Presidente a insertar.
     * @param collection Colección MongoDB.
     */
    public static void insertarPresidente(Presidente presidente, MongoCollection<Document> collection) {
        Document insert = new Document("_id", presidente.getId())
                .append("nome", presidente.getNome())
                .append("idade", presidente.getIdade())
                .append("partido", presidente.getPartido());
        collection.insertOne(insert);
    }

    /**
     * Obtiene el ObjectId de un presidente a partir de su nombre.
     * @param nome Nombre del presidente.
     * @param collection Colección MongoDB.
     * @return ObjectId del presidente, o null si no se encuentra.
     */
    public static ObjectId obtenerIdPresidentePorNombre(String nome, MongoCollection<Document> collection) {
        Document query = new Document("nome", nome);
        Document result = collection.find(query).first();
        return (result != null) ? result.getObjectId("_id") : null;
    }

    /**
     * Obtiene un objeto Presidente a partir de su ObjectId.
     * @param id ObjectId del presidente.
     * @param collection Colección MongoDB.
     * @return Objeto Presidente, o null si no se encuentra.
     */
    public static Presidente obtenerPresidentePorId(ObjectId id, MongoCollection<Document> collection) {
        Document query = new Document("_id", id);
        Document result = collection.find(query).first();
        if (result != null) {
            return new Presidente(result.getString("nome"), result.getInteger("idade"), result.getString("partido"));
        }
        return null;
    }

    /**
     * Elimina un presidente de la colección.
     * @param nome Nombre del presidente a eliminar.
     * @param collection Colección MongoDB.
     */
    public static void eliminarPresidente(String nome, MongoCollection<Document> collection) {
        Document delete = new Document("nome", nome);
        collection.deleteOne(delete);
    }

    /**
     * Guarda un objeto Presidente en un archivo JSON.
     * @param presidente Objeto Presidente a guardar.
     * @param directorio Ruta del directorio donde se guardará el JSON.
     */
    public static void guardarPresidenteJson(Presidente presidente, String directorio) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File dir = new File(directorio);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File archivo = new File(dir, presidente.getNome() + ".json");
            objectMapper.writeValue(archivo, presidente);
            System.out.println("👤 Presidente guardado en JSON: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    /**
     * Muestra todos los presidentes almacenados en la base de datos.
     */
    public static void mostrarPresidentes(MongoCollection<Document> collection) {
        FindIterable<Document> presidentes = collection.find();
        for (Document presidente : presidentes) {
            System.out.println(presidente.toJson());
        }
    }

    /**
     * Modifica un presidente en la base de datos.
     * @param id ObjectId del presidente a modificar.
     * @param nuevoNome Nuevo nombre del presidente.
     * @param nuevaIdade Nueva edad del presidente.
     * @param nuevoPartido Nuevo partido del presidente.
     * @param collection Colección MongoDB.
     */
    public static void actualizarPresidente(ObjectId id, String nuevoNome, int nuevaIdade, String nuevoPartido, MongoCollection<Document> collection) {
        Document query = new Document("_id", id);
        Document update = new Document("$set", new Document("nome", nuevoNome)
                .append("idade", nuevaIdade)
                .append("partido", nuevoPartido));
        collection.updateOne(query, update);
        System.out.println("✅ Presidente actualizado correctamente.");
    }
}
