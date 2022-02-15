package it.artform.database;

import java.util.Date;
import java.util.List;

import it.artform.pojos.*;

public interface ArtformRepository {

	//Utente
	Utente findUtenteByUsername(String username);
	Utente findUtenteByEmail(String email);
	int saveUtente(Utente u);
	int updateUtente(Utente u);
	int deleteUtente(String username);
	int activateUserNotifications(String username1, String username2);
	
	//Post
	Post findPost(long id);
	List<Post> findAllPostsByUtente(String username);
	int savePost(Post p);
	int updatePost(Post p);
	int deletePost(long id);
	//int saveUserPost(String username, long id);

	//Notifica
	Notifica findNotifica(Date d);
	int saveNotifica(Notifica p);
	List<Notifica> findAllNotifiche();

	//Badge
	Badge findBadge(String nome);
	int saveBadge(Badge b);
	
	//Commissione
	Commissione findCommissione(long id);
	int saveCommissione(Commissione c);
	List<Commissione> findCommissioniByArtista(String username);
	
}
