package it.artform.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public int saveUser(Utente u) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Utente findUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateUser() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUser() {
		// TODO Auto-generated method stub
		return 0;
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
	public int updatePost() {
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
