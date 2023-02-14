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
}
