package rppim.reps;

import java.util.Collection;

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

/* Vežbe 04 - JPA omogućava manuelno kreiranje upita, kao što je npr. kreiran upit
 * sa anotacijom @NamedQuery u klasi Artikal, ali i kreiranje upita na osnovu naziva
 * metoda. Postoji određen broj rezervisanih reči koje možete koristiti u nazivima 
 * metode, a koje če omogućiti da se iz samog naziva automatski generiše upit. 
 * Neke od rezervisanih reči su npr. StartsWith, EndsWith, NotContaining, Containing, 
 * Contains. And, After, Like, OrderBy itd. U konkretnom slučaju upotrebom rezervisanih 
 * reči definisana je metoda koja će pronalaziti sve artikle koja u nezivu sadrži prosleđeni 
 * String ingorišući da li ja napisan malim ili valikim slovima.
 */

public interface ArtiklRepository extends JpaRepository<Artikl, Integer>{
	
	Collection<Artikl> findByNazivContainingIgnoreCase(String naziv);

}