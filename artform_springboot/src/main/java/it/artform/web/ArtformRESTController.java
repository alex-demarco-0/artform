package it.artform.web;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.artform.database.ArtformRepository;
import it.artform.pojos.*;

@RestController
public class ArtformRESTController {
	
	@Autowired
	ArtformRepository artformRepository;
	
	/* 
	 * Utente
	 */
	
	@RequestMapping(value="/artform/utente/{username}", method=RequestMethod.GET)
	public ResponseEntity<Utente> getUtente(@PathVariable String username) {
		Utente u = this.artformRepository.findUtente(username);
		if(u != null)
			return new ResponseEntity<Utente>(u, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente_email/{email}", method=RequestMethod.GET) //potrebbe ritornare solo String
	public ResponseEntity<Utente> getUtenteByEmail(@PathVariable String email) {
		Utente u = this.artformRepository.findUtenteByEmail(email);
		if(u != null)
			return new ResponseEntity<Utente>(u, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente", method=RequestMethod.POST)
	public ResponseEntity<Utente> addUtente(@RequestBody Utente newUtente) {
		if(this.artformRepository.saveUtente(newUtente) == 1)
			return new ResponseEntity<Utente>(newUtente, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value="/artform/utente/{username}", method=RequestMethod.PUT)
	public ResponseEntity<Utente> updateUtente(@PathVariable String username, @RequestBody Utente modUtente) {
		Utente u = this.artformRepository.findUtente(username);
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
			if(modUtente.getBio() != null && !modUtente.getBio().isBlank())
				u.setBio(modUtente.getBio());
			if(modUtente.getPunteggio() >= u.getPunteggio())
				u.setPunteggio(modUtente.getPunteggio());
			if(modUtente.getImmagineProfiloSrc() != null && !modUtente.getImmagineProfiloSrc().isBlank())
				u.setImmagineProfiloSrc(modUtente.getImmagineProfiloSrc());
			if(this.artformRepository.updateUtente(u) == 1)
				return new ResponseEntity<Utente>(u, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteUtente(@PathVariable String username) {
		if(this.artformRepository.deleteUtente(username) == 1)
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
	}
	
	/* 
	 * Post
	 */
	
	@RequestMapping(value="/artform/post/{id}", method=RequestMethod.GET)
	public ResponseEntity<Post> getPost(@PathVariable int id) {
		Post p = this.artformRepository.findPost(id);
		if(p != null)
			return new ResponseEntity<Post>(p, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	///
	
	@RequestMapping(value="/artform/utente/{username}/commissioni", method=RequestMethod.GET)
	public List<Commissione> getAllCommissioni(@PathVariable String username) {
		return this.artformRepository.findCommissioniByArtista(username);
	}

}
