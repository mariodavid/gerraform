package in.element.gerraform.artifact

class Resource extends TypeArtifact {

    def ref(String property) {
        getString(type, name, property)
    }
}
