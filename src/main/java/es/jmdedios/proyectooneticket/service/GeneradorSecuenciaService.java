package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.Secuencia;
import es.jmdedios.proyectooneticket.repository.IGeneradorSecuencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class GeneradorSecuenciaService implements IGeneradorSecuencia {

    @Autowired
    private ReactiveMongoOperations mongoOperations;

    @Override
    public long generarSecuencia(final String nombreSecuencia) throws ExecutionException, InterruptedException {
        return mongoOperations.findAndModify(new Query(Criteria.where("_id").is(nombreSecuencia)),
                new Update().inc("secuencia", 1), Secuencia.class).doOnSuccess(object -> {
        }).toFuture().get().getSecuencia();
    }

}
