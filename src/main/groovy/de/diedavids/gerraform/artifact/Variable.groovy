package de.diedavids.gerraform.artifact

class Variable extends Artifact {
    String ref() {
        getString("var", name)
    }
}
