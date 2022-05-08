package es.jmdedios.proyectooneticket.component;

import es.jmdedios.proyectooneticket.model.Ticket;
import es.jmdedios.proyectooneticket.repository.IGeneradorSecuencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class TicketModelListener extends AbstractMongoEventListener<Ticket> {

    @Autowired
    private IGeneradorSecuencia generatorSecuencia;

    /**
     * Method is triggered on event of inserting a new document in collection
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Ticket> event) {
        try {
            if (event.getSource().getSecuencia() == 0) {
                event.getSource().setSecuencia(generatorSecuencia.generarSecuencia(Ticket.SEQUENCE_NAME));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
