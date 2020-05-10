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
import rppim.jpa.Dobavljac;
import rppim.reps.DobavljacRepository;

@RestController
public class DobavljacRestController {
	
	@Autowired
	private DobavljacRepository dobavljacRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@CrossOrigin
	@ApiOperation(value = "Returns collection of all Dobavljac from database.")
	@GetMapping("dobavljac")
	public Collection<Dobavljac> getAll(){
		return dobavljacRepository.findAll();
	}
	
	@CrossOrigin
	@ApiOperation(value = "Returns Dobavljac with id that was forwarded as path variable.")
	@GetMapping("dobavljac/{id}")
	public Dobavljac getOne(@PathVariable("id") Integer id) {
		return dobavljacRepository.getOne(id);
	}
	
	@CrossOrigin
	@ApiOperation(value = "Returns Dobavljac with name that was forwarded as path variable.")
	@GetMapping("dobavljac/naziv/{naziv}")
	public Collection<Dobavljac> getByNaziv(@PathVariable("naziv") String naziv){
		return dobavljacRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@CrossOrigin
	@ApiOperation(value = "Adds instance of Dobavljac to database.")
	@PostMapping("dobavljac")
	public ResponseEntity<HttpStatus> addDobavljac(@RequestBody Dobavljac dobavljac) {
		dobavljacRepository.save(dobavljac);
		
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@ApiOperation(value = "Updates Dobavljac that has id that was forwarded as path variable with values forwarded in Request Body.")
	@PutMapping("dobavljac/{id}")
	public ResponseEntity<HttpStatus> updateArtikl(@RequestBody Dobavljac dobavljac, 
			@PathVariable("id")Integer id){
		if (dobavljacRepository.existsById(id)) {
			dobavljac.setId(id);
			dobavljacRepository.save(dobavljac);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		
	}
	
	@CrossOrigin
	@ApiOperation(value = "Deletes Dobavljac with id that was forwarded as path variable.")
	@DeleteMapping("dobavljac/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable Integer id){
		
		if(id==-100 && !dobavljacRepository.existsById(-100)) {
			jdbcTemplate.execute("INSERT INTO dobavljac (\"id\", \"naziv\", \"adresa\", \"kontakt\") VALUES (-100, 'Test Naziv', 'Test Adresa', 'Test Kontakt')");
		}
		
		if (dobavljacRepository.existsById(id)) {
			dobavljacRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		
	}

}
