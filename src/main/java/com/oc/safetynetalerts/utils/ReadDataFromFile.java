package com.oc.safetynetalerts.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynetalerts.model.RawData;

import java.io.File;
import java.io.IOException;

public class ReadDataFromFile {
    private static final String FILE_PATH = "./src/main/resources/data.json";

    public static RawData getData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(FILE_PATH), RawData.class);
    }

}