package org.example;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;

public class Main {
    public static void main(String[] args) {
        //INSERTAR
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) { //me conecto
            MongoDatabase db = mongoClient.getDatabase("BaseDeDatos"); // crea base
            MongoCollection<Document> collection = db.getCollection("miColeccion"); //crea coleccion o tabla
            Document doc = new Document("nombre", "Raul")//Insersion de datos
                    .append("apellido", "Cortez")
                    .append("edad", 31);
            collection.insertOne(doc);
            System.out.println("Documento insertado con éxito");
        }
        //leer

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            MongoDatabase database = mongoClient.getDatabase(   "miBaseDeDatos");
            MongoCollection<Document> collection = database.getCollection("miColeccion");

            FindIterable<Document> documentos = collection.find();

            for (Document documento : documentos) {
                //System.out.println(documento.toJson());
                String nombre = documento.getString("nombre");
                String apellido = documento.getString("apellido");
                int edad = documento.getInteger("edad");

                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + apellido);
                System.out.println("Edad: " + edad);
            }
        }

        //actualizar
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("miBaseDeDatos");
            MongoCollection<Document> collection = database.getCollection("miColeccion");
            Document filtro = new Document("nombre", "Yadira");
            Document actualizacion = new Document("$set", new Document("apellido", "Gómez"));
            UpdateResult resultado = collection.updateOne(filtro, actualizacion);
            System.out.println("Documentos modificados: " + resultado.getModifiedCount());
        }

        //borrar
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            MongoDatabase database = mongoClient.getDatabase("miBaseDeDatos");
            MongoCollection<Document> collection = database.getCollection("miColeccion");
            Document filtro = new Document("nombre", "Juan");
            DeleteResult resultado = collection.deleteOne(filtro);
            System.out.println("Documentos borrados: " + resultado.getDeletedCount());
        }
    }
}
