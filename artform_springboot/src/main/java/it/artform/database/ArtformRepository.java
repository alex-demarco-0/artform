package it.artform.database;

import java.util.List;

import it.artform.pojos.*;

public interface ArtformRepository {
	// recupera da DB in base a ID
	Utente findUtenteById(long id);
	
	// recupera da DB in base a Username
	Utente findUtenteByUsername(String username);
	int saveUtente(Utente u);
	int updateUtente(Utente u);
	int deleteUtente(long id);
	//int activateUserNotifications(Utente u1, Utente u2);
	
	Post findPost();
	int savePost(Post p);
	int updatePost(Post p);
	int deletePost();
	//int saveUserPost(Utente u, Post p);

	Notifica findNotifica();
	int saveNotifica(Notifica p);
	List<Notifica> findAllNotifiche();

	Badge findBadge();
	int saveBadge(Badge b);
	
	Commissione findCommissione();
	int saveCommissione(Commissione c);
	List<Commissione> findAllCommissioni(long utenteId);
	
}
