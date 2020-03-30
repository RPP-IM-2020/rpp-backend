package rppim.ctrl;

import java.math.BigDecimal;
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

import io.swagger.annotations.ApiOperation;
import rppim.jpa.Porudzbina;
import rppim.jpa.StavkaPorudzbine;
import rppim.reps.PorudzbinaRepository;
import rppim.reps.StavkaPorudzbineRepository;

@RestController
public class StavakPoruzbineRestController {
	
	@Autowired
	private StavkaPorudzbineRepository stavkaPorudzbineRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PorudzbinaRepository porudzbinaRepository;
	
	@ApiOperation(value = "Returns collection of all StavkaPorudzbine from database.")
	@GetMapping("stavkaPorudzbine")
	public Collection<StavkaPorudzbine> getAll() {
		return stavkaPorudzbineRepository.findAll();
	}
	
	@ApiOperation(value = "Returns StavkaPorudzbine with id that was forwarded as path variable.")
	@GetMapping("stavkaPorudzbine/{id}")
	public StavkaPorudzbine getOne(@PathVariable("id") Integer id) {
		return stavkaPorudzbineRepository.getOne(id);
	}
	
	@ApiOperation(value = "Returns collection of StavkePorudzbine for Porudzbina with id that was forwarded as path variable.")
	@GetMapping("stavkaZaPorudzbinu/{id}")
	public Collection<StavkaPorudzbine> getAllForPorudzbina(@PathVariable("id") Integer id){
		Porudzbina p = porudzbinaRepository.getOne(id);
		return stavkaPorudzbineRepository.findByPorudzbina(p);
		
	}
	
	@ApiOperation(value = "Returns collection of StavkePorudzbine with price that is lower then price that was forwarded as path variable.")
	@GetMapping(value = "stavkaPorudzbineCena/{cena}")
	public Collection<StavkaPorudzbine> getStavkaPorudzbineCena(@PathVariable("cena") BigDecimal cena){
		return stavkaPorudzbineRepository.findByCenaLessThanOrderById(cena);
	}
	
	@ApiOperation(value = "Adds instance of StavkaPorudzbine to database.")
	@PostMapping("stavkaPorudzbine")
	public ResponseEntity<HttpStatus> addOne(@RequestBody StavkaPorudzbine stavkaPorudzbine){
		stavkaPorudzbine.setRedniBroj(stavkaPorudzbineRepository.nextRBr(stavkaPorudzbine.getPorudzbina().getId()));
		stavkaPorudzbineRepository.save(stavkaPorudzbine);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Updates StavkaPorudzbine that has id that was forwarded as path variable with values forwarded in Request Body.")
	@PutMapping("stavkaPorudzbine/{id}")
	public ResponseEntity<HttpStatus> updateOne(@RequestBody StavkaPorudzbine stavkaPorudzbine, @PathVariable("id") Integer id){
		if (!stavkaPorudzbineRepository.existsById(id)) {
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}
		stavkaPorudzbine.setId(id);
		stavkaPorudzbineRepository.save(stavkaPorudzbine);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Deletes StavkaPorudzbine with id that was forwarded as path variable.")
	@DeleteMapping("stavkaPorudzbine/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable Integer id){
		if (id == -100 && !stavkaPorudzbineRepository.existsById(-100)) {
            
            jdbcTemplate.execute("INSERT INTO stavka_porudzbine (\"id\", \"redni_broj\", \"kolicina\", \"jedinica_mere\", \"cena\", \"porudzbina\", \"artikl\") "
                    + "VALUES ('-100', '100', '1', 'kom', '1', '1', '1')");
        }
		
		if(stavkaPorudzbineRepository.existsById(id)) {
            stavkaPorudzbineRepository.deleteById(id); 
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
         
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

}
