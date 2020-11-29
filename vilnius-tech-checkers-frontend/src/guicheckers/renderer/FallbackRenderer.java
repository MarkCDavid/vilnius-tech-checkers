package guicheckers.renderer;


import java.awt.*;

public class FallbackRenderer {

    private final static String MESSAGE = "NO GAME SELECTED";

    public FallbackRenderer() {
        this.message = MESSAGE;
    }

    public FallbackRenderer(String message) {
        this.message = message;
    }

    private String message;

    public void draw(Graphics2D graphics) {
        graphics.setFont(graphics.getFont().deriveFont(20f));
        graphics.setColor(Color.BLACK);

        var textPosition = getMiddleTextPosition(graphics, message);

        graphics.drawString(message, textPosition.x, textPosition.y);
    }

    private static Point getMiddleTextPosition(Graphics2D graphics, String message) {
        var fontMetrics = graphics.getFontMetrics();
        var stringBounds = fontMetrics.getStringBounds(message, graphics);
        var panelBounds = graphics.getClipBounds();

        return new Point(
                (int) (panelBounds.getWidth() / 2 - stringBounds.getWidth() / 2),
                (int) (panelBounds.getHeight() / 2 - stringBounds.getHeight() / 2)
        );
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
