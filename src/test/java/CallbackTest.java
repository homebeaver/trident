import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.pushingpixels.trident.api.Timeline;
import org.pushingpixels.trident.api.Timeline.Builder;
import org.pushingpixels.trident.api.Timeline.TimelineState;
import org.pushingpixels.trident.api.callback.TimelineCallbackAdapter;

/**
 * this test do not use Timeline.addPropertyToInterpolate() because there is no setter for a timeline property.
 * 
 * The timeline callback TimelineCallback.onTimelinePulse() method is used instead.
 * 
 * @author homeb
 *
 */
@RunWith(JUnit4.class)
public class CallbackTest extends junit.framework.TestCase {
	
	private static final Logger LOG = Logger.getLogger(CallbackTest.class.getName());
	static final long DURATION = Timeline.DEFAULT_DURATION; //500;
	static final int WAITFACTOR = 120; // % of DURATION

	//                     public class TimelineCallbackAdapter implements TimelineCallback {
	public class TestHelloWorld extends TimelineCallbackAdapter {
	    private float last;
	    private int pulseno = 0;
	    public float getLast() {
	    	return last;
	    }
	    
	    @Override
	    public void onTimelineStateChanged(TimelineState oldState, TimelineState newState,
	            float durationFraction, float timelinePosition) {
	        LOG.info("oldState "+ oldState + " -> " + newState + " , durationFraction="+durationFraction + " , timelinePosition="+timelinePosition);
	        if(newState==TimelineState.READY) {
		        this.pulseno = 0;
	        }
	        if(newState==TimelineState.DONE) {
		        this.last = timelinePosition;
	        }
	    }

	    @Override
	    public void onTimelinePulse(float durationFraction, float timelinePosition) {
	        System.out.println("pulseno "+ pulseno + ": " + durationFraction + " => "+timelinePosition);
	        // no onTimelinePulse callback on last pulse at 1.0f !!!
	        this.pulseno++;
	        this.last = timelinePosition;
	    }

	}
	@Test
	public void testDefaultDuration() {
		TestHelloWorld helloWorld = new TestHelloWorld();
        Builder tlb = Timeline.builder();
//        tlb.addPropertyToInterpolate("value", 0.0f, 1.0f);  // not used! addCallback instead:
		tlb.addCallback(helloWorld);
        Timeline timeline = tlb.build();
		assertEquals(DURATION, timeline.getDuration());
		
		timeline.play();
		try {
	        // make sure that the application waits long enough for the timeline to finish playing
	        // long enough? 120 % of default Duration or more 
			long wait = DURATION*WAITFACTOR/100;
			Thread.sleep(wait);
			assertEquals(Timeline.TimelineState.IDLE, timeline.getState()); // ??? bei mvn install auch mal PLAYING_FORWARD
			// TODO daher vll LOG, statt out.println damit Zeiten sichtbar werden, LOG per default nur sekundengenau
//	        System.out.println(" timeline.getState() " + timeline.getState());
			System.out.println(" waiting " + wait + "ms is long enough!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// 2nd test - we wait not long enough
		// last is still 1.0
		System.out.println(" helloWorld.getLast() " + helloWorld.getLast() + " helloWorld.pulseno="+helloWorld.pulseno);
		assertTrue(1f==helloWorld.getLast());
		assertTrue(helloWorld.pulseno>0);
		
		System.out.println("\n playReverse - waiting " + DURATION + "ms will be NOT long enough:");
		timeline.playReverse();;
		try {
			Thread.sleep(DURATION);
			assertEquals(Timeline.TimelineState.PLAYING_REVERSE, timeline.getState());
	        System.out.println(" not finished: timeline.getState() " + timeline.getState());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(0f<helloWorld.getLast()); // last is NOT 0 because playing not finished
		
	}

}
