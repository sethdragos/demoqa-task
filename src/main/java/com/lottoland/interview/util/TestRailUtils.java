package com.lottoland.interview.util;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.*;
import net.minidev.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

class TestRailUtils {

    private String testrailUrl, testrailUser, testrailPass;
    private Integer testrailProjId, testrailMilestoneId, testrailRunId;
    private List<Integer> testrailSectionIds;



    private static final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
    private static final String TESTRAIL_PROPERTIES_FILE = ".//src//test//resources//testrail.properties";

    private void loadProperties(String sourceFile) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(sourceFile);
        properties.load(inputStream);
        testrailUrl = properties.getProperty("testrail.url");
        testrailUser = properties.getProperty("testrail.user");
        testrailPass = properties.getProperty("testrail.pass");
        testrailProjId = Integer.parseInt(properties.getProperty("testrail.proj.id"));
        testrailMilestoneId = Integer.parseInt(properties.getProperty("testrail.milestone.id"));
        testrailRunId = Integer.parseInt(properties.getProperty("testrail.run.id"));
        String[] sectionIdsArray = properties.getProperty("testrail.section.ids").split(",");
        testrailSectionIds = new ArrayList<>();
        for (String id : sectionIdsArray) {
            testrailSectionIds.add(Integer.parseInt(id));
        }
    }

    void publishResults(Map<Integer, JSONObject> resultsMap) {

        try {
            loadProperties(TESTRAIL_PROPERTIES_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] userDecodedBytes = Base64.decodeBase64(testrailUser);
        byte[] passDecodedBytes = Base64.decodeBase64(testrailPass);

        String decodedUser = new String(userDecodedBytes);
        String decodedPass = new String(passDecodedBytes);

        TestRail testRail = TestRail.builder(testrailUrl, decodedUser, decodedPass).build();

        Project project = testRail.projects().get(testrailProjId).execute();

        Date date = new Date();
        String todayDate = sdf.format(date);

        try {
            testRail.runs().get(testrailRunId).execute();
        } catch (Exception e) {
            testrailRunId = testRail.runs().add(project.getId(),
                    new Run().setMilestoneId(testrailMilestoneId).setName("Test Run " + todayDate)).execute().getId();
        }

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();

        List<Case> cases = testRail.cases().list(testrailProjId, customCaseFields).execute();

        for (Case testCase : cases) {
            if (testrailSectionIds.contains(testCase.getSectionId())) {
                int testCaseId = testCase.getId();
                Object value = resultsMap.get(testCaseId);
                if (value != null) {
                    List<ResultField> customResultFields = testRail.resultFields().list().execute();
                    int status = ((JSONObject) value).getAsNumber("status_id").intValue();
                    if(status == 5) {
                        String comment = (String)((JSONObject) value).get("comment");
                        testRail.results().addForCase(testrailRunId, testCaseId, new Result().setStatusId(status).setComment(comment), customResultFields).execute();
                    } else {
                        testRail.results().addForCase(testrailRunId, testCaseId, new Result().setStatusId(status), customResultFields).execute();
                    }
                }
            }
        }
    }
}
