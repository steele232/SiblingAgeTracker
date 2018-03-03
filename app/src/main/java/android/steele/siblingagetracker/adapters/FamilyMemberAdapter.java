package android.steele.siblingagetracker.adapters;

import android.content.Context;
import android.steele.siblingagetracker.R;
import android.steele.siblingagetracker.model.FamilyMember;
import android.steele.siblingagetracker.service.AgeCalculator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;


public class FamilyMemberAdapter extends ArrayAdapter<FamilyMember> {

    private Context _context;
    private List<FamilyMember> _familyMemberList;
    private static final DateFormat localizedDateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
    private Inflater _inflater;


    public FamilyMemberAdapter(Context context, int resource, List<FamilyMember> objects) {
        super(context, resource, objects);
        this._context = context;
        this._familyMemberList = objects;
        _inflater = new Inflater();
    }

    public FamilyMemberAdapter(Context context, int resource) {
        super(context, resource);
        this._context = context;
        this._familyMemberList = new ArrayList<FamilyMember>();
        _inflater = new Inflater();
    }

    @Override
    public int getCount () {
        if (_familyMemberList != null) {
            return _familyMemberList.size();
        } else {
            return 0;
        }
    }

    public void setList(ArrayList<FamilyMember> newList) {
        _familyMemberList = newList;
        notifyDataSetChanged();
    }


    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public FamilyMember getItem (int position) {
        return _familyMemberList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_family_member, parent, false);
        }

        FamilyMember fm = _familyMemberList.get(position);

        //set the name
        ((TextView)convertView.findViewById(R.id.name)).setText(fm.name);

        //set the birthday
        GregorianCalendar calendar = fm.birthdate;
        int birthMonth = calendar.get(GregorianCalendar.MONTH);
        int birthDay = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        int birthYear = calendar.get(GregorianCalendar.YEAR);
        ((TextView)convertView.findViewById(R.id.birthdate)).setText(
                localizedDateFormatter.format(calendar.getTime())
        );

        //set the Age
        TextView ageView = (TextView) convertView.findViewById(R.id.age);

        int age = AgeCalculator.calculateAge(birthYear, birthMonth, birthDay);
        String ageString = Integer.toString(age);
        ageView.setText(ageString);

        return convertView;
    }
//
//    public class ViewHolder {
//
//        //BLOCK
//        private View row;
//        private TextView
//            nameText = null,
//            birthdateText = null,
//            ageText = null;
//
//        public ViewHolder(View row) {
//            this.row = row;
//        }
//
//        public View getRow() {
//            return row;
//        }
//
//        public void setRow(View row) {
//            this.row = row;
//        }
//
//        public TextView getNameText() {
//            return nameText;
//        }
//
//        public void setNameText(TextView nameText) {
//            this.nameText = nameText;
//        }
//
//        public TextView getBirthdateText() {
//            return birthdateText;
//        }
//
//        public void setBirthdateText(TextView birthdateText) {
//            this.birthdateText = birthdateText;
//        }
//
//        public TextView getAgeText() {
//            return ageText;
//        }
//
//        public void setAgeText(TextView ageText) {
//            this.ageText = ageText;
//        }
//
//    }
}
