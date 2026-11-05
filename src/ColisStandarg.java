public class ColisStandard extends Colis {
    public ColisStandard(String code, int poids, boolean horsDakar) {
        super(code, poids, horsDakar);
    }

    @Override
    public long tarif() {
        return super.tarif(); // tarif de Colis
    }

    @Override
    public String sigle() { return "STD"; }

    @Override
    public int priorite() { return 1; }
}