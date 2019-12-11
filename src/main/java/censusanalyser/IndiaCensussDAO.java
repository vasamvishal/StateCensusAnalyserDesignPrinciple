package censusanalyser;

public class IndiaCensussDAO {
    public String state;
    public String StateCode;
    public int population;
    public int AreaInSqKm;
    public int DensityPerSqKm;

    public IndiaCensussDAO(IndiaCensusCSV indiaCensusCSV) {
        this.population=indiaCensusCSV.population;
        this.state=indiaCensusCSV.state;
        this.AreaInSqKm=indiaCensusCSV.areaInSqKm;
        this.DensityPerSqKm=indiaCensusCSV.densityPerSqKm;
    }
}
