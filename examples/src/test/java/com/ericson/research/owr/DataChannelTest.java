package com.ericson.research.owr;

import com.ericsson.research.owr.Candidate;
import com.ericsson.research.owr.DataChannel;
import com.ericsson.research.owr.DataChannelReadyState;
import com.ericsson.research.owr.DataSession;
import com.ericsson.research.owr.Owr;
import com.ericsson.research.owr.OwrBridge;
import com.ericsson.research.owr.Session;
import com.ericsson.research.owr.TransportAgent;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class DataChannelTest {

    @BeforeClass
    public static void beforeClass() {
        OwrBridge.start();
        Owr.runInBackground();
    }

    @AfterClass
    public static void tearDown() {
        Owr.quit();
    }

    private Session.OnNewCandidateListener addCandidateToOtherSession(final DataSession addToOtherSession) {
        return new Session.OnNewCandidateListener() {
            public void onNewCandidate(Candidate candidate) {
                addToOtherSession.addRemoteCandidate(candidate);
            }
        };
    }

    private final TransportAgent leftTransportAgent = new TransportAgent(false);
    {
        leftTransportAgent.setLocalPortRange(5000,5999);
        leftTransportAgent.addLocalAddress("127.0.0.1");
    }

    private final TransportAgent rightTransportAgent = new TransportAgent(false);
    {
        rightTransportAgent.setLocalPortRange(5000,5999);
        rightTransportAgent.addLocalAddress("127.0.0.1");
    }

    private final DataSession leftSession = new DataSession(true);
    private final DataSession rightSession  = new DataSession(false);
    {
        leftSession.setSctpLocalPort(5000);
        leftSession.setSctpRemotePort(5000);
        leftSession.addOnNewCandidateListener(addCandidateToOtherSession(rightSession));
        leftTransportAgent.addSession(leftSession);
    }
    {
        rightSession.setSctpLocalPort(5000);
        rightSession.setSctpRemotePort(5000);
        rightSession.addOnNewCandidateListener(addCandidateToOtherSession(leftSession));
        rightTransportAgent.addSession(rightSession);
    }


    private Future<DataChannelReadyState> readyStateArrived(DataChannel dc, final DataChannelReadyState waitForReadyState) {
        final CompletableFuture<DataChannelReadyState> ready = new CompletableFuture<DataChannelReadyState>();
        dc.addReadyStateChangeListener(new DataChannel.ReadyStateChangeListener() {
            public void onReadyStateChanged(DataChannelReadyState readyState) {
                if (waitForReadyState == readyState)
                    ready.complete(readyState);
            }
        });
        return ready;
    }

    private Future<byte[]> nextData(DataChannel dc) {
        final CompletableFuture<byte[]> data = new CompletableFuture<byte[]>();
        dc.addOnBinaryDataListener(new DataChannel.OnBinaryDataListener() {
            public void onBinaryData(byte[] binary_data) {
                data.complete(binary_data.clone());
            }
        });
        return data;
    }

    @Test
    public void dataChannelTalk() throws InterruptedException, ExecutionException {


        final short channelId = 1;

        final DataChannel left = new DataChannel(false, 5000, -1, "OTP", true, channelId, "prenegotiated");
        final DataChannel right = new DataChannel(false, 5000, -1, "OTP", true, channelId, "prenegotiated");
        leftSession.addDataChannel(left);
        rightSession.addDataChannel(right);

        final Future<DataChannelReadyState> leftReady = readyStateArrived(left, DataChannelReadyState.OPEN);
        final Future<DataChannelReadyState> rightReady = readyStateArrived(right, DataChannelReadyState.OPEN);

        assertEquals(DataChannelReadyState.OPEN, leftReady.get());
        assertEquals(DataChannelReadyState.OPEN, rightReady.get());

        final Future<byte[]> dataOnRight = nextData(right);
        left.sendBinary("hello WEBRTC in javaland!".getBytes());
        assertArrayEquals(dataOnRight.get(), "hello WEBRTC in javaland!".getBytes());

        final Future<byte[]> dataOnLeft = nextData(left);
        right.sendBinary("EOT".getBytes());
        assertArrayEquals(dataOnLeft.get(), "EOT".getBytes());

    }

}
