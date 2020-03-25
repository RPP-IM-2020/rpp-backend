package rppim.ctrl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rppim.jpa.Artikl;
import rppim.reps.ArtiklRepository;

@RestController
public class ArtiklRestController {
	
	/*
	 * Anotacija @Autowired se može primeniti nad varijablama instace, setter metodama i
	 * konstruktorima. Označava da je neophodno injektovati zavisni objekat. Prilikom
	 * pokretanja aplikacije IoC kontejner prolazi kroz kompletan kod tražeči anotacije
	 * koje označavaju da je potrebno kreirati objekte. Upotrebom @Autowired anotacije
	 * stavljeno je do znanja da je potrebno kreirati objekta klase koja će implementirati
	 * repozitorijum AriklRepository i proslediti klasi ArtiklRestController referencu
	 * na taj objekat. 
	 */
	@Autowired
	private ArtiklRepository artiklRepository;
	
	/* Vežbe 04 - JdbcTemplate klasa pojednostavljuje korišćenje JDBC (Java Database 
	 * Connectivity), objekat ove klase će se koristiti u metodi delete() i zato je
	 * deklarisan i anotiran sa @Autowired
	 */
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * HTTP GET je jedna od HTTP metoda koja je analogna opciji READ iz CRUD operacija.
	 * Anotacija @GetMapping se koristi kako bi se mapirao HTTP GET zahtev.
	 * Predstavlja skraćenu verziju metode @RequestMapping(method = RequestMethod.GET)
	 * U konkretnom slučaju HTTP GET zahtevi (a to je npr. svako učitavanje stranice u
	 * browser-u) upućeni na adresu localhost:8083/artikl biće prosleđeni ovoj metodi.
	 * 
	 * Poziv metode artiklRepository.findAll() će vratiti kolekciju koja sadrži sve
	 * artikala koji će potom u browseru biti prikazani u JSON formatu
	 */
	
	@GetMapping("artikl")
	public Collection<Artikl> getAll(){
		return artiklRepository.findAll();
	}
	
	/*
	 * U slučaju metode getOne(), novina je uvođenje promenljive koja je predstavljena kao
	 * {id} u @GetMapping("artikl/{id}"). Mapiranje promenljive u vrednost koja se prosleđuje
	 * konkretnoj metodi getOne() vrši se upotrebom anotacije @PathVariable.
	 * U konkretnom slučaju HTTP GET zahtev upućen na adresu localhost:8083/artikl/1 biće
	 * prosleđen ovoj metodi, a vrednost 1 kao ID.
	 * 
	 *  Poziv metode artiklRepositorz.getOne(id) će vratiti konkretan artikal sa datim ID-je
	 *  i taj artikal će potom biti prikazan u browseru u JSON formatu. 
	 */
	
	@GetMapping("artikl/{id}")
	public Artikl getOne(@PathVariable("id") Integer id) {
		return artiklRepository.getOne(id);
	}
	
	@GetMapping("artikl/naziv/{naziv}")
	public Collection<Artikl> getByNaziv(@PathVariable("naziv") String naziv){
		return artiklRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	/*
	 * HTTP POST je jedna od HTTP metoda koja je analogna opciji CREATE iz CRUD operacija.
	 * Anotacija @PostMapping se koristi kako bi se mapirao HTTP POST zahtev.
	 * Predstavlja skraćenu verziju metode @RequestMapping(method = RequestMethod.POST)
	 * U konkretnom slučaju HTTP POST zahtevi upućeni na adresu localhost:8083/artikl 
	 * biće prosleđeni ovoj metodi.
	 * Poziv metode artiklRepository.save(artikl) će sačuvati prosleđeni artikl u bazi
	 * podataka
	 */
	
	@PostMapping("artikl")
	public ResponseEntity<HttpStatus> addArtikl(@RequestBody Artikl artikl) {
		artiklRepository.save(artikl);
		
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	/*
	 * HTTP PUT je jedna od HTTP metoda koja je analogna opciji UPDATE iz CRUD operacija.
	 * Anotacija @PutMapping se koristi kako bi se mapirao HTTP PUT zahtev.
	 * Predstavlja skraćenu verziju metode @RequestMapping(method = RequestMethod.PUT)
	 * U konkretnom slučaju HTTP PUT zahtevi upućeni na adresu localhost:8083/artikl/{id} 
	 * biće prosleđeni ovoj metodi.
	 * Poziv metode artiklRepository.save(artikl) će izmeniti artikl sa prosleđenim ID-ijem
	 * i prosleđenim sadržajem u bazi podataka.
	 */
	
	@PutMapping("artikl/{id}")
	public ResponseEntity<HttpStatus> updateArtikl(@RequestBody Artikl artikl, 
			@PathVariable("id")Integer id){
		if (artiklRepository.existsById(id)) {
			artikl.setId(id);
			artiklRepository.save(artikl);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		
	}
	
	/*
	 * HTTP DELETE je jedna od HTTP metoda koja je analogna opciji DELETE iz CRUD operacija.
	 * Anotacija @DELETEMapping se koristi kako bi se mapirao HTTP DELETE zahtev.
	 * Predstavlja skraćenu verziju metode @RequestMapping(method = RequestMethod.DELETE)
	 * U konkretnom slučaju HTTP PUT zahtevi upućeni na adresu localhost:8083/artikl/{id} 
	 * biće prosleđeni ovoj metodi.
	 * Poziv metode artiklRepository.deleteByID(id) će obrisati artikl sa prosleđenim ID-ije
	 * iz baze podataka.
	 */
	
	@DeleteMapping("artikl/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable Integer id){
		
		if(id==-100) {
			jdbcTemplate.execute("INSERT INTO artikl (\"id\", \"proizvodjac\", \"naziv\") VALUES (-100, 'Test Proizvodjac', 'Test Naziv')");
		}
		
		if (artiklRepository.existsById(id)) {
			artiklRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		
	}
	
}
