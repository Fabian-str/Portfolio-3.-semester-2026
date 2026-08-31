package app.daos;

import app.entities.Playthrough;

import java.util.List;

public interface PlaythroughDAO {

    Playthrough create(Playthrough playthrough);

    Playthrough findById(Long id);

    List<Playthrough> findAll();

    Playthrough update(Playthrough playthrough);

    void delete(Long id);
}