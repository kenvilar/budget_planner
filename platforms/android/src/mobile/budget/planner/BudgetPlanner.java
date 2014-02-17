/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package mobile.budget.planner;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mobile.budget.db.DatabaseLayer;
import mobile.budget.model.BudgetModel;

import org.apache.cordova.CordovaActivity;

public class BudgetPlanner extends CordovaActivity {
	EditText description, amount;
	Button save, show;
	DatabaseLayer dbLayer = null;
	String saveAlertMessage = null;
	Intent showBudget;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// super.init();
		// Set by <content src="index.html" /> in config.xml
		// super.loadUrl(Config.getStartUrl());
		super.loadUrl("file:///android_asset/www/index.html");
		/*setContentView(R.layout.activity_main);
		dbLayer = new DatabaseLayer(this);
		showBudget = new Intent(this, ShowBudget.class);
		description = (EditText) findViewById(R.id.description);
		amount = (EditText) findViewById(R.id.amount);
		save = (Button) findViewById(R.id.btnSave);
		show = (Button) findViewById(R.id.btnShow);
		save.setOnClickListener(onSave);
		show.setOnClickListener(onShow);
		*/
		
	}

	private View.OnClickListener onShow = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivity(showBudget);
		}
	};

	private View.OnClickListener onSave = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			try {
				String bDescription;
				long bAmount; 
				bDescription = description.getText().toString();
				bAmount = Long.parseLong(amount.getText().toString());
				BudgetModel budgetModel = new BudgetModel();
				budgetModel.setDescription(bDescription);
				budgetModel.setAmount(bAmount);
				dbLayer.open();
				boolean flag = dbLayer.saveBudget(budgetModel);
				if (flag == true) {
					saveAlertMessage = "Added SuccessFully";
					alertMessage(saveAlertMessage);
				} else {
					saveAlertMessage = "The Budget you entered is already exist!";
					alertMessage(saveAlertMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
				alertMessage("Please fill in the form");
			} finally {
				dbLayer.close();
			}
		}
	};

	public void alertMessage(String dialMsg) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("Add Budget");
		alertBuilder.setMessage(dialMsg);
		alertBuilder.setCancelable(false);
		alertBuilder.setPositiveButton("Ok", null);
		alertBuilder.show();
	}
}
