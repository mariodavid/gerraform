package de.diedavids.gerraform.artifact

import de.diedavids.gerraform.Gerraform
import spock.lang.Specification

abstract class AbstractAtrifactSpec extends Specification {

    Gerraform tf

    void setup() {
        tf = new Gerraform()
    }


}