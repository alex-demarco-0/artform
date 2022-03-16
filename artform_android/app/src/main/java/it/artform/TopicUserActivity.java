package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.artform.databases.UserDBAdapter;
import it.artform.feed.TopicGridAdapter;
import it.artform.pojos.Topic;
import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicUserActivity extends Activity { //magari rinominare in TopicSelectionActivity (layout: activity_topic_selection.xml
    // web services declaration
    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;

    // widgets declaration
    GridView topicsGridView = null;
    Button registerButton = null;

    // User pending registration
    User newUser = null;
    String[] topics = null;
    static List<String> topicSelection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_user);

        // widget setup
        topicsGridView = findViewById(R.id.topicsGridView);
        registerButton = findViewById(R.id.registerButton);

        //ricevere parametro User da RegisterActivity
        Bundle newUserParam = getIntent().getExtras();
        if (newUserParam != null)
            newUser = (User) newUserParam.get("newUser");
        topicSelection = new ArrayList<>();

        // web services setup
        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        // GET Topics
        fetchTopics();

        //pulsante effettua registrazione
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
                //e commit dei topic selezionati
                sendTopicSelection();
            }
        });
    }
/*
    private class TopicClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            //if not selected
            String topic = topics[pos];
            if(!topicSelection.contains(topic))
                topicSelection.add(topic); //inserendo in una lista la coda di Topic da aggiungere
            else
                topicSelection.remove(topic);
            //TEST
            Toast.makeText(TopicUserActivity.this, topicSelection.toString(), Toast.LENGTH_SHORT).show();
            //
            //evidenziare
            //if already selected
            //deselectTopic() //togliendo dalla lista
            //togliere evidenziazione
        }
    }
*/
    public static List<String> getTopicSelection() {
        return topicSelection;
    }

    public static void setTopicSelection(List<String> topicSelectionUpd) {
        topicSelection = topicSelectionUpd;
    }

    private void fetchTopics() {
        Call<List<Topic>> getTopicCall = apiService.getAllTopics();
        getTopicCall.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.isSuccessful()){
                    topics = new String[response.body().size()];
                    for (int i = 0; i < topics.length; i++)
                        topics[i] = response.body().get(i).getName();
                    TopicGridAdapter topicsAdapter = new TopicGridAdapter(TopicUserActivity.this, R.layout.item_topic_grid, topics);
                    topicsGridView.setAdapter(topicsAdapter);
                    topicsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String topic = topics[i];
                            if(!topicSelection.contains(topic))
                                topicSelection.add(topic); //inserendo in una lista la coda di Topic da aggiungere
                            else
                                topicSelection.remove(topic);
                            //TEST
                            Toast.makeText(TopicUserActivity.this, topicSelection.toString(), Toast.LENGTH_SHORT).show();
                            //
                        }
                    });
                } else
                    Toast.makeText(TopicUserActivity.this, "Error while fetching topics: ERROR" + response.code(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Toast.makeText(TopicUserActivity.this, "Error while fetching topics: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
/*
    private void selectTopic(int position) {
        // TEST
        Toast.makeText(this, "Hai Premuto " + position, Toast.LENGTH_SHORT).show();
        //
        // Call<String> userSelectsTopic
        //newUser.getUsername()
    }

    private void deselectTopic(int position) {
        // contrario di selectTopic()
    }
*/
    private void registerUser() {
        // richiesta POST sul Database server
        Call<User> addUserCall = apiService.addUser(newUser);
        addUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    //insert sul db locale
                    UserDBAdapter udba = new UserDBAdapter(TopicUserActivity.this);
                    try {
                        udba.open();
                    } catch (SQLException throwables) {
                        Toast.makeText(TopicUserActivity.this, "Si è verificato un problema durante la registrazione (errore SQLite)", Toast.LENGTH_LONG).show();
                        throwables.printStackTrace();
                    }
                    udba.createUser(newUser);
                    udba.close();

                    Toast.makeText(TopicUserActivity.this, "Registrazione effettuata con successo!", Toast.LENGTH_LONG).show();

                    Intent mainIntent = new Intent(TopicUserActivity.this, MainActivity.class);
                    // TEST - passaggio parametri alla MainActivity (una volta superato il controllo dei campi)
                    mainIntent.putExtra("nome", "nome: " + newUser.getName());
                    mainIntent.putExtra("cognome", "cognome: " + newUser.getSurname());
                    mainIntent.putExtra("email", "email: " + newUser.getEmail());
                    mainIntent.putExtra("username", "username: " + newUser.getUsername());
                    if (!newUser.getPhone().equals(""))
                        mainIntent.putExtra("telefono", "telefono: " + newUser.getPhone());
                    mainIntent.putExtra("password", "password: " + newUser.getPassword());
                    //
                    AFGlobal.setLoggedUser(newUser.getUsername());
                    startActivity(mainIntent);
                    //finish();
                } else
                    Toast.makeText(TopicUserActivity.this, "Si è verificato un problema durante la registrazione: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(TopicUserActivity.this, "Si è verificato un problema durante la registrazione: " + t.toString(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void sendTopicSelection() {
        for(String topic: topicSelection) {
            Call<String> userSelectsTopicCall = apiService.userSelectsTopic(newUser.getUsername(), topic);
            userSelectsTopicCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(!response.isSuccessful())
                        Toast.makeText(TopicUserActivity.this, "Error while selecting Topic " + topic + " for User " + newUser.getUsername() + ": ERROR " + response.code(), Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(TopicUserActivity.this, "Error while selecting Topic " + topic + " for User " + newUser.getUsername() + ": " + t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
