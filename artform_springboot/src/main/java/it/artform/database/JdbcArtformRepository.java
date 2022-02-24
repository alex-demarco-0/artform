package it.artform.database;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.artform.pojos.*;

@Repository(value="MYSQL")
public class JdbcArtformRepository implements ArtformRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Utente findUtente(String username) {
		return jdbcTemplate.queryForObject("SELECT * from utente WHERE username=?", BeanPropertyRowMapper.newInstance(Utente.class), username);
	}

	@Override
	public Utente findUtenteByEmail(String email) {
		return jdbcTemplate.queryForObject("SELECT * from utente WHERE email=?", BeanPropertyRowMapper.newInstance(Utente.class), email);
	}

	@Override
	public int saveUtente(Utente u) {
		return jdbcTemplate.update("INSERT INTO utente (nome, cognome, username, email, numeroTelefono, password, bio, punteggio, immagineProfiloSrc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[] {u.getNome(), u.getCognome(), u.getUsername(), u.getEmail(), u.getNumeroTelefono(), u.getPassword(), u.getBio(), u.getPunteggio(), u.getImmagineProfiloSrc()});
	}

	@Override
	public int updateUtente(Utente u) {
		return jdbcTemplate.update("UPDATE utente SET nome=?, cognome=?, username=?, email=?, numeroTelefono=?, password=?, bio=?, punteggio=?, immagineProfiloSrc=? WHERE username=?",
				new Object[] {u.getNome(), u.getCognome(), u.getUsername(), u.getEmail(), u.getNumeroTelefono(), u.getPassword(), u.getBio(), u.getPunteggio(), u.getImmagineProfiloSrc(), u.getUsername()});
	}

	@Override
	public int deleteUtente(String username) {
		return jdbcTemplate.update("DELETE FROM utente WHERE username=?", username);
	}

	@Override
	public Post findPost(int id) {
		return jdbcTemplate.queryForObject("SELECT * from post WHERE Id=?", BeanPropertyRowMapper.newInstance(Post.class), id);
	}

	@Override
	public List<Post> findPostsByUtente(String username) {
		return jdbcTemplate.query("SELECT * from post WHERE utenteUsername=?", BeanPropertyRowMapper.newInstance(Post.class), username);
	}

	@Override
	public int savePost(Post p) {
		return jdbcTemplate.update("INSERT INTO `post` (`utenteUsername`, `titolo`, `topic`, `tags`, `dataPubblicazione`, `like`, `tipologia`, `contenutoSrc`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[] {p.getUtenteUsername(), p.getTitolo(), p.getTopic(), p.getTags(), p.getDataPubblicazione(), p.getLike(), p.getTipologia(), p.getContenutoSrc()});
	}

	@Override
	public int updatePost(Post p) {
		return jdbcTemplate.update("UPDATE `post` SET `titolo`=?, `topic`=?, `tags`=?, `like`=? WHERE `Id`=?",
				new Object[] {p.getTitolo(), p.getTopic(), p.getTags(), p.getLike(), p.getId()});
	}

	@Override
	public int deletePost(int id) {
		return jdbcTemplate.update("DELETE FROM post WHERE Id=?", id);
	}
	
	@Override
	public int deleteAllPostsByUtente(String username) {
		return jdbcTemplate.update("DELETE FROM post WHERE utenteUsername=?", username);
	}

	@Override
	public Notifica findNotifica(String username, Date data) {
		return jdbcTemplate.queryForObject("SELECT * from notifica WHERE utenteUsername=? AND data=?", BeanPropertyRowMapper.newInstance(Notifica.class), username, data);
	}

	@Override
	public List<Notifica> findNotificheByUtente(String username) {
		return jdbcTemplate.query("SELECT * from notifica WHERE utenteUsername=?", BeanPropertyRowMapper.newInstance(Notifica.class), username);
	}

	@Override
	public int saveNotifica(Notifica n) {
		return jdbcTemplate.update("INSERT INTO notifica (data, categoria, descrizione, collegamento, utenteUsername) VALUES (?, ?, ?, ?, ?)",
				new Object[] {n.getData(), n.getCategoria(),n.getDescrizione(), n.getCollegamento(), n.getUtenteUsername()});
	}

	@Override
	public Badge findBadge(String nome) {
		return jdbcTemplate.queryForObject("SELECT * from badge WHERE nome=?", BeanPropertyRowMapper.newInstance(Badge.class), nome);
	}
	
	@Override
	public List<Badge> findAllBadges() {
		return jdbcTemplate.query("SELECT * from badge", BeanPropertyRowMapper.newInstance(Badge.class));
	}

	@Override
	public int saveBadge(Badge b) {
		return jdbcTemplate.update("INSERT INTO badge (nome, descrizione, punteggio) VALUES (?, ?, ?)",
				new Object[] {b.getNome(), b.getDescrizione(), b.getPunteggio()});
	}
	
	@Override
	public List<Topic> findAllTopics() {
		return jdbcTemplate.query("SELECT nome from topic", BeanPropertyRowMapper.newInstance(Topic.class));
	}

	@Override
	public Commissione findCommissione(int id) {
		return jdbcTemplate.queryForObject("SELECT * from commissione WHERE Id=?", BeanPropertyRowMapper.newInstance(Commissione.class), id);
	}

	@Override
	public List<Commissione> findCommissioniByArtista(String username) {
		return jdbcTemplate.query("SELECT * from commissione WHERE artistaUsername=?", BeanPropertyRowMapper.newInstance(Commissione.class), username);
	}

	@Override
	public List<Commissione> findCommissioniByCliente(String username) {
		return jdbcTemplate.query("SELECT * from commissione WHERE clienteUsername=?", BeanPropertyRowMapper.newInstance(Commissione.class), username);
	}

	@Override
	public int saveCommissione(Commissione c) {
		return jdbcTemplate.update("INSERT INTO commissione (titolo, prezzo, data, artistaUsername, clienteUsername, indirizzoConto) VALUES (?, ?, ?, ?, ?, ?)",
				new Object[] {c.getTitolo(), c.getPrezzo(), c.getData(), c.getArtistaUsername(), c.getClienteUsername(), c.getIndirizzoConto()});
	}

	@Override
	public List<Post> findUserSavedPosts(String username) {
		return jdbcTemplate.query("SELECT p.* FROM post p INNER JOIN postSalvati ps ON p.Id = ps.postID WHERE ps.utenteUsername=?", BeanPropertyRowMapper.newInstance(Post.class), username);
	}

	@Override
	public int saveUserPost(String username, int id) {
		return jdbcTemplate.update("INSERT INTO postSalvati (utenteUsername, postID) VALUES (?, ?)",
				new Object[] {username, id});
	}

	@Override
	public int deletePostFromSaved(String username, int id) {
		return jdbcTemplate.update("DELETE FROM postSalvati WHERE utenteUsername=? AND postID=?", username, id);
	}
	
	@Override
	public int deleteAllPostsFromSaved(String username) {
		return jdbcTemplate.update("DELETE FROM postSalvati WHERE utenteUsername=?", username);
	}

	@Override
	public List<Utente> findUserActiveNotifications(String username) {
		return jdbcTemplate.query("SELECT ue.* FROM notificheUtente nu INNER JOIN utente ue ON nu.utenteExtUsername = ue.username WHERE nu.utenteUsername=?", BeanPropertyRowMapper.newInstance(Utente.class), username);
	}

	@Override
	public int activateUserNotifications(String username1, String username2) {
		return jdbcTemplate.update("INSERT INTO notificheUtente (utenteUsername, utenteExtUsername) VALUES (?, ?)",
				new Object[] {username1, username2});
	}

	@Override
	public int deactivateUserNotifications(String username1, String username2) {
		return jdbcTemplate.update("DELETE FROM notificheUtente WHERE utenteUsername=? AND utenteExtUsername=?", username1, username2);
	}
	
	@Override
	public int deactivateAllUserNotifications(String username) {
		return jdbcTemplate.update("DELETE FROM notificheUtente WHERE utenteUsername=?", username);
	}

	@Override
	public List<Badge> findUserBadges(String username) {
		return jdbcTemplate.query("SELECT b.* FROM badge b INNER JOIN badgeUtente bu ON b.nome = bu.badgeNome WHERE bu.utenteUsername=?", BeanPropertyRowMapper.newInstance(Badge.class), username);
	}

	@Override
	public int giveBadgeToUser(String username, String nome) {
		return jdbcTemplate.update("INSERT INTO badgeUtente (utenteUsername, badgeNome) VALUES (?, ?)",
				new Object[] {username, nome});
	}

	@Override
	public List<Topic> findUserSelectedTopics(String username) {
		return jdbcTemplate.query("SELECT t.* FROM topicUtente tu INNER JOIN topic t ON tu.topicNome = t.nome WHERE utenteUsername=?", BeanPropertyRowMapper.newInstance(Topic.class), username);
	}

	@Override
	public int addTopicToUserSelection(String username, String nome) {
		return jdbcTemplate.update("INSERT INTO topicUtente (utenteUsername, topicNome) VALUES (?, ?)",
				new Object[] {username, nome});
	}

	@Override
	public int removeTopicFromUserSelection(String username, String nome) {
		return jdbcTemplate.update("DELETE FROM topicUtente WHERE utenteUsername=? AND topicNome=?", username, nome);
	}
	
	@Override
	public int removeAllTopicsFromUserSelection(String username) {
		return jdbcTemplate.update("DELETE FROM topicUtente WHERE utenteUsername=?", username);
	}

}