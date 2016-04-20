package ser402team.weallcode;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by KBasra on 4/1/16.
 */
public class GravatarAdapter extends BaseAdapter {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final int mAvatarImageViewPixelSize;
    private List<GravatarActivity.User> mUsers = Collections.emptyList();

    public GravatarAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mAvatarImageViewPixelSize = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
    }

    public void updateUsers(List<GravatarActivity.User> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public GravatarActivity.User getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.gravatar_list_item, null);
        }

        GravatarActivity.User user = getItem(position);

        String gravatarUrl = Gravatar.init().with(user.email).force404().size(mAvatarImageViewPixelSize).build();
        Picasso.with(mContext)
                .load(gravatarUrl)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into((ImageView) convertView.findViewById(R.id.user_avatar));

        ((TextView) convertView.findViewById(R.id.user_name)).setText(user.name);

        return convertView;
    }
}
