package it.artform.web;

import java.util.Date;
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
			return new ResponseEntity<String>("Utente username=" + username + " DELETED", HttpStatus.OK);
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
	
	@RequestMapping(value="/artform/utente/{username}/posts", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> getUtentePosts(@PathVariable String username) {
		List<Post> posts = this.artformRepository.findPostsByUtente(username);
		if(posts != null)
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/post", method=RequestMethod.POST)
	public ResponseEntity<Post> addPost(@RequestBody Post newPost) {
		if(this.artformRepository.savePost(newPost) == 1)
			return new ResponseEntity<Post>(newPost, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/artform/post/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Post> updatePost(@PathVariable int id, @RequestBody Post modPost) {
		Post p = this.artformRepository.findPost(id);
		if(p != null) {
			if(modPost.getTitolo() != null && !modPost.getTitolo().isBlank())
				p.setTitolo(modPost.getTitolo());
			if(modPost.getTopic() != null && !modPost.getTopic().isBlank())
				p.setTopic(modPost.getTitolo());
			if(modPost.getTags() != null && modPost.getTags().length > 0)
				p.setTags(modPost.getTags());
			if(modPost.getLike() > p.getLike())
				p.addLike();
			if(this.artformRepository.updatePost(p) == 1)
				return new ResponseEntity<Post>(p, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/post/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deletePost(@PathVariable int id) {
		if(this.artformRepository.deletePost(id) == 1)
			return new ResponseEntity<String>("Post Id=" + id + " DELETED", HttpStatus.OK);
		return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/posts", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteAllUtentePosts(@PathVariable String username) {
		if(this.artformRepository.deleteAllPostsByUtente(username) > 0)
			return new ResponseEntity<String>("All User username=" + username + "'s Posts DELETED", HttpStatus.OK);
		return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
	}
	
	/* 
	 * Notifica
	 */
	
	@RequestMapping(value="/artform/utente/{username}/notifiche/{data}", method=RequestMethod.GET)
	public ResponseEntity<Notifica> getNotifica(@PathVariable String username, Date data) {
		Notifica n = this.artformRepository.findNotifica(username, data);
		if(n != null)
			return new ResponseEntity<Notifica>(n, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/notifiche", method=RequestMethod.GET)
	public ResponseEntity<List<Notifica>> getNotificheUtente(@PathVariable String username) {
		List<Notifica> notifications = this.artformRepository.findNotificheByUtente(username);
		if(notifications != null)
			return new ResponseEntity<List<Notifica>>(notifications, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/notifiche", method=RequestMethod.POST)
	public ResponseEntity<Notifica> addNotifica(@RequestBody Notifica newNotifica) {
		if(this.artformRepository.saveNotifica(newNotifica) == 1)
			return new ResponseEntity<Notifica>(newNotifica, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/* 
	 * Badge
	 */
	
	@RequestMapping(value="/artform/badge/{nome}", method=RequestMethod.GET)
	public ResponseEntity<Badge> getBadge(@PathVariable String nome) {
		Badge b = this.artformRepository.findBadge(nome);
		if(b != null)
			return new ResponseEntity<Badge>(b, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/badges", method=RequestMethod.GET)
	public ResponseEntity<List<Badge>> getAllBadges() {
		List<Badge> badges = this.artformRepository.findAllBadges();
		if(badges != null)
			return new ResponseEntity<List<Badge>>(badges, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/badges", method=RequestMethod.POST)
	public ResponseEntity<Badge> addBadge(@RequestBody Badge newBadge) {
		if(this.artformRepository.saveBadge(newBadge) == 1)
			return new ResponseEntity<Badge>(newBadge, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/* 
	 * Topic
	 */
	
	@RequestMapping(value="/artform/topics", method=RequestMethod.GET)
	public ResponseEntity<List<String>> getAllTopics() {
		List<String> topics = this.artformRepository.findAllTopics();
		if(topics != null)
			return new ResponseEntity<List<String>>(topics, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/* 
	 * Commissione
	 */
	
	@RequestMapping(value="/artform/commissione/{id}", method=RequestMethod.GET)
	public ResponseEntity<Commissione> getCommissione(@PathVariable int id) {
		Commissione c = this.artformRepository.findCommissione(id);
		if(c != null)
			return new ResponseEntity<Commissione>(c, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/commissioni_da_artista", method=RequestMethod.GET)
	public ResponseEntity<List<Commissione>> getCommissioniAsArtista(@PathVariable String username) {
		List<Commissione> commissioni = this.artformRepository.findCommissioniByArtista(username);
		if(commissioni != null)
			return new ResponseEntity<List<Commissione>>(commissioni, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/commissioni_da_cliente", method=RequestMethod.GET)
	public ResponseEntity<List<Commissione>> getCommissioniAsCliente(@PathVariable String username) {
		List<Commissione> commissioni = this.artformRepository.findCommissioniByCliente(username);
		if(commissioni != null)
			return new ResponseEntity<List<Commissione>>(commissioni, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/commissione", method=RequestMethod.POST)
	public ResponseEntity<Commissione> addCommissione(@RequestBody Commissione newCommissione) {
		if(this.artformRepository.saveCommissione(newCommissione) == 1)
			return new ResponseEntity<Commissione>(newCommissione, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/* 
	 * Post Salvati
	 */
	
	@RequestMapping(value="/artform/utente/{username}/post_salvati", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> getUserSavedPosts(@PathVariable String username) {
		List<Post> savedPosts = this.artformRepository.findUserSavedPosts(username);
		if(savedPosts != null)
			return new ResponseEntity<List<Post>>(savedPosts, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/post_salvati", method=RequestMethod.POST)
	public ResponseEntity<Post> addPostToSaved(@PathVariable String username, @RequestBody int id) {
		if(this.artformRepository.saveUserPost(username, id) == 1)
			return new ResponseEntity<Post>(this.artformRepository.findPost(id), HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/artform/utente/{username}/post_salvati/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<String> removePostFromSaved(@PathVariable String username, int id) {
		if(this.artformRepository.deletePostFromSaved(username, id) == 1)
			return new ResponseEntity<String>("Post Id=" + id + " REMOVED from User username=" + username + "'s saved", HttpStatus.OK);
		return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/post_salvati", method=RequestMethod.DELETE)
	public ResponseEntity<String> removeAllPostsFromSaved(@PathVariable String username) {
		if(this.artformRepository.deleteAllPostsFromSaved(username) > 0)
			return new ResponseEntity<String>("REMOVED all User username=" + username + "'s saved Posts", HttpStatus.OK);
		return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
	}
	
	/* 
	 * Notifiche Utente
	 */
	
	@RequestMapping(value="/artform/utente/{username}/notifiche_attive", method=RequestMethod.GET)
	public ResponseEntity<List<String>> getUserActiveNotifications(@PathVariable String username) {
		List<String> activeNotifications = this.artformRepository.findUserActiveNotifications(username);
		if(activeNotifications != null)
			return new ResponseEntity<List<String>>(activeNotifications, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/notifiche_attive", method=RequestMethod.POST)
	public ResponseEntity<Utente> activateUserNotifications(@PathVariable String username1, @RequestBody String username2) {
		if(this.artformRepository.activateUserNotifications(username1, username2) == 1)
			return new ResponseEntity<Utente>(this.artformRepository.findUtente(username2), HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/artform/utente/{username}/notifiche_attive/{username2}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deactivateUserNotifications(@PathVariable String username1, String username2) {
		if(this.artformRepository.deactivateUserNotifications(username1, username2) == 1)
			return new ResponseEntity<String>("User username=" + username1 + "'s Notifications for User username=" + username2 + " DEACTIVATED", HttpStatus.OK);
		return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/notifiche_attive", method=RequestMethod.DELETE)
	public ResponseEntity<String> deactivateAllUserNotifications(@PathVariable String username) {
		if(this.artformRepository.deactivateAllUserNotifications(username) > 0)
			return new ResponseEntity<String>("DEACTIVATED all User username=" + username + "'s active Notifications", HttpStatus.OK);
		return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
	}
	
	/* 
	 * Badge Utente
	 */
	
	@RequestMapping(value="/artform/utente/{username}/badges", method=RequestMethod.GET)
	public ResponseEntity<List<Badge>> getUserBadges(@PathVariable String username) {
		List<Badge> userBadges = this.artformRepository.findUserBadges(username);
		if(userBadges != null)
			return new ResponseEntity<List<Badge>>(userBadges, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/badges", method=RequestMethod.POST)
	public ResponseEntity<Badge> userObtainsBadge(@PathVariable String username, @RequestBody String nome) {
		if(this.artformRepository.giveBadgeToUser(username, nome) == 1)
			return new ResponseEntity<Badge>(this.artformRepository.findBadge(nome), HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/* 
	 * Topic Utente
	 */
	
	@RequestMapping(value="/artform/utente/{username}/topics", method=RequestMethod.GET)
	public ResponseEntity<List<String>> getUserSelectedTopics(@PathVariable String username) {
		List<String> userTopics = this.artformRepository.findUserSelectedTopics(username);
		if(userTopics != null)
			return new ResponseEntity<List<String>>(userTopics, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/topics", method=RequestMethod.POST)
	public ResponseEntity<String> userSelectsTopic(@PathVariable String username, @RequestBody String nome) {
		if(this.artformRepository.addTopicToUserSelection(username, nome) == 1)
			return new ResponseEntity<String>(nome, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/artform/utente/{username}/topics/{nome}", method=RequestMethod.DELETE)
	public ResponseEntity<String> userDeselectsTopic(@PathVariable String username, String nome) {
		if(this.artformRepository.removeTopicFromUserSelection(username, nome) == 1)
			return new ResponseEntity<String>("User username=" + username + "'s Topic nome=" + nome + " DESELECTED", HttpStatus.OK);
		return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/topics", method=RequestMethod.DELETE)
	public ResponseEntity<String> userDeselectsAllTopics(@PathVariable String username) {
		if(this.artformRepository.removeAllTopicsFromUserSelection(username) > 0)
			return new ResponseEntity<String>("All User username=" + username + "'s Topics DESELECTED", HttpStatus.OK);
		return new ResponseEntity<String>("NOT_FOUND", HttpStatus.NOT_FOUND);
	}

}
