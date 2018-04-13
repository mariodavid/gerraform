package in.element.gerraform

class Local extends Artifact {
    String ref() {
        getString("local", name)
    }
}
