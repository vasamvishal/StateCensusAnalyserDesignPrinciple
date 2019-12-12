package censusanalyser;

import csvbuilderanalyser.CSVBuilderException;
import csvbuilderanalyser.CSVBuilderFactory;
import csvbuilderanalyser.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class censusLoader {

    public <E> Map<String, CensussDAO> loadCensusData(String csvFilePath, Class<E> CensusCSVClass) throws CensusAnalyserException {
        Map<String, CensussDAO> censusStateMap=new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCsvIterator = csvBuilder.
                    getCSVFileIterator(reader, CensusCSVClass);
            Iterable<E> csvIterable = () -> censusCsvIterator;
            if (CensusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCsv -> censusStateMap.put(censusCsv.state, new CensussDAO(censusCsv)));

            } else if (CensusCSVClass.getName().equals("censusanalyser.USCensusCsv")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCsv.class::cast)
                        .forEach(censusCsv -> censusStateMap.put(censusCsv.State, new CensussDAO(censusCsv)));
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.HEADER_EXCEPTION);
        }
        return censusStateMap;
    }
}
