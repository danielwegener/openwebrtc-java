package com.ericson.research.owr;

import com.ericsson.research.owr.Owr;
import com.ericsson.research.owr.OwrBridge;
import org.junit.Test;

public class OwrBridgeTest {

    @Test
    public void initBridge() {
        OwrBridge.start();
        Owr.init();
        Owr.quit();
    }

}
