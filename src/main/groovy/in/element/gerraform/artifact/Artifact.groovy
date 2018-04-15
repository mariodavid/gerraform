package in.element.gerraform.artifact

class Artifact {
    String name

    String getString(String... parts) {
        "\${${parts.join(".")}}"
    }
}
