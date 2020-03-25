package rppim.reps;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rppim.jpa.Artikl;
import rppim.jpa.Dobavljac;

public interface DobavljacRepository extends JpaRepository<Dobavljac, Integer>{

	Collection<Dobavljac> findByNazivContainingIgnoreCase(String naziv);
	
}
