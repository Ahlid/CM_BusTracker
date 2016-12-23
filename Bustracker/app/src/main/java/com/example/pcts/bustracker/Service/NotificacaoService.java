package com.example.pcts.bustracker.Service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.example.pcts.bustracker.Lists.NotificacaoAdapter;
import com.example.pcts.bustracker.Managers.GestorInformacao;
import com.example.pcts.bustracker.Managers.GestorNotificacao;
import com.example.pcts.bustracker.Model.Paragem;
import com.example.pcts.bustracker.Model.Viagem;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by pcts on 12/22/2016.
 */

public class NotificacaoService extends Service {

    GestorNotificacao gestorNotificacao;
    GestorInformacao gestorInformacao;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    public ArrayList<LatLng> pathSimulacao;
    public Stack<LatLng> posicoesParagens;

    public NotificacaoService() {
        this.gestorNotificacao = GestorNotificacao.getInstance();
        this.gestorInformacao = GestorInformacao.getInstance();
        this.pathSimulacao = new ArrayList<>();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        // To avoid cpu-blocking, we create a background handler to run our service
        HandlerThread thread = new HandlerThread("TutorialService",
                Process.THREAD_PRIORITY_BACKGROUND);
        // start the new handler thread
        thread.start();

        mServiceLooper = thread.getLooper();
        // start the service using the background handler
        mServiceHandler = new ServiceHandler(mServiceLooper);


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Starting bustracker service", Toast.LENGTH_SHORT).show();

        posicoesParagens = new Stack<>();

        posicoesParagens.push(GestorInformacao.getInstance().findCarreiraById(1).getTrajeto().get(4).getPosicao());
        posicoesParagens.push(GestorInformacao.getInstance().findCarreiraById(1).getTrajeto().get(3).getPosicao());
        posicoesParagens.push(GestorInformacao.getInstance().findCarreiraById(1).getTrajeto().get(2).getPosicao());
        posicoesParagens.push(GestorInformacao.getInstance().findCarreiraById(1).getTrajeto().get(1).getPosicao());
        posicoesParagens.push(GestorInformacao.getInstance().findCarreiraById(1).getTrajeto().get(0).getPosicao());



        String url = getUrl(gestorInformacao.findCarreiraById(1).getTrajeto().get(0), gestorInformacao.findCarreiraById(1).getTrajeto().get(1));

        Log.d("GMAP", url);


        new FetchUrl().execute(url);
        url = getUrl(gestorInformacao.findCarreiraById(1).getTrajeto().get(1), gestorInformacao.findCarreiraById(1).getTrajeto().get(2));

        Log.d("GMAP", url);

        new FetchUrl().execute(url);
        url = getUrl(gestorInformacao.findCarreiraById(1).getTrajeto().get(2), gestorInformacao.findCarreiraById(1).getTrajeto().get(3));

        Log.d("GMAP", url);

        new FetchUrl().execute(url);
        url = getUrl(gestorInformacao.findCarreiraById(1).getTrajeto().get(3), gestorInformacao.findCarreiraById(1).getTrajeto().get(4));

        Log.d("GMAP", url);

        new FetchUrl().execute(url);


        // call a new service handler. The service ID can be used to identify the service
        Message message = mServiceHandler.obtainMessage();
        message.arg1 = startId;
        mServiceHandler.sendMessage(message);

        return START_STICKY;
    }

    protected void showToast(final String msg) {
        //gets the main thread
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                // run this code in the main thread
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getUrl(Paragem inicio, Paragem fim) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?" + "" +
                "origin=" + inicio.getPosicao().latitude + "," + inicio.getPosicao().longitude +
                "&destination=" + fim.getPosicao().latitude + "," + fim.getPosicao().longitude +
                "&key=" + "AIzaSyBTmgCzFDzhKlQxLMMXWmbKwV6GcymF90s";

        return url;
    }


    private class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            while (pathSimulacao.size() < 100) {

                // Add your cpu-blocking activity here
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (pathSimulacao.size() > 0) {
                    showToast("Getting Data to simulate");
                } else {
                    showToast("Data simulation 100%");
                }


