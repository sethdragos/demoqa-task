package com.lottoland.interview.util;

import com.intuit.karate.cucumber.CucumberRunner;
import com.jayway.jsonpath.JsonPath;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class ReportsRunner {

    public static void generateCucumberReport(String karateOutputPath) {
        Collection<File> jsonFiles = readJsonFiles(karateOutputPath, true);
        List<String> jsonPaths = new ArrayList<>();
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), ReportsRunner.class.getPackage().getName());
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }

    public static Map<Integer, JSONObject> generateTestRailResultsMap(String karateOutputPath) throws IOException {
        Map<Integer, JSONObject> resultsMap = new HashMap<>();
        Collection<File> jsonFiles = readJsonFiles(karateOutputPath, true);
        for (File file : jsonFiles) {
            Object object = com.jayway.jsonpath.Configuration.defaultConfiguration().jsonProvider().parse(readFileToString(file));
            JSONArray resultsArray = JsonPath.read(object, "*.elements.*");
            for (int i=0; i<resultsArray.size(); i++) {
                Object resultObj = resultsArray.get(i);
                String title = (String)((LinkedHashMap) resultObj).get("name");
                String type = (String)((LinkedHashMap) resultObj).get("type");
                if (title != null && !title.contentEquals("") && type.contentEquals("scenario")) {
                    int testCaseId = extractTestCaseId(title);
                    String status = "1";
                    String errorMessage = "";
                    JSONArray stepsArray = (JSONArray)((LinkedHashMap) resultObj).get("steps");
                    for (int j=0; j<stepsArray.size(); j++) {
                        Object stepObj = stepsArray.get(j);
                        Object resObj = ((LinkedHashMap) stepObj).get("result");
                        String stepStatus = (String)((LinkedHashMap) resObj).get("status");
                        switch (stepStatus) {
                            case "failed": {
                                status = "5";
                                errorMessage = (String)((LinkedHashMap) resObj).get("error_message");
                                break;
                            }
                            case "ignored": {
                                status = "3";
                                break;
                            }
                        }
                    }
                    JSONObject resultObject;
                    switch (status) {
                        case "1" : {
                            resultObject = new JSONObject();
                            resultObject.put("status_id", status);
                            resultsMap.put(testCaseId, resultObject);
                            break;
                        }
                        case "3": {
                            resultObject = new JSONObject();
                            resultObject.put("status_id", status);
                            resultsMap.put(testCaseId, resultObject);
                            break;
                        }
                        case "5": {
                            resultObject = new JSONObject();
                            resultObject.put("status_id", "5");
                            resultObject.put("comment", errorMessage);
                            resultsMap.put(testCaseId, resultObject);
                            break;
                        }
                    }
                }
            }
        }
        return resultsMap;
    }

    private static String readFileToString(File file) throws IOException {
        String fileContent;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fileContent = sb.toString();
            br.close();
        }
        return fileContent;
    }

    private static Collection<File> readJsonFiles(String path, boolean recursive) {
        return FileUtils.listFiles(new File(path), new String[] {"json"}, recursive);
    }

    private static int extractTestCaseId(String title) {
        int testCaseId = 0;
        String[] words = title.split(" - ");
        try {
            testCaseId = Integer.parseInt(words[words.length - 1]);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return testCaseId;
    }

    public static void main(String[] args) throws IOException {
        final String karateOutputPath = "target/surefire-reports";
        CucumberRunner.parallel(ReportsRunner.class, 5, karateOutputPath);
        generateCucumberReport(karateOutputPath);

        TestRailUtils testRailUtils = new TestRailUtils();
//        testRailUtils.publishResults(generateTestRailResultsMap(karateOutputPath));
    }
}
