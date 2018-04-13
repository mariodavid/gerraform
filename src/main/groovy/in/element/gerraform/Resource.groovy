package in.element.gerraform

class Resource extends TypeArtifact {

    def ref(String property) {
        getString(type, name, property)
    }
}
