package it.artform.database;

import java.util.Date;
import java.util.List;

import it.artform.pojos.*;

public interface ArtformRepository {

	//Utente
	Utente findUtente(String username);
	Utente findUtenteByEmail(String email);
	int saveUtente(Utente u);
	int updateUtente(Utente u);
	int deleteUtente(String username);
	int activateUserNotifications(String username1, String username2);
	
	//Post
	Post findPost(int id);
	List<Post> findPostsByUtente(String username);
	int savePost(Post p);
	int updatePost(Post p);
	int deletePost(int id);
	//int saveUserPost(String username, long id);

	//Notifica
	Notifica findNotifica(String username, Date d);
	int saveNotifica(Notifica p);
	List<Notifica> findNotificheByUtente(String username);

	//Badge
	Badge findBadge(String nome);
	int saveBadge(Badge b);
	List<Badge> findBadgesByUtente(String username);
	
	//Commissione
	Commissione findCommissione(int id);
	int saveCommissione(Commissione c);
	List<Commissione> findCommissioniByArtista(String username);
	
}
