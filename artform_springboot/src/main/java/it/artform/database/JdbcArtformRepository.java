package it.artform.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.artform.pojos.Badge;
import it.artform.pojos.Commissione;
import it.artform.pojos.Notifica;
import it.artform.pojos.Post;
import it.artform.pojos.Utente;

@Repository
public class JdbcArtformRepository implements ArtformRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public int saveUtente(Utente u) {
		return jdbcTemplate.update("INSERT INTO utente (nome, cognome, username, email, numeroTelefono, password) VALUES (?, ?, ?, ?, ?, ?)",
				new Object[] {u.getNome(), u.getCognome(), u.getUsername(), u.getEmail(), u.getNumeroTelefono(), u.getPassword()});
	}

	@Override
	public Utente findUtenteById(long id) {
		return jdbcTemplate.queryForObject("SELECT * from utente WHERE ID=?", BeanPropertyRowMapper.newInstance(Utente.class), id);
	}
	
	@Override
	public Utente findUtenteByUsername(String username) {
		return jdbcTemplate.queryForObject("SELECT * from utente WHERE username=?", BeanPropertyRowMapper.newInstance(Utente.class), username);
	}

	@Override
	public int updateUtente(Utente u) {
		return jdbcTemplate.update("UPDATE utente SET nome=?, cognome=?, username=?, email=?, numeroTelefono=?, password=?, punteggio=? WHERE ID=?",
				new Object[] {u.getNome(), u.getCognome(), u.getUsername(), u.getEmail(), u.getNumeroTelefono(), u.getPassword(), u.getPunteggio(), u.getID()});
	}

	@Override
	public int deleteUtente(long id) {
		return jdbcTemplate.update("DELETE FROM utente WHERE ID=?", id);
	}

	@Override
	public int savePost(Post p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Post findPost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updatePost(Post p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletePost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int saveNotifica(Notifica p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Notifica findNotifica() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notifica> findAllNotifiche() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveBadge(Badge b) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Badge findBadge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveCommissione(Commissione c) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Commissione findCommissione() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Commissione> findAllCommissioni(Utente u) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
