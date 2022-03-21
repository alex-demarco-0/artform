package it.artform.feed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import it.artform.AFGlobal;
import it.artform.R;
import it.artform.pojos.Post;
import it.artform.web.ArtformApiEndpointInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostArrayAdapter extends ArrayAdapter<Post> {
    private Context ctx = null;
    private int res = 0;
    private Post[] postList = null;
    ArtformApiEndpointInterface apiService = null;

    static class ViewHolder {
        ImageView postImageView;
        TextView titleTextView;
        TextView userTextView;
        TextView topicTextView;
        TextView tagsTextView;
        TextView likeTextView;
        ImageButton optionImageButton; //elimina se il post è proprio, altrimenti salva
    }

    public PostArrayAdapter(Context context, int resource, Post[] objects) {
        super(context, resource, objects);
        ctx = context;
        res = resource;
        postList = objects;
        apiService = AFGlobal.getInstance().getRetrofit().create(ArtformApiEndpointInterface.class);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent, false);
            ViewHolder vh = new ViewHolder();
            vh.postImageView = convertView.findViewById(R.id.postImageView);
            vh.titleTextView = convertView.findViewById(R.id.titleTextView);
            vh.userTextView = convertView.findViewById(R.id.userTextView);
            vh.topicTextView = convertView.findViewById(R.id.topicTextView);
            vh.tagsTextView = convertView.findViewById(R.id.tagsTextView);
            vh.likeTextView = convertView.findViewById(R.id.likeTextView);
            vh.optionImageButton = convertView.findViewById(R.id.optionImageButton);
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        Post p = postList[position];
        vh.userTextView.setText(String.valueOf(p.getUser()));
        vh.titleTextView.setText(String.valueOf(p.getTitle()));
        vh.topicTextView.setText(String.valueOf(p.getTopic()));
        vh.tagsTextView.setText(String.valueOf(p.getTags()));
        String postResUri = AFGlobal.POST_IMAGE_PATH + p.getId() + ".jpg";
        Picasso.get().load(postResUri).resize(900, 0).into(vh.postImageView);
        vh.likeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giveLikeToPost(p);
            }
        });
        if(p.getUser().equals(AFGlobal.getLoggedUser())) {
            vh.optionImageButton.setImageResource(R.drawable.outline_delete_forever_24);
            vh.optionImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(ctx)
                            .setTitle("Delete post")
                            .setMessage("Do you really want to delete this post?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    deletePost(p);
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
        }
        else {
            vh.optionImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    savePost(p);
                }
            });
        }
        return convertView;
    }

    private void giveLikeToPost(Post p) {
        p.giveLike();
        Call<Post> updatePostCall = apiService.updatePost(p.getId(), p);
        updatePostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful())
                    Toast.makeText(ctx, "You liked " + p.getUser() + "'s post", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(ctx, "Error while giving like to the post" + p.getUser(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void savePost(Post p) {
        Call<Post> savePostCall = apiService.addPostToSaved(AFGlobal.getLoggedUser(), p.getId());
        savePostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful())
                    Toast.makeText(ctx, "Post saved", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(ctx, "Post not saved. Maybe it is already", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deletePost(Post p) {
        Call<ResponseBody> deletePostCall = apiService.deletePost(p.getId());
        deletePostCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                    Toast.makeText(ctx, "Post deleted", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ctx, "Error while deleting the post", Toast.LENGTH_LONG).show();
            }
        });
    }

}