                // the msg.arg1 is the startId used in the onStartCommand,
                // so we can track the running sevice here.
                // stopSelf(msg.arg1);
            }


            showToast("Starting simulation...");

            List<Viagem> viagens = GestorInformacao.getInstance().getViagens();
            List<Simulador> simuladors = new ArrayList();
            for(Viagem v : viagens){

              simuladors.add(new Simulador(v, pathSimulacao));


            }

            showToast("Simulation started");

            for (Simulador s : simuladors){
                s.start();
            }



        }
    }


    /**
     * Fetches data from url passed
     */
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }

        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();
                Log.d("downloadUrl", data.toString());
                br.close();

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }

        private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

            // Parsing the data in non-ui thread
            @Override
            protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

                JSONObject jObject;
                List<List<HashMap<String, String>>> routes = null;

                try {
                    jObject = new JSONObject(jsonData[0]);
                    Log.d("ParserTask", jsonData[0].toString());
                    DataParser parser = new DataParser();
                    Log.d("ParserTask", parser.toString());

                    // Starts parsing data
                    routes = parser.parse(jObject);
                    Log.d("ParserTask", "Executing routes");
                    Log.d("ParserTask", routes.toString());

                } catch (Exception e) {
                    Log.d("ParserTask", e.toString());
                    e.printStackTrace();
                }
                return routes;
            }

            // Executes in UI thread, after the parsing process
            @Override
            protected void onPostExecute(List<List<HashMap<String, String>>> result) {
                ArrayList<LatLng> points;

                // Traversing through all the routes
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<>();

                    // Fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // Fetching all the points in i-th route
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }


                    pathSimulacao.add(posicoesParagens.pop());
                    for (LatLng ll : points) {
                        pathSimulacao.add(ll);
                    }




                    Log.d("onPostExecute", "onPostExecute lineoptions decoded" + pathSimulacao.get(0).latitude);
                    Log.d("onPostExecute", "onPostExecute lineoptions decoded" + pathSimulacao.toString());
                }
            }

            public class DataParser {

                /**
                 * Receives a JSONObject and returns a list of lists containing latitude and longitude
                 */
                public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

                    List<List<HashMap<String, String>>> routes = new ArrayList<>();
                    JSONArray jRoutes;
                    JSONArray jLegs;
                    JSONArray jSteps;

                    try {

                        jRoutes = jObject.getJSONArray("routes");

                        /** Traversing all routes */
                        for (int i = 0; i < jRoutes.length(); i++) {
                            jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                            List path = new ArrayList<>();

                            /** Traversing all legs */
                            for (int j = 0; j < jLegs.length(); j++) {
                                jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                                /** Traversing all steps */
                                for (int k = 0; k < jSteps.length(); k++) {
                                    String polyline = "";
                                    polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                                    List<LatLng> list = decodePoly(polyline);

                                    /** Traversing all points */
                                    for (int l = 0; l < list.size(); l++) {
                                        HashMap<String, String> hm = new HashMap<>();
                                        hm.put("lat", Double.toString((list.get(l)).latitude));
                                        hm.put("lng", Double.toString((list.get(l)).longitude));
                                        path.add(hm);
                                    }
                                }
                                routes.add(path);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                    }


                    return routes;
                }

                /**
                 * Method to decode polyline points
                 * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
                 */
                private List<LatLng> decodePoly(String encoded) {

                    List<LatLng> poly = new ArrayList<>();
                    int index = 0, len = encoded.length();
                    int lat = 0, lng = 0;

                    while (index < len) {
                        int b, shift = 0, result = 0;
                        do {
                            b = encoded.charAt(index++) - 63;
                            result |= (b & 0x1f) << shift;
                            shift += 5;
                        } while (b >= 0x20);
                        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                        lat += dlat;

                        shift = 0;
                        result = 0;
                        do {
                            b = encoded.charAt(index++) - 63;
                            result |= (b & 0x1f) << shift;
                            shift += 5;
                        } while (b >= 0x20);
                        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                        lng += dlng;

                        LatLng p = new LatLng((((double) lat / 1E5)),
                                (((double) lng / 1E5)));
                        poly.add(p);
                    }

                    return poly;
                }
            }
        }
    }


}
