package mobile.budget.planner;

import mobile.budget.db.DatabaseLayer;
import mobile.budget.model.BudgetModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditBudget extends Activity {
	DatabaseLayer dbLayer;
	Button btnUpdate, btnDelete, btnShow;
	long valNum = 0;
	String mbd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		dbLayer = new DatabaseLayer(this);
		final Intent showEditBudget = getIntent();
		mbd = showEditBudget.getStringExtra("mobile.budget.description");
		TextView tv = (TextView) findViewById(R.id.description);
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnShow = (Button) findViewById(R.id.btnShow);
		tv.setText(mbd.toLowerCase());
		btnUpdate.setOnClickListener(onUpdate);
		btnDelete.setOnClickListener(onDelete);
		btnShow.setOnClickListener(onShow);
	}

	View.OnClickListener onShow = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			dbLayer.open();
			long numAmount = dbLayer.getAmount(mbd);
			dbLayer.close();
			alertMessage("Budget Amount:" + numAmount);

		}
	};

	View.OnClickListener onDelete = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				dbLayer.open();
				dbLayer.deleteBudget(mbd);
				dbLayer.close();
				alertMessage(mbd + " Deleted successfully");
			} catch (Exception e) {
				e.printStackTrace();
				alertMessage("Deletetion Error");
			}
		}
	};

	View.OnClickListener onUpdate = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			updateDialog();
		}
	};

	public void updateDialog() {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("Edit Budget");
		alertBuilder.setMessage("Enter your new budget amount");
		final EditText newAmount = new EditText(this);
		alertBuilder.setCancelable(false);
		alertBuilder.setView(newAmount);

		alertBuilder.setPositiveButton("Edit",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						valNum = Long.parseLong(newAmount.getText().toString());
						try {
							dbLayer.open();
							BudgetModel budgetModel = new BudgetModel();
							budgetModel.setDescription(mbd);
							budgetModel.setAmount(valNum);
							dbLayer.editBudget(budgetModel);
							alertMessage("Edit Successfully");

						} catch (Exception e) {
							e.printStackTrace();
							alertMessage("Edit Error!");
						} finally {
							dbLayer.close();
						}

					}
				});
		alertBuilder.setNegativeButton("Cancel", null);
		alertBuilder.show();
	}

	public void alertMessage(String dialogMsg) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("Mobile Budget Planner");
		alertBuilder.setMessage(dialogMsg);
		alertBuilder.setCancelable(false);
		alertBuilder.setPositiveButton("Ok", null);
		alertBuilder.show();
	}
}