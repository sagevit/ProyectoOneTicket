package es.jmdedios.proyectooneticket.model;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    @NonNull
    private String codigo;

    @NonNull
    private String nombre;

    @NonNull
    private String email;

}
