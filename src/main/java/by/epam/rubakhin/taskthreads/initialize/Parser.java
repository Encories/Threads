package by.epam.rubakhin.taskthreads.initialize;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


class Parser {
    private final static org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger("Parser");

    List<String> findInformationInFile(String filename, String pattern){
        List<String> resultList = null;

        try (Scanner lineScanner = new Scanner(new FileReader(filename))){
            while(lineScanner.hasNext()){
                String founded = lineScanner.nextLine();
                if(founded.startsWith(pattern)){
                    resultList = new ArrayList<>(Arrays.asList(founded.split(" ")));
                }
            }
        } catch (FileNotFoundException e) {
            LOG.fatal("FileNotFoundException " + e);
            throw new RuntimeException();
        }
        resultList.remove(0);
        return resultList;
    }
}
