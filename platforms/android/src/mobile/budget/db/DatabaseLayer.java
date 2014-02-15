package mobile.budget.db;

import java.util.ArrayList;
import java.util.List;

import mobile.budget.model.BudgetModel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseLayer {
	String budgetAmount[] = { "amount" };
	String budgetDescription[] = { "description" };
	DatabaseHandler dbHandler = null;
	SQLiteDatabase db = null;

	public void open() throws SQLException {
		db = dbHandler.getWritableDatabase();
	}

	public DatabaseLayer(Context context) {
		dbHandler = new DatabaseHandler(context);
	}

	public boolean saveBudget(BudgetModel svbudget) {
		ContentValues cvalues = new ContentValues();
		cvalues.put("amount", svbudget.getAmount());
		cvalues.put("description", svbudget.getDescription());
		long id = db.insert("budget", null, cvalues);
		if (id > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<String> getNames() {
		List<String> budgetList = new ArrayList<String>();
		Cursor cursor = db.query("budget", budgetDescription, null, null, null,
				null, null);
		while (cursor.moveToNext()) {
			String strc = cursor.getString(0);
			budgetList.add(strc);
		}
		return budgetList;
	}

	public boolean editBudget(BudgetModel edtbudget) {
		ContentValues cvalues = new ContentValues();
		String description[] = { edtbudget.getDescription() };
		cvalues.put("amount", edtbudget.getAmount());
		long id = db.update("budget", cvalues, "description=?", description);
		if (id > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteBudget(String delBudget) {
		String strDelBudget[] = { delBudget };
		db.delete("budget", "description=?", strDelBudget);
	}

	public long getAmount(String gtamount) {
		long numZ;
		numZ = 0;
		String strAmount[] = { gtamount };
		Cursor cursor = db.query("budget", budgetAmount, "description=?",
				strAmount, null, null, null);
		while (cursor.moveToNext()) {
			numZ = cursor.getLong(0);
		}
		return numZ;
	}

	public void close() {
		db.close();
	}
}