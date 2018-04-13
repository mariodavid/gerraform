package in.element.gerraform

import spock.lang.Specification

abstract class AbstractAtrifactSpec extends Specification {

    Gerraform tf

    void setup() {
        tf = new Gerraform()
    }


}