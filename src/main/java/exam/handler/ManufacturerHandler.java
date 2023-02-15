package exam.handler;

public interface ManufacturerHandler {
    void getManufacturers();

    void getManufacturer();

    void getFullManufacturers();

    void removeManufacturer();

    void updateManufacturer();

    void addManufacturer();

    void getManufacturersBySouvenirNameAndYear();

    void getManufacturersThatMakesSouvenirsCheapestThenValue();

    void getSouvenirs();

    void getFullSouvenirs();

    void getSouvenir();

    void addSouvenir();

    void updateSouvenir();

    void removeSouvenir();

    void getSouvenirsByYears();

    void getSouvenirsByCountry();
}
