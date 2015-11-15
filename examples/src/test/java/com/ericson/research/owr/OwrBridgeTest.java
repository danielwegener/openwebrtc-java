package com.ericson.research.owr;

import com.ericsson.research.owr.CaptureSourcesCallback;
import com.ericsson.research.owr.MediaSource;
import com.ericsson.research.owr.MediaType;
import com.ericsson.research.owr.Owr;
import com.ericsson.research.owr.OwrBridge;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class OwrBridgeTest {

    @Test
    public void findMediaSources() throws Exception {
        OwrBridge.start();
        Owr.runInBackground();
        final CompletableFuture<List<MediaSource>> fut = new CompletableFuture<List<MediaSource>>();

        Owr.getCaptureSources(EnumSet.of(MediaType.VIDEO), new CaptureSourcesCallback() {

            public void onCaptureSourcesCallback(final List<MediaSource> mediaSources) {
                fut.complete(new ArrayList<MediaSource>(mediaSources));

            }
        });

        List<MediaSource> mediaSources = fut.get();

        Owr.quit();
    }

}
