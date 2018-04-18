package de.diedavids.gerraform.artifact

class DataSource extends TypeArtifact {

    String ref(String property) {
        getString("data", type, name, property)
    }
}
