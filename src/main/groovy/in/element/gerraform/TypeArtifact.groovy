package in.element.gerraform

class TypeArtifact extends Artifact {
    String type

    String getString(String... parts) {
        "\${${parts.join(".")}}"
    }
}
