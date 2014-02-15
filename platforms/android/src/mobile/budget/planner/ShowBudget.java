package mobile.budget.planner;

import java.util.List;

import mobile.budget.db.DatabaseLayer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowBudget extends ListActivity {
	DatabaseLayer dbLayer;
	List<String> list;
	ArrayAdapter<String> arAdapter;
	Intent showEditBudget;

	@Override
	protected void onListItemClick(ListView lv, View view, int position, long id) {
		String str = (String) getListAdapter().getItem(position);
		showEditBudget.putExtra("mobile.budget.description", str);
		startActivity(showEditBudget);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showEditBudget = new Intent(this, EditBudget.class);
		dbLayer = new DatabaseLayer(this);
		dbLayer.open();
		list = dbLayer.getNames();
		arAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		setListAdapter(arAdapter);
		dbLayer.close();
	}
}