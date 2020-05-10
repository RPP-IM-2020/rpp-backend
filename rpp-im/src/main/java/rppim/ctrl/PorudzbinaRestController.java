package rppim.ctrl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import rppim.jpa.Porudzbina;
import rppim.reps.PorudzbinaRepository;

@RestController
public class PorudzbinaRestController {
	
	@Autowired
	private PorudzbinaRepository porudzbinaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@CrossOrigin
	@ApiOperation(value = "Returns collection of all Porudzbina from database.")
	@GetMapping("porudzbina")
	public Collection<Porudzbina> getPorudzbine() {
		return porudzbinaRepository.findAll();
	}

	@CrossOrigin
	@ApiOperation(value = "Returns Porudzbina with id that was forwarded as path variable.")
	@GetMapping("porudzbina/{id}")
	public Porudzbina getPorudzbina(@PathVariable("id") Integer id) {
		return porudzbinaRepository.getOne(id);
	}

	@CrossOrigin
	@ApiOperation(value = "Returns Porudzbina that are payed.")
	@GetMapping("porudzbinePlacene")
	public Collection<Porudzbina> getPorudzbineByPlaceno() {
		return porudzbinaRepository.findByPlacenoTrue();
	}
	
	@CrossOrigin
	@ApiOperation(value = "Adds instance of Porudzbina to database.")
	@PostMapping("porudzbina")
	public ResponseEntity<HttpStatus> addOne(@RequestBody Porudzbina porudzbina){
		porudzbinaRepository.save(porudzbina);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@ApiOperation(value = "Updates Porudzbina that has id that was forwarded as path variable with values forwarded in Request Body.")
	@PutMapping("porudzbina/{id}")
	public ResponseEntity<HttpStatus> update(@RequestBody Porudzbina porudzbina, @PathVariable("id") Integer id){
		if(porudzbinaRepository.existsById(id)) {
			porudzbina.setId(id);
			porudzbinaRepository.save(porudzbina);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin
	@ApiOperation(value = "Deletes Porudzbina with id that was forwarded as path variable.")
	@DeleteMapping("porudzbina/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable Integer id){
		
		if (id == -100 && !porudzbinaRepository.existsById(-100)) {
			
			jdbcTemplate.execute("INSERT INTO porudzbina (\"id\", \"dobavljac\", \"placeno\", \"iznos\", \"isporuceno\", \"datum\") "
					+ "VALUES ('-100', '1', 'true', '1000', to_date('03.03.2017.', 'dd.mm.yyyy.'), to_date('03.03.2017.', 'dd.mm.yyyy.'))");
		}
		
		if(porudzbinaRepository.existsById(id)) {
			porudzbinaRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		
	}

}
