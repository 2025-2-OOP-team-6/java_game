package Screen;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;


import java.awt.geom.Line2D;
import java.util.List;

public class StatisticsPanel extends JPanel
{
    //CONST
    private static final int DATA_PADDING = 30;
    private static final int LABEL_PADDING = 10;
    private static final int POINT_RADIUS = 3;
    private static final Color AXIS_COLOR = Color.GRAY;
    private static final Color GRAPH_COLOR = new Color(209, 195, 231);
    private static final Color POINT_COLOR = new Color(120, 93, 77, 166);

    //VARIABLES
    private final List<Integer> data;
    private final List<String> rowLables;
    private final int MAX_VALUE;


    public StatisticsPanel(List<Integer> data, List<String> rowLables)
    {
        assert(!(data == null) && !(rowLables != null)) : "Error: the data and rowlabels are null";

        this.data = data;
        this.rowLables = rowLables;
        this.MAX_VALUE = getMaxValue(data);
    }

    private int getMaxValue(final List<Integer> data)
    {
        int max = 0;

        for(int value : data)
        {
            if(max < value) { max = value; }
        }

        return max;
    }



    @Override
    protected void paintComponent(final Graphics g)
    {
        super.paintComponent(g);
        Graphics2D graph = (Graphics2D) g;

        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int width = getWidth();
        final int height = getHeight();
        final int graphWidth = width - 2 * DATA_PADDING;
        final int graphHeight = height - 2 * DATA_PADDING;

        graph.setColor(AXIS_COLOR);
        // - x grid lines -
        graph.drawLine(
                DATA_PADDING, DATA_PADDING + graphHeight,
                DATA_PADDING + graphWidth, DATA_PADDING + graphHeight
        );

        // - y grid lines -
        graph.drawLine(
                DATA_PADDING, DATA_PADDING,
                DATA_PADDING, DATA_PADDING + graphHeight
        );

        setXArgument(graph, graphWidth, graphHeight);
        setYArgument(graph, graphWidth, graphHeight);
        drawPointGraph(graph, graphWidth, graphHeight);
    }

    private void setYArgument(final Graphics2D graph, final int graphWidth, final int graphHeight)
    {
        graph.setColor(AXIS_COLOR);

        for(int i = 0; i < MAX_VALUE; ++i)
        {
            double coordY = DATA_PADDING + graphHeight - (i * graphHeight / MAX_VALUE);
            String yLabel = String.format("%lf", MAX_VALUE - i);

            graph.draw(new Line2D.Double(DATA_PADDING, coordY, DATA_PADDING + graphWidth, coordY));

            int labelWidth = graph.getFontMetrics().stringWidth(yLabel);
            graph.drawString(yLabel, (float) (DATA_PADDING - labelWidth - LABEL_PADDING), (float)coordY);
        }
    }

    private void setXArgument(final Graphics2D graph,final int graphWidth,  final int graphHeight)
    {
        final int DATA_LIST_LENGTH = data.size();
        final double STEP_X = (double)graphWidth / data.size();

        graph.setColor(Color.BLUE);


        for(int i = 0; i < DATA_LIST_LENGTH; ++i)
        {
            double x = DATA_PADDING + i * STEP_X;

           graph.rotate(Math.toRadians(-90), x, DATA_PADDING);
           graph.drawString(rowLables.get(i), (float)x, (float)DATA_PADDING);
           graph.rotate(Math.toRadians(90), x, DATA_PADDING);
        }
    }

    private void drawPointGraph(final Graphics2D graph, final int graphWidth, final int graphHeight)
    {
        final int DATA_LIST_LENGTH = data.size();
        final float STEP_X = graphWidth / data.size();

        graph.setColor(GRAPH_COLOR);
        graph.setStroke(new BasicStroke(2));

        float prevX = -1;
        float prevY = -1;

        for(int i = 0; i < DATA_LIST_LENGTH; ++i)
        {
            final float COORD_X = DATA_PADDING + i * STEP_X;
            final float COORD_Y = DATA_PADDING + (float)graphHeight / (float)data.get(i);

            if(prevX != -1)
            {
                graph.draw(new Line2D.Float(prevX, prevY, COORD_X, COORD_Y));
            }

            graph.setColor(POINT_COLOR);
            graph.fill(new Rectangle.Float(
                    COORD_X - POINT_RADIUS,
                    COORD_Y - POINT_RADIUS,
                    2 * POINT_RADIUS,
                    2 * POINT_RADIUS
            ));

            graph.setColor(GRAPH_COLOR);

            prevX = COORD_X;
            prevY = COORD_Y;
        }
    }
}
