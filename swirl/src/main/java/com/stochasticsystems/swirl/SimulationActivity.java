package com.stochasticsystems.swirl;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.stochasticsystems.swirl.engine.SwirlEngine;
import com.stochasticsystems.swirl.engine.SwirlParameterBuilder;
import com.stochasticsystems.swirl.engine.SwirlParameterBundle;

public class SimulationActivity extends AppCompatActivity
{

    SwirlParameterBundle params;
    private SimulationService simulationService;
    private ServiceConnection simulationConnection = new ServiceConnection()
    {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            simulationService = ((SimulationService.LocalBinder) service).getService();
            simulationService.setContext(SimulationActivity.this);
            if (params != null)
                simulationService.newSimulation(params);
            else
                simulationService.drawGraph();
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            simulationService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (getIntent().hasExtra("simulation_parameters"))
        {
            String stringparam = getIntent().getStringExtra("simulation_parameters");
            params = SwirlParameterBundle.fromDelimitedString(stringparam, "\n");

            //quick fix for demo
            SwirlParameterBuilder parameterBuilder = new SwirlParameterBuilder();
            parameterBuilder.setDefaults();
            parameterBuilder.setNRuns(SwirlEngine.N_RUNS_DEFAULT);
            parameterBuilder.setNPeriods(SwirlEngine.N_PERIODS_MAX);
            parameterBuilder.setCarryingCapacity(SwirlEngine.CARRYING_CAPACITY_DEFAULT * 2);
            parameterBuilder.setSDCarryingCapacity(SwirlEngine.CARRYING_CAPACITY_DEFAULT / 2);
            params = parameterBuilder.build();
        }
        else
        {
            params = null;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        Intent intent = new Intent(this, SimulationService.class);
        boolean found = false;
        do
        {
            for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE))
            {
                if (SimulationService.class.getName().equals(service.service.getClassName()))
                {
                    found = true;
                }
            }
            if (!found)
            {
                startService(intent);
            }
        } while (!found);
        bindService(intent, simulationConnection, 0);
    }
}
