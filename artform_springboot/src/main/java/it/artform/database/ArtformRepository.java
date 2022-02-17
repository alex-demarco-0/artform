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
	int deleteAllPostsByUtente(String username);
	
	//Notifica
	Notifica findNotifica(String username, Date data);
	List<Notifica> findNotificheByUtente(String username);
	int saveNotifica(Notifica n);

	//Badge
	Badge findBadge(String nome);
	List<Badge> findAllBadges();
	int saveBadge(Badge b); //forse non serve
	
	//Topic
	List<String> findAllTopics();
	
	//Commissione
	Commissione findCommissione(int id);
	List<Commissione> findCommissioniByArtista(String username);
	List<Commissione> findCommissioniByCliente(String username);
	int saveCommissione(Commissione c);
	
	//Post Salvati
	List<Post> findUserSavedPosts(String username);
	int saveUserPost(String username, int id);
	int deletePostFromSaved(String username, int id);
	int deleteAllPostsFromSaved(String username);
	
	//Notifiche Utente
	List<String> findUserActiveNotifications(String username);
	int activateUserNotifications(String username1, String username2);
	int deactivateUserNotifications(String username1, String username2);
	int deactivateAllUserNotifications(String username);
	
	//Badge Utente
	List<Badge> findUserBadges(String username);
	int userObtainsBadge(String username, String nome);

	//Topic Utente
	List<String> findUserSelectedTopics(String username);
	int userSelectsTopic(String username, String nome);
	int userDeselectsTopic(String username, String nome);
	
}
