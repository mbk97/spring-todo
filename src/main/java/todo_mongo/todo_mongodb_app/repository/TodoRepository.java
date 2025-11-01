package todo_mongo.todo_mongodb_app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import todo_mongo.todo_mongodb_app.model.Todo;

//? defines how your application interacts with the MongoDB collection that stores the Todo items.Itextends a Spring Data interface like MongoRepository or CrudRepository, which gives you built-in methods to perform CRUD (Create, Read, Update, Delete) operations — without writing any SQL or queries manually.
@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
}
