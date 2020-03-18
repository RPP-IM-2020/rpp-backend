package rppim.reps;

import org.springframework.data.jpa.repository.JpaRepository;

import rppim.jpa.Artikl;

/*
 * Kao i kada želimo da neku postojeću klasu proširimo nekim novim metodama
 * i u ovom slučaju želimo da metode koje su deklarisane unutar interfejsa
 * JpaRepository proširimo nekim novim. Neke od metoda koje su deklarisane
 * u JpaRepository su:
 * List<T> findAll()
 * getOne(ID id);
 */
public interface ArtiklRepository extends JpaRepository<Artikl, Integer>{

}