package com.stochasticsystems.swirl;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.stochasticsystems.swirl.engine.SwirlEngine;
import com.stochasticsystems.swirl.engine.SwirlOutputBundle;
import com.stochasticsystems.swirl.engine.SwirlParameterBundle;

public class SimulationService extends Service {

    private final IBinder mBinder = new LocalBinder();
    Activity boundContext;
    SwirlOutputBundle lastResults;
    int NOTIFICATION_ID = 1;


    public SimulationService() {

    }

    @Override
    public void onCreate() {
        updateNotification("Initializing Simulation...", "Initializing...", 0, 0);
    }

    @Override
    public void onDestroy()
    {
        updateNotification("Simulation ShuttingDown...", "ShuttingDown...", 0, 0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {

    }

    @Override
    public void onLowMemory()
    {
        updateNotification("Warning Low Memory...", "Memory Warning...", 0, 0);
    }

    @Override
    public void onTrimMemory(int level)
    {

    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        setContext(null);
        return true;
    }

    @Override
    public void onRebind(Intent intent)
    {

    }

    public void setContext(Activity contextIn)
    {
        boundContext = contextIn;
    }

    void updateNotification(String contentText, String tickerText, int progressCur, int progressMax)
    {
        Intent notificationIntent = new Intent(this, SimulationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_swirl)
                .setContentTitle("Swirl")
                .setContentText(contentText)
                .setTicker(tickerText)
                .setContentIntent(pendingIntent)
                .setProgress(progressMax, progressCur, false)
                .build();
        startForeground(1, notification);
    }

    public void newSimulation(SwirlParameterBundle swirlParameterBundleIn)
    {
        updateNotification("Simulation Started...", "", 0, 0);
        new SimulationTask().execute(swirlParameterBundleIn);
    }

    public void drawGraph()
    {
        GraphView graph = boundContext.findViewById(R.id.graph);
        double[] data = lastResults.getSummaryMeans();
        //for(int i=0;i<2;i++)
        //{
        DataPoint[] datapoints = new DataPoint[data.length];
        for (int j = 0; j < data.length; j++)
        {
            datapoints[j] = new DataPoint((double) j, data[j]);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(datapoints);
        graph.addSeries(series);
        //}

    }

    private class SimulationTask extends AsyncTask<SwirlParameterBundle, Integer, SwirlOutputBundle>
    {
        @Override
        protected SwirlOutputBundle doInBackground(SwirlParameterBundle... swirlParameterBundles)
        {
            SwirlOutputBundle swirlOutputBundle = new SwirlOutputBundle(swirlParameterBundles[0]);
            SwirlEngine engine = new SwirlEngine(swirlParameterBundles[0]);
            engine.initialize();
            do
            {
                //swirlParameterBundles[0].getReportingInterval()
                engine.iterate(1);
                publishProgress(engine.iterationsCompleted(), (engine.iterationsLeft() + engine.iterationsCompleted()));
            } while (!engine.isComplete());
            engine.complete();
            swirlOutputBundle.addData(engine.getData(0));
            //engine.completeResults();
            return swirlOutputBundle;
        }

        @Override
        protected void onPreExecute()
        {
            updateNotification("Initalizing Simulation...", "Initializing...", 0, 0);
        }

        @Override
        protected void onPostExecute(SwirlOutputBundle result)
        {
            updateNotification("Simulation Complete...", "Simulation Complete...", 0, 0);
            lastResults = result;
            if (boundContext != null)
                drawGraph();
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            updateNotification("Simulation Running ...", "", values[0], values[1]);
        }

        @Override
        protected void onCancelled(SwirlOutputBundle result)
        {
            updateNotification("Simulation Canceled ...", "Simulation Canceled...", 0, 0);
        }

        @Override
        protected void onCancelled()
        {
            updateNotification("Simulation Canceled ...", "Simulation Canceled...", 0, 0);
        }
    }

    public class LocalBinder extends Binder
    {
        SimulationService getService()
        {
            // Return this instance of LocalService so clients can call public methods
            return SimulationService.this;
        }
    }
}
