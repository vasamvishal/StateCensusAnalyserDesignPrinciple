package censusanalyser;

public class CountryFactory {
    public static CommonAdaptar createObject(CensusAnalyser.COUNTRY country) {
        if(country.equals(CensusAnalyser.COUNTRY.US))
        {
            return new USCensusAdaptor();
        }
        return new IndiaAdaptorSensor();
    }
}
