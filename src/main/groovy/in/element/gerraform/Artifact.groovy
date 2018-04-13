package in.element.gerraform

class Artifact {
    String name

    String getString(String... parts) {
        "\${${parts.join(".")}}"
    }
}
