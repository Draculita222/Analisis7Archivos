package mati.a7a.files;

public enum FileStereotype {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G");

    public final String baseName;

    FileStereotype(String baseName) {
        this.baseName = baseName;
    }

    public static final FileStereotype[] getProcessingOrder() {
        return new FileStereotype[] {A, B, C, D, E, F, G};
    }
}
