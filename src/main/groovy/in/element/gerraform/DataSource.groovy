package in.element.gerraform

class DataSource extends TypeArtifact {

    String ref(String property) {
        getString("data", type, name, property)
    }
}
