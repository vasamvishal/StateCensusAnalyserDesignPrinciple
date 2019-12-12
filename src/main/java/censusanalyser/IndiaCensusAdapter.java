package censusanalyser;

import censusanalyser.CensusAnalyser.COUNTRY;
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

public class IndiaCensusAdapter{
    public <E> Map<String, CensussDAO> loadCensusData(Class<E> CensusCSVClass, String ... csvFilePath) throws CensusAnalyserException {
        Map<String, CensussDAO> censusStateMap=new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
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
            if(csvFilePath.length == 1)return censusStateMap;
            {
              this.loadIndiaStateCode(censusStateMap,csvFilePath[1]);
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

    public <E> Map<String, CensussDAO> loadCensusData(COUNTRY country, String ... csvFilePath) throws CensusAnalyserException {
        if(country.equals(COUNTRY.INDIA)){
            return this.loadCensusData(IndiaCensusCSV.class,csvFilePath);
        }
        else if (country.equals(COUNTRY.US)){
            return this.loadCensusData(USCensusCsv.class,csvFilePath);
        }
        throw new CensusAnalyserException("Enter proper Country name", CensusAnalyserException.ExceptionType.INCORRECT_COUNTRY);
    }

    private int loadIndiaStateCode(Map<String, CensussDAO> censusStateMap, String indiaStateCodeFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaStateCodeFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCsvIterator = csvBuilder.
                    getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> censusCsvIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusStateMap.get(csvState.StateName) != null)
                    .forEach(csvState -> censusStateMap.get(csvState.StateName).StateCode = csvState.StateCode);
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.HEADER_EXCEPTION);
        }
    }
}
