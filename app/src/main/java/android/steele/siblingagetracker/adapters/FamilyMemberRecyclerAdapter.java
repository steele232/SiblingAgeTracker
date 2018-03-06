package android.steele.siblingagetracker.adapters;

import android.steele.siblingagetracker.R;
import android.steele.siblingagetracker.interfaces.FMOnClickListener;
import android.steele.siblingagetracker.model.FamilyMember;
import android.steele.siblingagetracker.service.AgeCalculator;
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


    //TODO make ViewHolder
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

            _nameView.setText(fm.name);

            String birthdateString = localizedDateFormatter.format(fm.birthdate.getTime());
            _birthdateView.setText(birthdateString);


            //TODO Make sure I'm using the Age Calculator right..
            int age = AgeCalculator.calculateAge(
                    fm.birthdate.get(Calendar.YEAR),
                    fm.birthdate.get(Calendar.MONTH),
                    fm.birthdate.get(Calendar.DAY_OF_MONTH)
            );

            _ageView.setText(Integer.toString(age));

        }
    }


    //TODO fill in the rest

    public FamilyMemberRecyclerAdapter(ArrayList<FamilyMember> newDataSet, FMOnClickListener listener) {
        _dataset = newDataSet;
        _listener = listener;
    }

    public void setList(ArrayList<FamilyMember> newDataSet) {
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
}
