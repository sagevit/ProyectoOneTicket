package es.jmdedios.proyectooneticket.component;

import es.jmdedios.proyectooneticket.model.Comentario;
import es.jmdedios.proyectooneticket.model.Ticket;
import es.jmdedios.proyectooneticket.repository.IGeneradorSecuencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class ComentarioModelListener extends AbstractMongoEventListener<Comentario> {

    @Autowired
    private IGeneradorSecuencia generatorSecuencia;

    /**
     * Method is triggered on event of inserting a new document in collection
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Comentario> event) {
        try {
            if (event.getSource().getSecuencia() == 0L) {
                event.getSource().setSecuencia(generatorSecuencia.generarSecuencia(Comentario.SEQUENCE_NAME));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
