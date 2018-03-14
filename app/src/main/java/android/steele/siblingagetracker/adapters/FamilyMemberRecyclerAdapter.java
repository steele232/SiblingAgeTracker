package android.steele.siblingagetracker.adapters;

import android.steele.siblingagetracker.R;
import android.steele.siblingagetracker.interfaces.FMOnClickListener;
import android.steele.siblingagetracker.db.FamilyMember;
import android.steele.siblingagetracker.service.AgeCalculator;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jonathansteele on 3/3/18.
 */

public class FamilyMemberRecyclerAdapter extends
        RecyclerView.Adapter<FamilyMemberRecyclerAdapter.FMViewHolder> {

    private static final DateFormat localizedDateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());

    private FMOnClickListener _listener;

    private ArrayList<FamilyMember> _dataset;

    public static class FMViewHolder extends RecyclerView.ViewHolder {

        public TextView _nameView;
        public TextView _birthdateView;
        public TextView _ageView;
        public FMOnClickListener _listener;
        public int _position;
        public FMViewHolder(View view, FMOnClickListener listener) {
            super(view);
            _nameView = (TextView) view.findViewById(R.id.name);
            _birthdateView = (TextView) view.findViewById(R.id.birthdate);
            _ageView = (TextView) view.findViewById(R.id.age);
            _listener = listener;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _listener.onItemClick(_position);
                }
            });
        }

        public void bindFamilyMember(FamilyMember fm, int position) {
            _position = position;

            _nameView.setText(fm.getName());

            String birthdateString = localizedDateFormatter.format(fm.getBirthdate().getTime());
            _birthdateView.setText(birthdateString);


            //TODO Make sure I'm using the Age Calculator right..
            int age = AgeCalculator.calculateAge(
                    fm.getBirthdate().get(Calendar.YEAR),
                    fm.getBirthdate().get(Calendar.MONTH),
                    fm.getBirthdate().get(Calendar.DAY_OF_MONTH)
            );

            _ageView.setText(Integer.toString(age));

        }
    }


    public FamilyMemberRecyclerAdapter(ArrayList<FamilyMember> newDataSet, FMOnClickListener listener) {
        _dataset = newDataSet;
        _listener = listener;
    }

    public void setList(ArrayList<FamilyMember> newDataSet) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(_dataset, newDataSet));

        result.dispatchUpdatesTo(this);

        _dataset = newDataSet;
    }

    @Override
    public FMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflate a view from the row layout
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_family_member, parent, false);

        //create a Viewholder to hold it then return it
        FMViewHolder viewHolder = new FMViewHolder(view, _listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FMViewHolder holder, int position) {
        holder.bindFamilyMember(_dataset.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (_dataset != null) {
            return _dataset.size();
        }
        return 0;
    }

    public class DiffCallback extends DiffUtil.Callback {

        private ArrayList<FamilyMember> _oldList;
        private ArrayList<FamilyMember> _newList;


        DiffCallback(
                ArrayList<FamilyMember> oldList,
                ArrayList<FamilyMember> newList
        ) {
            _oldList = oldList;
            _newList = newList;

        }

        @Override
        public int getOldListSize() {
            return _oldList.size();
        }

        @Override
        public int getNewListSize() {
            return _newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return true;
            //want to compare apples to apples and apple2 to apple2. (types && id's)
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return (
                        _oldList.get(oldItemPosition).getName().equals(_newList.get(newItemPosition).getName()) &&
                        _oldList.get(oldItemPosition).getBirthdate().equals(_newList.get(newItemPosition).getBirthdate())
                    );
        }

    }

}
