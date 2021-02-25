package de.viadee.cleancode.spacestation.modules.supply;

public class SalamiStorage {
    private Integer salamiStorage = 0;

    /**
     * @param withSilces to refill the storage with if empty
     */
    public void refillSalamiStorage(int withSilces) {
        if (salamiStorage == 0)
        {
            salamiStorage += withSilces;
        }
        else {
            salamiStorage = salamiStorage;
        }
    }

    public Integer getSalamiStorage() {
        return salamiStorage;
    }

    public Integer getStorage() {
        return salamiStorage;
    }

    public void pull(int i) {
        salamiStorage -= i;
    }
}
