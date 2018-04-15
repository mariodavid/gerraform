package in.element.gerraform.artifact

class Local extends Artifact {
    String ref() {
        getString("local", name)
    }
}
