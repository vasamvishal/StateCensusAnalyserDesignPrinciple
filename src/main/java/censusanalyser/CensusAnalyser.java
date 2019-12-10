package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensussDAO> censusList = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensussDAO>();
    }


    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCsvIterator = csvBuilder.
                    getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (censusCsvIterator.hasNext()) {
                this.censusList.add(new IndiaCensussDAO(censusCsvIterator.next()));
            }
            return censusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        }
    }

    public int loadIndiaStateCode(String indiaStateCodeFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaStateCodeFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List csvFileList = csvBuilder
                    .getCSVFileList(reader, IndiaStateCodeCSV.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> csvIterable = () -> censusCSVIterator;
        int numOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEateries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList.size() == 0 || censusList == null) {
            throw new CensusAnalyserException("List is Empty", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensussDAO> censusCSVComparator = Comparator.comparing(census -> census.state);
        this.sort(censusCSVComparator);
        String sortedStateData = new Gson().toJson(this.censusList);
        return sortedStateData;
    }

    private void sort(Comparator<IndiaCensussDAO> censusCSVComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensussDAO census1 = censusList.get(j);
                IndiaCensussDAO census2 = censusList.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }
}

