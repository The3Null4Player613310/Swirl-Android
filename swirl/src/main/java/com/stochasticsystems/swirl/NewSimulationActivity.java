package com.stochasticsystems.swirl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.stochasticsystems.swirl.engine.SwirlEngine;
import com.stochasticsystems.swirl.engine.SwirlParameterBuilder;
import com.stochasticsystems.swirl.engine.SwirlParameterBundle;

import java.io.FileOutputStream;

public class NewSimulationActivity extends AppCompatActivity {

    SwirlParameterBuilder parameterBuilder;
    String simulationName = "simulationName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Button beginButton = (Button) findViewById(R.id.begin_button);
        Button saveButton = (Button) findViewById(R.id.save_button);

        parameterBuilder = new SwirlParameterBuilder();

        //new simulation button listener
        beginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent beginIntent = new Intent(NewSimulationActivity.this, SimulationActivity.class);
                beginIntent.putExtra("simulation_name", simulationName);
                beginIntent.putExtra("simulation_parameters", makeParams(simulationName));
                startActivity(beginIntent);
            }
        });

        //load simulation button listener
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                makeParams(simulationName);
                finish();
            }
        });

    }

    String makeParams(String fileNameIn)
    {
        parameterBuilder.setDefaults();
        parameterBuilder.setNRuns(SwirlEngine.N_RUNS_DEFAULT / 10);
        parameterBuilder.setNPeriods(SwirlEngine.N_PERIODS_MAX);
        parameterBuilder.setCarryingCapacity(SwirlEngine.CARRYING_CAPACITY_DEFAULT * 2);
        parameterBuilder.setSDCarryingCapacity(SwirlEngine.CARRYING_CAPACITY_DEFAULT / 2);
        SwirlParameterBundle params = parameterBuilder.build();
        if (params != null)
        {
            String paramString = params.toDelimitedString("\n");
            try
            {
                FileOutputStream fos = openFileOutput(fileNameIn, Context.MODE_PRIVATE);
                fos.write(paramString.getBytes());
                fos.close();
            } catch (Exception e)
            {
            }
            return paramString;
        }
        return null;
    }
}
