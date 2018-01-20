package se.caglabs.hunchback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Handler;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
@Named("mapBean")
public class Map {

    List<Rectangle> coordinatesOfObstacles;
    private static final String FILE_NAME_OF_MAP = "map.txt";
    private static final Long OBSTACLE = 1L;
    private static final int tileSize = 32;

    public Map() {
        coordinatesOfObstacles = new ArrayList<>(getCoordinatesOfAllObstacles());
    }

    @Handler
    public String getMap() {
        ArrayList<ArrayList<Long>> map = getMapAsListFromFile();
        int nrOfRows = map.size();
        int nfOfColumns = map.get(0).size();
        return toJSON(map, nrOfRows, nfOfColumns, tileSize);
    }

    private String toJSON(ArrayList<ArrayList<Long>> map, int nrOfRows, int nfOfColumns, int tileSize) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("nrOfRows", nrOfRows);
        rootNode.put("nrOfColumns", nfOfColumns);
        rootNode.put("tileSize", tileSize);

        // Skapa en karta som är en array och där varje rad också är en array.
        ArrayNode mapAsArray = rootNode.putArray("map");
        map.forEach(row -> {
            ArrayNode newArrayRow = mapAsArray.addArray();
            row.forEach(column -> newArrayRow.add(column.intValue()));
        });
        try {
            return mapper.writer().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressWarnings("unchecked")
    private ArrayList<ArrayList<Long>> getMapAsListFromFile() {
        final FileReader fileReader;
        try {
            fileReader = new FileReader(new ClassPathResource(FILE_NAME_OF_MAP).getFile().getPath());
        } catch (IOException e) {
            throw new NotFoundException("Can not find file: " + FILE_NAME_OF_MAP, e);
        }

        JSONParser jsonParser = new JSONParser();
        JSONArray mapAsJsonArray;
        try {
            mapAsJsonArray = (JSONArray) jsonParser.parse(fileReader);
        } catch (IOException | ParseException e) {
            throw new Error("Failed to parse file: " + FILE_NAME_OF_MAP, e);
        }

        return new ArrayList<ArrayList<Long>>(mapAsJsonArray);
    }

    private List<Rectangle> getCoordinatesOfAllObstacles() {
        ArrayList<ArrayList<Long>> mapAsJsonArray = getMapAsListFromFile();
        return findAllObstacleCoordinates(mapAsJsonArray);
    }

    private List<Rectangle> findAllObstacleCoordinates(ArrayList<ArrayList<Long>> map) {
        List<Rectangle> coordinatesOfObstacles = new ArrayList<>();
        AtomicInteger rowIndexGen = new AtomicInteger();
        map.forEach(row -> {
            int rowIndex = rowIndexGen.getAndIncrement();
            AtomicInteger colIndexGen = new AtomicInteger();
            row.forEach(column -> {
                int colIndex = colIndexGen.getAndIncrement();
                if (column.equals(OBSTACLE)) {
                    coordinatesOfObstacles.add(new Rectangle(colIndex * tileSize, rowIndex * tileSize, tileSize, tileSize));
                }
            });
        });
        return coordinatesOfObstacles;
    }
}


