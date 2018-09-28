package com.samjameskennedy.contentextractor.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

public class Weights {

    private int positiveCssClasses;
    private int positiveCssIds;
    private int unlikelyCssClasses;
    private int unlikelyCssIds;
    private int negativeCssClasses;
    private int negativeCssIds;
    private int noMatch;

    public Weights() {
        try {
            JSONObject jsonObject = readWeightsFile();

            positiveCssClasses = (int) jsonObject.get("positiveCssClasses");
            positiveCssIds = (int) jsonObject.get("positiveCssIds");
            unlikelyCssClasses = (int) jsonObject.get("unlikelyCssClasses");
            unlikelyCssIds = (int) jsonObject.get("unlikelyCssIds");
            negativeCssClasses = (int) jsonObject.get("negativeCssClasses");
            negativeCssIds = (int) jsonObject.get("negativeCssIds");
            noMatch = (int) jsonObject.get("noMatch");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getPositiveCssClasses() {
        return positiveCssClasses;
    }

    public int getPositiveCssIds() {
        return positiveCssIds;
    }

    public int getUnlikelyCssClasses() {
        return unlikelyCssClasses;
    }

    public int getUnlikelyCssIds() {
        return unlikelyCssIds;
    }

    public int getNegativeCssClasses() {
        return negativeCssClasses;
    }

    public int getNegativeCssIds() {
        return negativeCssIds;
    }

    public int getNoMatch() {
        return noMatch;
    }

    private JSONObject readWeightsFile() {
        InputStream inputStream = null;
        JSONObject json = null;
        try {
            inputStream = getClass().getClassLoader()
                    .getResourceAsStream("weights.json");

            if (inputStream != null) {
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
                json = new JSONObject(responseStrBuilder.toString());
                return json;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return json;
    }
}
