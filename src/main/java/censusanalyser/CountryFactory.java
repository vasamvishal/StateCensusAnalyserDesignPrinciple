package censusanalyser;

public class CountryFactory {
    public static CommonAdaptar createObject(CensusAnalyser.COUNTRY country) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.COUNTRY.US)) {
            return new USCensusAdaptor();
        }
        else if (country.equals(CensusAnalyser.COUNTRY.INDIA)) {
            return new IndiaAdaptorSensor();
        }
        throw new CensusAnalyserException("Invalid country",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
