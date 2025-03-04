package com.pedro.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pedro.controller.dbConection.DB_Conection;
import com.pedro.model.Pais;
import com.pedro.model.Presidente;
import com.pedro.service.PaisService;
import com.pedro.service.PresidenteService;
import org.bson.Document;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    public static final int PRESIDENTE_COLLECTION = 0;
    public static final int PAIS_COLLECTION = 1;

    MongoClient mongoClient;
    MongoDatabase database;
    ArrayList<MongoCollection<Document>> collections = new ArrayList<>();
    String rutaJSON = "JSON";

    public Controller() {
        DB_Conection db = new DB_Conection();
        mongoClient = db.createConnection();
        database = mongoClient.getDatabase("PracticaMongoDB_02");
        collections.add(database.getCollection("presidentes"));
        collections.add(database.getCollection("paises"));
    }

    public void iniciarApp() {
        pintarMenu();
    }

    public void pintarMenu() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog("Introduce una opción: "
                + "\n1. Insertar presidente"
                + "\n2. Insertar país"
                + "\n3. Borrar presidente"
                + "\n4. Borrar país"
                + "\n5. Mostrar todos los presidentes"
                + "\n6. Mostrar todos los países"
                + "\n7. Modificar presidente"
                + "\n8. Modificar país"
                + "\n9. Salir"));

        switch (opcion){
            case 1:
                insertarPresidente();
                pintarMenu();
                break;
            case 2:
                insertarPais();
                pintarMenu();
                break;
            case 3:
                borrarPresidente();
                pintarMenu();
                break;
            case 4:
                borrarPais();
                pintarMenu();
                break;
            case 5:
                PresidenteService.showAllPresidentes(collections.get(PRESIDENTE_COLLECTION));
                pintarMenu();
                break;
            case 6:
                PaisService.showAllPaises(collections.get(PAIS_COLLECTION));
                pintarMenu();
                break;
            case 7:
                modificarPresidente();
                pintarMenu();
                break;
            case 8:
                modificarPais();
                pintarMenu();
                break;
            case 9:
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida");
                pintarMenu();
                break;
        }
    }

    public void insertarPresidente(){
        String nome = JOptionPane.showInputDialog("Introduce el nombre del presidente");
        int idade = Integer.parseInt(JOptionPane.showInputDialog("Introduce la edad del presidente"));
        String partido = JOptionPane.showInputDialog("Introduce el partido del presidente");
        Presidente presidente = new Presidente(nome, idade, partido);
        PresidenteService.insertPresidente(presidente, collections.get(PRESIDENTE_COLLECTION));
        System.out.println("Presidente insertado");
        PresidenteService.savePresidenteToJson(presidente, rutaJSON);
        pintarMenu();
    }

    public void insertarPais(){
        String nome = JOptionPane.showInputDialog("Introduce el nombre del país");
        String organizacion = JOptionPane.showInputDialog("Introduce la organización del país");
        String partido = JOptionPane.showInputDialog("Introduce el partido del país");
        String presidenteNome = JOptionPane.showInputDialog("Introduce el nombre del presidente del país");
        Presidente presidente = PresidenteService.getPresidenteById(PresidenteService.getPresidentIdByName(presidenteNome, collections.get(PRESIDENTE_COLLECTION)), collections.get(PRESIDENTE_COLLECTION));
        Pais pais = new Pais(nome, organizacion, new String[]{partido}, presidente.getIdade());
        PaisService.insertPais(pais, collections.get(PAIS_COLLECTION));
        PaisService.savePaisToJson(pais, rutaJSON);

        System.out.println("País insertado");
        pintarMenu();
    }

    public void modificarPresidente(){
        String name = JOptionPane.showInputDialog("Introduce el nombre del presidente a modificar");
        Presidente presidente = PresidenteService.getPresidenteById(PresidenteService.getPresidentIdByName(name, collections.get(PRESIDENTE_COLLECTION)), collections.get(PRESIDENTE_COLLECTION));
        String newNome = JOptionPane.showInputDialog("Introduce el nuevo nombre del presidente");
        int newIdade = Integer.parseInt(JOptionPane.showInputDialog("Introduce la nueva edad del presidente"));
        String newPartido = JOptionPane.showInputDialog("Introduce el nuevo partido del presidente");
        PresidenteService.updatePresidente(presidente,newNome, newIdade, newPartido, collections.get(PRESIDENTE_COLLECTION));
        System.out.println("Presidente modificado");
        pintarMenu();
    }

    public void modificarPais(){
        String name = JOptionPane.showInputDialog("Introduce el nombre del país a modificar");
        String newName = JOptionPane.showInputDialog("Introduce el nuevo nombre del país");
        String newOrganization = JOptionPane.showInputDialog("Introduce la nueva organización del país");
        String newParties = JOptionPane.showInputDialog("Introduce el nuevo partido del país");
        Pais pais;
        for (Document doc : collections.get(PAIS_COLLECTION).find()) {
            if (doc.getString("name").equals(name)) {
                pais = new Pais(doc.getString("name"), doc.getString("organization"), new String[]{doc.getString("parties")}, PresidenteService.getPresidenteById(doc.getObjectId("president_id"), collections.get(PRESIDENTE_COLLECTION)).getIdade());
                PaisService.updatePais(pais, newName, newOrganization, Arrays.toString(new String[]{newParties}), collections.get(PAIS_COLLECTION));
            }
        }
        System.out.println("País modificado");
        pintarMenu();
    }

    public void borrarPresidente(){
        String name = JOptionPane.showInputDialog("Introduce el nombre del presidente a borrar");
        PresidenteService.deletePresident(name, collections.get(PRESIDENTE_COLLECTION));
        System.out.println("Presidente borrado");
        pintarMenu();
    }

    public void borrarPais(){
        String name = JOptionPane.showInputDialog("Introduce el nombre del país a borrar");
        PaisService.deleteCountry(name, collections.get(PAIS_COLLECTION));
        System.out.println("País borrado");
        pintarMenu();
    }

}