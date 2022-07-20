import org.pushingpixels.trident.Timeline;

public class HelloWorld {
    private float value;

    // Note that the actual numbers depend on the current system load.
    public void setValue(float newValue) {
        System.out.println(this.value + " -> " + newValue);
        this.value = newValue;
    }

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        Timeline timeline = new Timeline(helloWorld);
        timeline.addPropertyToInterpolate("value", 0.0f, 1.0f);
        System.out.println("default Duration = " + timeline.getDuration());
        timeline.play();

        // make sure that the application waits long enough for the timeline to finish playing
        // long enough? 120 % of default Duration or more   
        try {
            Thread.sleep(3000);
        } catch (Exception exc) {
        }

    }
}
