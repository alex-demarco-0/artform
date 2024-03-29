package it.artform.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.artform.ResourcesStorageService;
import it.artform.database.ArtformRepository;
import it.artform.pojos.*;

@RestController
public class ArtformRESTController {
	
	@Autowired
	ArtformRepository artformRepository;
	
	@Autowired
	ResourcesStorageService resourcesStorageService;
	
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
	
	@RequestMapping(value="/artform/utente_email/{email}", method=RequestMethod.GET)
	public ResponseEntity<Utente> getUtenteByEmail(@PathVariable String email) {
		Utente u = this.artformRepository.findUtenteByEmail(email);
		if(u != null)
			return new ResponseEntity<Utente>(u, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/topic={topic}/keywords={keywords}", method=RequestMethod.GET)
	public ResponseEntity<List<Utente>> getUtentiByFilters(@PathVariable String topic, @PathVariable String keywords) {
		List<Utente> utenti = this.artformRepository.findUtentiByFilters(topic, keywords);
		if(utenti != null)
			return new ResponseEntity<List<Utente>>(utenti, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente", method=RequestMethod.POST)
	public ResponseEntity<Utente> addUtente(@RequestBody Utente newUtente) {
		if(this.artformRepository.saveUtente(newUtente) == 1) {
			this.userObtainsBadge(newUtente.getUsername(), "Welcome on ArtForm!");
			return new ResponseEntity<Utente>(newUtente, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value="/artform/utente/{username}", method=RequestMethod.PUT)
	public ResponseEntity<Utente> updateUtente(@PathVariable String username, @RequestParam("userObj") Utente modUtente, @RequestParam("resource") MultipartFile profilePicResource) {
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
			if(this.artformRepository.updateUtente(u) == 1) {
				resourcesStorageService.storeProfilePic(profilePicResource, modUtente);
				return new ResponseEntity<Utente>(u, HttpStatus.OK);
			}
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

	@RequestMapping(value="/artform/post/topic={topic}/keywords={keywords}/type={type}", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> getPostsByFilters(@PathVariable String topic, @PathVariable String keywords, @PathVariable String type) {
		List<Post> posts = this.artformRepository.findPostsByFilters(topic, keywords, type);
		if(posts != null)
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/post/{username}/{topic}", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> getPostsbyTopic(@PathVariable String username, @PathVariable String topic){
		List<Post> posts = this.artformRepository.findPostsByTopic(topic, username);
		if (posts != null) 
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/posts", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> getUtentePosts(@PathVariable String username) {
		List<Post> posts = this.artformRepository.findPostsByUtente(username);
		if(posts != null)
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
/*	
	@RequestMapping(value="/artform/utente/{username}/posts/n", method=RequestMethod.POST)
	public int getUserPostAmount(@PathVariable String username) {
		return this.artformRepository.findUserPostAmount(username);
	}
*/	
	@RequestMapping(value="/artform/post", method=RequestMethod.POST)
	public ResponseEntity<Post> addPost(@RequestParam("resource") MultipartFile postResource, @RequestParam("postObj") Post newPost) {
		if(this.artformRepository.savePost(newPost) == 1) {
			Post createdPost = this.artformRepository.findPostByParams(newPost.getUtenteUsername(), newPost.getDataPubblicazione());
			if(newPost.getTipologia().equals("img"))
				resourcesStorageService.storeImagePost(postResource, createdPost);
			else
				resourcesStorageService.storeVideoPost(postResource, createdPost);
			//se è il primo post dell'utente dai badge, notifica e punteggio maggiorato
			Utente u = this.artformRepository.findUtente(newPost.getUtenteUsername());
			if(this.artformRepository.findUserPostAmount(newPost.getUtenteUsername()) == 1) {
				this.userObtainsBadge(newPost.getUtenteUsername(), "First content published");
				u.incrementaPunteggio(100);
			}
			u.incrementaPunteggio(40);
			this.artformRepository.updateUtente(u);
			List<String> usersToNotify = this.artformRepository.findAllUsersWhoActivatedNotificationsOnUser(newPost.getUtenteUsername());
			int i = 1;
			for(String user: usersToNotify) {
				Notifica n = new Notifica();
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.SECOND, i++); //necessario per evitare conflitto di chiave primaria
				n.setData(calendar.getTime());
				n.setCategoria(1);
				String description = newPost.getUtenteUsername() + " published a new " +
									(newPost.getTipologia().equals("img") ? "artwork" : "video");
				n.setDescrizione(description);
				n.setCollegamento(String.valueOf(createdPost.getId()));
				n.setUtenteUsername(user);
				this.artformRepository.saveNotifica(n);
			}
			return new ResponseEntity<Post>(createdPost, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/artform/post/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Post> updatePost(@PathVariable int id, @RequestBody Post modPost) {
		Post p = this.artformRepository.findPost(id);
		boolean liked = false;
		if(p != null) {
			if(modPost.getTitolo() != null && !modPost.getTitolo().isBlank())
				p.setTitolo(modPost.getTitolo());
			if(modPost.getTopic() != null && !modPost.getTopic().isBlank())
				p.setTopic(modPost.getTopic());
			if(modPost.getTags() != null && !modPost.getTags().isBlank())
				p.setTags(modPost.getTags());
			if(modPost.getLike() > p.getLike()) {
				p.addLike();
				liked = true;
			}
			if(this.artformRepository.updatePost(p) == 1) {
				if(liked) {
					this.artformRepository.updatePunteggioUtente(p.getUtenteUsername(), 1);
					Notifica n = new Notifica();
					n.setData(new Date());
					n.setCategoria(2);
					String descrizione = "Someone liked your post!";
					n.setDescrizione(descrizione);
					String collegamento = String.valueOf(id);
					n.setCollegamento(collegamento);
					n.setUtenteUsername(p.getUtenteUsername());
					this.artformRepository.saveNotifica(n);
				}
				return new ResponseEntity<Post>(p, HttpStatus.OK);
			}
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
	
	@RequestMapping(value="/artform/utente/{username}/nuove_notifiche/", method=RequestMethod.POST)
	public int getUnreadNotificheNumber(@PathVariable String username, @RequestBody Date startDate) {
		return this.artformRepository.findNotificationAmountAfterDate(username, startDate);
	}
	
	@RequestMapping(value="/artform/utente/{username}/notifiche", method=RequestMethod.GET)
	public ResponseEntity<List<Notifica>> getNotificheUtente(@PathVariable String username) {
		List<Notifica> notifications = this.artformRepository.findNotificheByUtente(username);
		if(notifications != null)
			return new ResponseEntity<List<Notifica>>(notifications, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/notifiche", method=RequestMethod.POST)
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
	public ResponseEntity<List<Topic>> getAllTopics() {
		List<Topic> topics = this.artformRepository.findAllTopics();
		if(topics != null)
			return new ResponseEntity<List<Topic>>(topics, HttpStatus.OK);
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
		if(this.artformRepository.saveCommissione(newCommissione) == 1) {
			Notifica n = new Notifica();
			n.setData(new Date());
			n.setCategoria(3);
			String descrizione = "User " + newCommissione.getClienteUsername() + " sent you a commission request";
			n.setDescrizione(descrizione);
			String collegamento = String.valueOf(newCommissione.getId());
			n.setCollegamento(collegamento);
			n.setUtenteUsername(newCommissione.getArtistaUsername());
			this.artformRepository.saveNotifica(n);
			return new ResponseEntity<Commissione>(newCommissione, HttpStatus.CREATED);
		}
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
	
	@RequestMapping(value="/artform/utente/{username1}/verifica_notifiche_attive/{username2}", method=RequestMethod.GET)
	public ResponseEntity<Utente> checkUserNotifications(@PathVariable String username1, @PathVariable String username2) {
		Utente check = this.artformRepository.checkUserNotifications(username1, username2);
		if(check != null)
			return new ResponseEntity<Utente>(check, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/artform/utente/{username}/notifiche_attive", method=RequestMethod.GET)
	public ResponseEntity<List<Utente>> getUserActiveNotifications(@PathVariable String username) {
		List<Utente> activeNotifications = this.artformRepository.findUserActiveNotifications(username);
		if(activeNotifications != null)
			return new ResponseEntity<List<Utente>>(activeNotifications, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value="/artform/utente/{username1}/notifiche_attive", method=RequestMethod.POST)
	public ResponseEntity<Utente> activateUserNotifications(@PathVariable String username1, @RequestBody String username2) {
		if(this.artformRepository.activateUserNotifications(username1, username2) == 1)
			return new ResponseEntity<Utente>(this.artformRepository.findUtente(username2), HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/artform/utente/{username1}/notifiche_attive/{username2}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deactivateUserNotifications(@PathVariable String username1, @PathVariable String username2) {
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
		if(this.artformRepository.giveBadgeToUser(username, nome) == 1) {
			Notifica n = new Notifica();
			n.setData(new Date());
			n.setCategoria(5);
			n.setDescrizione("Congrats! You obtained a new Badge: " + nome);
			n.setCollegamento("badge");
			n.setUtenteUsername(username);
			this.artformRepository.saveNotifica(n);
			return new ResponseEntity<Badge>(this.artformRepository.findBadge(nome), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/* 
	 * Topic Utente
	 */
	
	@RequestMapping(value="/artform/utente/{username}/topics", method=RequestMethod.GET)
	public ResponseEntity<List<Topic>> getUserSelectedTopics(@PathVariable String username) {
		List<Topic> userTopics = this.artformRepository.findUserSelectedTopics(username);
		if(userTopics != null)
			return new ResponseEntity<List<Topic>>(userTopics, HttpStatus.OK);
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
