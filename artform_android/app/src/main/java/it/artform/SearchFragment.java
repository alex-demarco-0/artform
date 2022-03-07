package it.artform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import it.artform.feed.UserGridAdapter;
import it.artform.pojos.Topic;
import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    /*SearchView contentSearchView = null;
    Button searchArtworksButton = null;
    Button searchVideosButton = null;
    Button searchArtistsButton = null;
    Spinner topicSpinner = null;
    GridView artistsGridView = null;
    TextView noResultTextView = null;

    ArtformApiEndpointInterface apiService = null;

    String selectedTopic = null;
    String[] searchedUsers = null;

    Activity act=null; */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container,false);
    }

    /*@Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        act=(Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //widget setup
        contentSearchView = getView().findViewById(R.id.contentSearchView); //search while typing
        contentSearchView.setQueryHint("Search…");
        searchArtworksButton = getView().findViewById(R.id.searchArtworksButton); //no listener
        searchVideosButton = getView().findViewById(R.id.searchVideosButton); //activity
        searchArtistsButton = getView().findViewById(R.id.searchArtistsButton); //activity
        topicSpinner = getView().findViewById(R.id.topicSpinner); //fetch topics
        artistsGridView = getView().findViewById(R.id.artistsGridView); //load posts
        noResultTextView = getView().findViewById(R.id.noResultTextView); //show only when no posts

        //web services setup
        AFGlobal app = (AFGlobal) act.getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        //GET dei Topic
        fetchTopics();

        //imposta SearchView da parametri
        Bundle searchParams = act.getIntent().getExtras();
        if(searchParams != null)
            contentSearchView.setQuery(searchParams.getCharSequence("keywords"), false);

        //listener barra di ricerca
        contentSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                fetchUsers((selectedTopic == null ? "" : selectedTopic), String.valueOf(contentSearchView.getQuery()));
                return true;
            }
        });

        //listener pulsante Opere
        searchArtworksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent artworkSearchIntent = new Intent(act, ImagePostSearchActivity.class);
                if(selectedTopic != null)
                    artworkSearchIntent.putExtra("topic", selectedTopic);
                artworkSearchIntent.putExtra("keywords", contentSearchView.getQuery());
                startActivity(artworkSearchIntent);
            }
        });

        //listener pulsante Video
        searchVideosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent videoSearchIntent = new Intent(act, VideoPostSearchActivity.class);
                if(selectedTopic != null)
                    videoSearchIntent.putExtra("topic", selectedTopic);
                videoSearchIntent.putExtra("keywords", contentSearchView.getQuery());
                startActivity(videoSearchIntent);
            }
        });
    }

    private void fetchTopics() {
        Call<List<Topic>> getTopicsCall = apiService.getAllTopics();
        getTopicsCall.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if(response.isSuccessful()) {
                    String[] topics = new String[response.body().size() + 1];
                    topics[0] = "Select topic:";
                    for(int i=1; i<topics.length; i++)
                        topics[i] = response.body().get(i-1).getName();
                    ArrayAdapter topicsAdapter = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_dropdown_item, topics);
                    topicSpinner.setAdapter(topicsAdapter);
                    //imposta Spinner da parametri
                    Bundle searchParams = act.getIntent().getExtras();
                    if(searchParams != null) {
                        String topicParam = searchParams.getString("topic");
                        if(topicParam != null) {
                            selectedTopic = topicParam;
                            topicSpinner.setSelection(topicsAdapter.getPosition(selectedTopic));
                        }
                    }
                    topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(adapterView.getItemAtPosition(i).equals("Select topic:"))
                                selectedTopic = null;
                            else
                                selectedTopic = String.valueOf(adapterView.getItemAtPosition(i));
                            if(!String.valueOf(contentSearchView.getQuery()).equals(""))
                                fetchUsers((selectedTopic == null ? "" : selectedTopic), String.valueOf(contentSearchView.getQuery()));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else
                    Toast.makeText(act, "Error while fetching topics", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Toast.makeText(act, "Error while fetching topics: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchUsers(String topic, String keywords) {
        Call<List<User>> getUsersByFiltersCall = apiService.getUsersByFilters(topic, keywords);
        getUsersByFiltersCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                    searchedUsers = new String[response.body().size()];
                    for(int i=0; i<searchedUsers.length; i++)
                        searchedUsers[i] = response.body().get(i).getUsername();
                    //Caricamento utenti nella GridView
                    if(searchedUsers.length > 0) {
                        noResultTextView.setVisibility(View.INVISIBLE);
                        artistsGridView.setAdapter(new UserGridAdapter(act, R.layout.row_user_grid, searchedUsers));
                        //dettagli profilo utente
                        artistsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                //se l'utente selezionato corrisponde al proprio profilo
                                if(searchedUsers[position].equals(AFGlobal.getLoggedUser())) {
                                    Intent userProfileIntent = new Intent(act, UserProfileActivity.class);
                                    startActivity(userProfileIntent);
                                    return;
                                }
                                Intent externalProfileIntent = new Intent(act, ExternalProfileActivity.class);
                                externalProfileIntent.putExtra("username", searchedUsers[position]);
                                startActivity(externalProfileIntent);
                            }
                        });
                    }
                    else
                        noResultTextView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                noResultTextView.setVisibility(View.VISIBLE);
                Toast.makeText(act, "Error while searching users: " + t.toString(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }*/

}
