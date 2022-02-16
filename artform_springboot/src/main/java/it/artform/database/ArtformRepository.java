package it.artform.database;

import java.util.Date;
import java.util.List;

import it.artform.pojos.*;

public interface ArtformRepository {

	//Utente
	Utente findUtente(String username);
	Utente findUtenteByEmail(String email); //serve per controllo email esistente, si potrebbe ottimizzare recuperando la sola colonna
	int saveUtente(Utente u);
	int updateUtente(Utente u);
	int deleteUtente(String username);
		
	//Post
	Post findPost(int id);
	List<Post> findPostsByUtente(String username);
	int savePost(Post p);
	int updatePost(Post p);
	int deletePost(int id);
	
	//Notifica
	Notifica findNotifica(String username, Date d);
	List<Notifica> findNotificheByUtente(String username);
	int saveNotifica(Notifica p);

	//Badge
	Badge findBadge(String nome);
	int saveBadge(Badge b); //forse non serve
	
	//Commissione
	Commissione findCommissione(int id);
	List<Commissione> findCommissioniByArtista(String username);
	List<Commissione> findCommissioniByCliente(String username);
	int saveCommissione(Commissione c);
	
	//Post Salvati
	List<Post> findUserSavedPosts(String username);
	int saveUserPost(String username, int id);
	int deletePostFromSaved(String username, int id);
	
	//Notifiche Utente
	List<String> findUserActiveNotifications(String username);
	int activateUserNotifications(String username1, String username2);
	int deactivateUserNotifications(String username1, String username2);
	
	//Badge Utente
	List<Badge> findUserBadges(String username);
	int userObtainsBadge(String username, String nome);

	//Topic Utente
	List<String> findUserSelectedTopics(String username);
	int userSelectsTopic(String username, String nome);
	int userDeselectsTopic(String username, String nome);
	
}
