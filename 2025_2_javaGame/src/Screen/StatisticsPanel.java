package Screen;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class StatisticsPanel extends JPanel
{
    public enum GraphType
    {
        POINT,
        BAR
    }

    private static final int DATA_PADDING = 30;
    private static final int LABEL_PADDING = 10;
    private static final int POINT_RADIUS = 3;
    private static final int BAR_GAP_PERCENT = 30;

    private static final Color AXIS_COLOR = Color.GRAY;
    private static final Color BG_COLOR = Color.WHITE;
    private static final Color GRAPH_COLOR = new Color(132, 108, 181);
    private static final Color POINT_COLOR = new Color(120, 93, 77, 166);
    private static final Color LABEL_COLOR = Color.BLACK; // 레이블 색상 복원

    private static final int NUM_DIVISION = 5;
    private static final int Y_AXIS_MARGIN = 5;

    // VARIABLES
    private final List<Integer> data;
    private final List<String> rowLables;
    private final int MAX_VALUE;
    private final Color graphAccentColor;
    private final GraphType graphType;

    public StatisticsPanel(List<Integer> data, List<String> rowLables, Color accentColor, GraphType type) {
        if(data == null || rowLables == null) {
            throw new IllegalArgumentException("Data and row labels cannot be null.");
        }

        this.data = data;
        this.rowLables = rowLables;
        this.graphAccentColor = accentColor;
        this.graphType = type;

        int max = 0;
        for (int val : data) {
            if (val > max) max = val;
        }
        MAX_VALUE = max;

        setBackground(BG_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graph = (Graphics2D) g;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int graphWidth = getWidth() - 2 * DATA_PADDING;
        int graphHeight = getHeight() - 2 * DATA_PADDING;

        if (graphWidth <= 0 || graphHeight <= 0 || data.isEmpty()) return;

        drawAxis(graph, graphWidth, graphHeight);

        if (graphType == GraphType.POINT) {
            drawPointGraph(graph, graphWidth, graphHeight);
        } else if (graphType == GraphType.BAR) {
            drawBarGraph(graph, graphWidth, graphHeight);
        }

        setYArgument(graph, graphWidth, graphHeight);
        setXArgument(graph, graphWidth, graphHeight);
    }

    private void drawAxis(Graphics2D graph, int graphWidth, int graphHeight) {
        graph.setColor(AXIS_COLOR);
        graph.draw(new Line2D.Double(DATA_PADDING, DATA_PADDING + graphHeight, DATA_PADDING + graphWidth, DATA_PADDING + graphHeight));
        graph.draw(new Line2D.Double(DATA_PADDING, DATA_PADDING, DATA_PADDING, DATA_PADDING + graphHeight));
    }

    private void drawPointGraph(Graphics2D graph, int graphWidth, int graphHeight) {
        final int DATA_LIST_LENGTH = data.size();
        final float STEP_X = (float) graphWidth / DATA_LIST_LENGTH;
        final float Y_RANGE = (float) MAX_VALUE + Y_AXIS_MARGIN;

        graph.setColor(GRAPH_COLOR);
        graph.setStroke(new BasicStroke(2));

        float prevX = -1;
        float prevY = -1;

        for (int i = 0; i < DATA_LIST_LENGTH; ++i) {
            final float COORD_X = DATA_PADDING + i * STEP_X + (STEP_X / 2);
            final float DATA_VALUE = (float) data.get(i);
            final float COORD_Y = DATA_PADDING + graphHeight - (DATA_VALUE * graphHeight / Y_RANGE);

            if (prevX != -1) {
                graph.draw(new Line2D.Float(prevX, prevY, COORD_X, COORD_Y));
            }

            graph.setColor(POINT_COLOR);
            graph.fillOval((int) (COORD_X - POINT_RADIUS), (int) (COORD_Y - POINT_RADIUS), POINT_RADIUS * 2, POINT_RADIUS * 2);

            // Draw Point Value
            graph.setColor(LABEL_COLOR);
            String value = String.valueOf(data.get(i));
            float textWidth = graph.getFontMetrics().stringWidth(value);
            graph.drawString(value, COORD_X - (textWidth/2), COORD_Y - 10);

            prevX = COORD_X;
            prevY = COORD_Y;
        }
    }

    private void drawBarGraph(final Graphics2D graph, final int graphWidth, final int graphHeight)
    {
        final int DATA_LIST_LENGTH = data.size();
        final float STEP_X = (float) graphWidth / DATA_LIST_LENGTH;
        final float Y_RANGE = (float) MAX_VALUE + Y_AXIS_MARGIN;

        final float BAR_WIDTH = STEP_X * (100 - BAR_GAP_PERCENT) / 100;
        final float BAR_GAP = STEP_X * BAR_GAP_PERCENT / 100;

        graph.setColor(graphAccentColor);

        for (int i = 0; i < DATA_LIST_LENGTH; ++i) {
            final float DATA_VALUE = (float) data.get(i);
            final float COORD_X = DATA_PADDING + i * STEP_X + (BAR_GAP / 2);
            final float HEIGHT = DATA_VALUE * graphHeight / Y_RANGE;
            final float COORD_Y = DATA_PADDING + graphHeight - HEIGHT;

            // Draw Bar
            graph.fill(new Rectangle2D.Float(COORD_X, COORD_Y, BAR_WIDTH, HEIGHT));

            // Draw Value on top of the bar
            graph.setColor(LABEL_COLOR);
            String value = String.valueOf(data.get(i));
            float textWidth = graph.getFontMetrics().stringWidth(value);
            graph.drawString(value, COORD_X + (BAR_WIDTH/2) - (textWidth/2), COORD_Y - 5);
        }
    }

    private void setYArgument(Graphics2D graph, int graphWidth, int graphHeight) {
        final int Y_RANGE = MAX_VALUE + Y_AXIS_MARGIN;
        final double STEP_Y = (double) graphHeight / NUM_DIVISION;

        graph.setColor(AXIS_COLOR);

        for (int i = 0; i <= NUM_DIVISION; ++i) {
            final double coordY = DATA_PADDING + graphHeight - i * STEP_Y;
            final int value = (int) (Y_RANGE * i / NUM_DIVISION);
            String yLabel = String.valueOf(value);

            if (i > 0) {
                graph.draw(new Line2D.Double(DATA_PADDING, coordY, DATA_PADDING + graphWidth, coordY));
            }

            graph.setColor(LABEL_COLOR);
            graph.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            int labelWidth = graph.getFontMetrics().stringWidth(yLabel);
            graph.drawString(yLabel, (float) (DATA_PADDING - labelWidth - LABEL_PADDING), (float) coordY + 5);
        }
    }

    private void setXArgument(Graphics2D graph, int graphWidth, int graphHeight) {
        final int DATA_LIST_LENGTH = rowLables.size();
        if (DATA_LIST_LENGTH == 0) return;
        final double STEP_X = (double) graphWidth / DATA_LIST_LENGTH;

        graph.setColor(LABEL_COLOR);
        graph.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

        FontMetrics fm = graph.getFontMetrics();

        for (int i = 0; i < DATA_LIST_LENGTH; ++i) {
            double x = DATA_PADDING + i * STEP_X + (STEP_X / 2);
            final String label = rowLables.get(i);
            int labelWidth = fm.stringWidth(label);

            float adjustedX = (float) x - (labelWidth / 2);
            float drawY = (float) DATA_PADDING + graphHeight + LABEL_PADDING;

            graph.drawString(label, adjustedX, drawY);
        }
    }
}