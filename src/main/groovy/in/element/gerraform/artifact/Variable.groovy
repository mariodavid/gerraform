package in.element.gerraform.artifact

class Variable extends Artifact {
    String ref() {
        getString("var", name)
    }
}
