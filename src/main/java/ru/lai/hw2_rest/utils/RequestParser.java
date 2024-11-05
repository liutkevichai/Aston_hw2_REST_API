package ru.lai.hw2_rest.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RequestParser {
    private static final Logger logger = Logger.getLogger(RequestParser.class.getName());

    public static Map<String, String> getParameterMap(HttpServletRequest request) {

        BufferedReader br = null;
        Map<String, String> dataMap = null;

        try {

            InputStreamReader reader = new InputStreamReader(request.getInputStream());
            br = new BufferedReader(reader);

            String data = br.readLine();

            dataMap = Arrays.stream(data.split("&"))
                    .map(s -> s.split("=", 2))
                    .filter(pair -> pair.length == 2)
                    .collect(Collectors.toMap(
                            pair -> pair[0].trim(),
                            pair -> pair[1].trim()
                    ));

            return dataMap;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    logger.log(Level.WARNING, null, ex);
                }
            }
        }
        return dataMap;
    }

}
