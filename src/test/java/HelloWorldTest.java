import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.Timeline.Builder;

@RunWith(JUnit4.class)
public class HelloWorldTest extends junit.framework.TestCase {
	
//	@BeforeClass
//	public static void beforeClass() {
//		System.out.println("@BeforeClass");
//	}
//
//	@AfterClass
//	public static void afterClass() {
//		System.out.println("@AfterClass");
//	}
//
//	@Before
//	@Override
//	public void setUp() {
//		System.out.println("@Before");
//	}
//
//	@After
//	@Override
//	public void tearDown() {
//		System.out.println("@After");
//	}

	static final long DURATION = Timeline.DEFAULT_DURATION; //500;
	static final int WAITFACTOR = 120; // % of DURATION

	public class TestHelloWorld {
	    private float value;
	    private float last;
	    public float getLast() {
	    	return last;
	    }
	    // Note that the actual values depend on the current system load.
	    public void setValue(float newValue) {
	        System.out.println(this.value + " -> " + newValue);
	        this.value = newValue;
	        this.last = newValue;
	    }
	}
	@Test
	public void testDefaultDuration() {
		TestHelloWorld helloWorld = new TestHelloWorld();
        Builder tlb = Timeline.builder(helloWorld);
        tlb.addPropertyToInterpolate("value", 0.0f, 1.0f);
        Timeline timeline = tlb.build();
		assertEquals(DURATION, timeline.getDuration());
		
		timeline.play();
		try {
	        // make sure that the application waits long enough for the timeline to finish playing
	        // long enough? 120 % of default Duration or more 
			long wait = DURATION*WAITFACTOR/100;
			Thread.sleep(wait);
			assertEquals(Timeline.TimelineState.IDLE, timeline.getState());
//	        System.out.println(" timeline.getState() " + timeline.getState());
			System.out.println(" waiting " + wait + "ms is long enough!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// 2nd test - we wait not long enough
		// last is still 1.0
		assertTrue(1f==helloWorld.getLast());
		
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
