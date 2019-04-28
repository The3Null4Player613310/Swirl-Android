package com.stochasticsystems.swirl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stochasticsystems.swirl.engine.SwirlParameterBundle;

public class LoadSimulationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        String simulationName = savedInstanceState.getString("simulation_name");
        String simulationParameters = savedInstanceState.getString("simulation_parameters");
        SwirlParameterBundle i = SwirlParameterBundle.fromDelimitedString(simulationParameters, "\n");
    }
}
