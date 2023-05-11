package darth.jminas;

public class Timer extends Thread {

    private boolean active = false;

    @Override
    public void run() {
        int min = 0, seconds = 0;
        active = true;
        while (active) {
            try {
                PanelSuperior.UpdateTime(min, seconds);
                Thread.sleep(999);
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    min += 1;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
