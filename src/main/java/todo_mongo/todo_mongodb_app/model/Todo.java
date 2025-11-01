package todo_mongo.todo_mongodb_app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todo")
public class Todo {

    @Id
    public String id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    public String title;

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 50, message = "Description must be between 3 and 1000 characters")
    public String description;

    @NotNull(message = "Done status is required")
    public boolean done;
}
