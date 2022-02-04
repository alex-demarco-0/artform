package it.artform.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.artform.database.ArtformRepository;
import it.artform.pojos.*;

public class ArtformRESTController {
	
	@Autowired
	ArtformRepository artformRepository = null;
	
	@RequestMapping(value="/artform/utente/{id}", method=RequestMethod.GET)
	public ResponseEntity<Utente> getUtenteById(@PathVariable int id) {
		Utente u = this.artformRepository.findUtenteById(id);
		System.out.println(u);
		if(u != null)
			return new ResponseEntity<Utente>(u, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}", method=RequestMethod.GET)
	public ResponseEntity<Utente> getUtenteByUsername(@PathVariable String username) {
		Utente u = this.artformRepository.findUtenteByUsername(username);
		if(u != null)
			return new ResponseEntity<Utente>(u, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente", method=RequestMethod.POST)
	public ResponseEntity<String> addPlayer(@RequestBody Utente newUtente) {
		if(this.artformRepository.saveUtente(newUtente) == 1)
			return new ResponseEntity<String>("OK", HttpStatus.CREATED);
		return new ResponseEntity<String>("KO", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/artform/utente/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Utente> updateUtente(@PathVariable int id, @RequestBody Utente modUtente) {
		Utente u = this.artformRepository.findUtenteById(id);
		if(u != null) {
			if(modUtente.getNome() != null && !modUtente.getNome().isBlank())
				u.setNome(modUtente.getNome());
			if(modUtente.getCognome() != null && !modUtente.getCognome().isBlank())
				u.setCognome(modUtente.getCognome());
			if(modUtente.getUsername() != null && !modUtente.getUsername().isBlank())
				u.setUsername(modUtente.getUsername());
			if(modUtente.getEmail() != null && !modUtente.getEmail().isBlank())
				u.setEmail(modUtente.getEmail());
			if(modUtente.getNumeroTelefono() != null && !modUtente.getNumeroTelefono().isBlank())
				u.setNumeroTelefono(modUtente.getNumeroTelefono());
			if(modUtente.getPassword() != null && !modUtente.getPassword().isBlank())
				u.setPassword(modUtente.getPassword());
			if(modUtente.getPunteggio() >= u.getPunteggio())
				u.setPunteggio(modUtente.getPunteggio());
			if(this.artformRepository.updateUtente(u) == 1)
				return new ResponseEntity<Utente>(u, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteUtente(@PathVariable int id) {
		if(this.artformRepository.deleteUtente(id) == 1)
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
	}
	
	
	
	@RequestMapping(value="/artform/utente/{id}/commissioni", method=RequestMethod.GET)
	public List<Commissione> getAllCommissioni(@PathVariable int utenteId) {
		return this.artformRepository.findAllCommissioni(utenteId);
	}

}
