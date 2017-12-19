package filme.projeto1.br.com.filme.Utils;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by G0036664 on 27/11/2017.
 */

public class OpenFilmeJsonUtils {
    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     * <p/>
     * Later on, we'll be parsing the JSON into structured data within the
     * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param filmJsonStr JSON response from server
     *
     * @return Array of Strings describing weather data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[] getSimpleFilmStringsFromJson(Context context, String filmJsonStr)
            throws JSONException {

        final String FILM_LIST = "results";

        /* Max temperature for the day */
        final String FILM_ID = "idFilm";
        final String FILM_TITLE = "nameFilm";
        final String FILM_POSTER = "posterFilm";
        final String FILM_DESCRIPTION = "overview";
        final String FILM_DATE = "release_date";

        /* String array to hold each day's weather String */
        String[] parsedFilm = null;

        JSONObject filmJson = new JSONObject(filmJsonStr);

        /* Is there an error? */
//        if (filmJsonStr.has(OWM_MESSAGE_CODE)) {
//            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);
//
//            switch (errorCode) {
//                case HttpURLConnection.HTTP_OK:
//                    break;
//                case HttpURLConnection.HTTP_NOT_FOUND:
//                    /* Location invalid */
//                    return null;
//                default:
//                    /* Server probably down */
//                    return null;
//            }
//        }

        JSONArray filmArray = filmJson.getJSONArray(FILM_LIST);

        parsedFilm = new String[filmArray.length()];

        for (int i = 0; i < filmArray.length(); i++) {

            Integer id;
            String poster;
            String title;
            String description;

            /* Get the JSON object representing the day */
            JSONObject film = filmArray.getJSONObject(i);

            id = film.getInt("id");
            poster = film.getString("poster_path");
            title = film.getString("title");
            description = film.getString(FILM_DESCRIPTION);

            parsedFilm[i] = title;
        }

        return parsedFilm;
    }

    /**
     * Parse the JSON and convert it into ContentValues that can be inserted into our database.
     *
     * @param context         An application context, such as a service or activity context.
     * @param filmJsonStr The JSON to parse into ContentValues.
     *
     * @return An array of ContentValues parsed from the JSON.
     */
    public static ContentValues[] getFullWeatherDataFromJson(Context context, String filmJsonStr) {
        /** This will be implemented in a future lesson **/
        return null;
    }
}
