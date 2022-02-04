package it.artform.database;

import java.util.List;

import it.artform.pojos.Badge;
import it.artform.pojos.Commissione;
import it.artform.pojos.Notifica;
import it.artform.pojos.Post;
import it.artform.pojos.Utente;

public interface ArtformRepository {
	
	int saveUser(Utente u);
	Utente findUser();
	int updateUser();
	int deleteUser();
	//int activateUserNotifications(Utente u1, Utente u2);
	
	int savePost(Post p);
	Post findPost();
	int updatePost();
	int deletePost();
	//int saveUserPost(Utente u, Post p);
	
	int saveNotifica(Notifica p);
	Notifica findNotifica();
	List<Notifica> findAllNotifiche();

	int saveBadge(Badge b);
	Badge findBadge();
	
	int saveCommissione(Commissione c);
	Commissione findCommissione();
	List<Commissione> findAllCommissioni(Utente u);
	
}
