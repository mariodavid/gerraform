package in.element.gerraform.artifact

class Module extends Artifact {

    def ref(String property) {
        getString("module", name, property)
    }
}
