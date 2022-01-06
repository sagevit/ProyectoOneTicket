package es.jmdedios.proyectooneticket.repository;

import java.util.concurrent.ExecutionException;

public interface IGeneradorSecuencia {

    long generarSecuencia (final String nombreSecuencia) throws ExecutionException, InterruptedException;

}
