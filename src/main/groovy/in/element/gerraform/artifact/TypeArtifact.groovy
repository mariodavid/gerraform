package in.element.gerraform.artifact

class TypeArtifact extends Artifact {
    String type

    String getString(String... parts) {
        "\${${parts.join(".")}}"
    }
}
