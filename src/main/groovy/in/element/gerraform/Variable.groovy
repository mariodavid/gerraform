package in.element.gerraform

class Variable extends Artifact {
    String ref() {
        getString("var", name)
    }
}
