package it.artform.web;

import java.util.Date;
import java.util.List;

import it.artform.pojos.Badge;
import it.artform.pojos.Commission;
import it.artform.pojos.Notification;
import it.artform.pojos.Post;
import it.artform.pojos.Topic;
import it.artform.pojos.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ArtformApiEndpointInterface {

    // User
    @GET("/artform/utente/{username}")
    Call<User> getUserByUsername(@Path("username") String username);

    @GET("/artform/utente_email/{email}")
    Call<User> checkEmailExists(@Path("email") String email);

    @POST("/artform/utente")
    Call<User> addUser(@Body User user);

    @PUT("/artform/utente/{username}")
    Call<User> updateUser(@Path("username") String username, @Body User user);

    @DELETE("/artform/utente/{username}")
    String deleteUser(@Path("username") String username);

    // Post
    @GET("/artform/post/{id}")
    Call<Post> getPost(@Path("id") int id);

    @GET("/artform/utente/{username}/posts")
    Call<List<Post>> getUserPosts(@Path("username") String username);

    @POST("/artform/post")
    Call<Post> addPost(@Body Post post);

    @PUT("/artform/post/{id}")
    Call<Post> updatePost(@Path("id") int id, @Body Post post);

    @DELETE("/artform/post/{id}")
    String deletePost(@Path("id") int id);

    @DELETE("/artform/utente/{username}/posts")
    String deleteAllUserPosts(@Path("username") String username);

    // Notification
    @GET("/artform/utente/{username}/notifiche/{date}")
    Call<Notification> getNotification(@Path("username") String username, @Path("date") Date date);

    @GET("/artform/utente/{username}/notifiche")
    Call<List<Notification>> getAllUserNotifications(@Path("username") String username);

    @POST("/artform/utente/{username}/notifiche")
    Call<Notification> addNotification(@Body Notification notification);

    // Badge
    @GET("/artform/badge/{name}")
    Call<Badge> getBadge(@Path("name") String name);

    @GET("/artform/badges")
    Call<List<Badge>> getAllBadges();

    @POST("/artform/badges")
    Call<Badge> addBadge(@Body Badge badge);

    // Topic
    @GET("/artform/topic")
    Call<List<Topic>> getAllTopics();

    // Commission
    @GET("/artform/commissione/{id}")
    Call<Commission> getCommission(@Path("id") int id);

    @GET("/artform/utente/{username}/commissioni_da_artista")
    Call<List<Commission>> getUserCommissionsAsArtist(@Path("username") String username);

    @GET("/artform/utente/{username}/commissioni_da_cliente")
    Call<List<Commission>> getUserCommissionsAsCustomer(@Path("username") String username);

    @POST("/artform/commissione")
    Call<Commission> addCommission(@Body Commission commission);

    // Saved Posts
    @GET("/artform/utente/{username}/post_salvati")
    Call<List<Post>> getUserSavedPosts(@Path("username") String username);

    @POST("/artform/utente/{username}/post_salvati")
    Call<Post> addPostToSaved(@Path("username") String username, @Body int postId);

    @DELETE("/artform/utente/{username}/post_salvati/{postId}")
    String removePostFromSaved(@Path("username") String username, @Path("postId") int postId);

    @DELETE("/artform/utente/{username}/post_salvati")
    String removeAllPostsFromSaved(@Path("username") String username);

    // User Notifications
    @GET("/artform/utente/{username}/notifiche_attive")
    Call<List<User>> getUserActiveNotifications(@Path("username") String username);

    @POST("/artform/utente/{username1}/notifiche_attive")
    Call<User> activateUserNotifications(@Path("username1") String username1, @Body String username2);

    @DELETE("/artform/utente/{username1}/notifiche_attive/{username2}")
    String deactivateUserNotifications(@Path("username1") String username1, @Path("username2") String username2);

    @DELETE("/artform/utente/{username}/notifiche_attive")
    String deactivateAllUserNotifications(@Path("username") String username);

    // User Badges
    @GET("/artform/utente/{username}/badges")
    Call<List<Badge>> getUserBadges(@Path("username") String username);

    @POST("/artform/utente/{username}/badges")
    Call<Badge> userObtainsBadge(@Path("username") String username, @Body String badgeName);

    // User Topics
    @GET("/artform/utente/{username}/topics")
    Call<List<Topic>> getUserSelectedTopics(@Path("username") String username);

    @POST("/artform/utente/{username}/topics")
    Call<String> userSelectsTopic(@Path("username") String username, @Body String topicName);

    @DELETE("/artform/utente/{username}/topics/{topicName}")
    String userDeselectsTopic(@Path("username") String username, @Path("topicName") String topicName);

    @DELETE("/artform/utente/{username}/topics")
    String userDeselectsAllTopics(@Path("username") String username);

}