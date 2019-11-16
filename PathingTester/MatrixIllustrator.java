
/**
 * Write a description of class MatrixIllustrator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MatrixIllustrator
{
    public static void drawMatrix(int[][] matrix)
    {
        drawGrid(matrix);
        drawElements(matrix);
    }
    
    private static void drawGrid(int[][] map)
    {
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        double cellWidth = 1.0/(double)map.length;
        double cellHeight = 1.0/(double)map[0].length;
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[0].length; j++)
            {

                //System.out.println(i + ", " + j);
                drawCell(j *cellWidth + cellWidth/2,1.0 - i *cellHeight - cellHeight/2, 
                    cellWidth, cellHeight);

            }
        }
    }
    
    
    /*
     * 0 = empty
     * 1 = wall
     * 2 = path
     * 3 = start
     * 4 = end
     */
    private static void drawElements(int[][] map)
    {
        
        double cellWidth = 1.0/(double)map.length;
        double cellHeight = 1.0/(double)map[0].length;
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[0].length; j++)
            {
                if (map[i][j] == 1)
                {
                    StdDraw.setPenColor(StdDraw.DARK_GRAY);
                    drawFillCell( (j *cellWidth + cellWidth/2),1.0 - i *cellHeight - cellHeight/2, 
                        cellWidth, cellHeight);
                }
                else if (map[i][j] == 2)
                {
                    StdDraw.setPenColor(StdDraw.RED);
                    drawFillCell( (j *cellWidth + cellWidth/2),1.0 - i *cellHeight - cellHeight/2, 
                        cellWidth, cellHeight);
                }
                else if (map[i][j] == 3)
                {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    drawFillCell( (j *cellWidth + cellWidth/2),1.0 - i *cellHeight - cellHeight/2, 
                        cellWidth, cellHeight);
                }
                else if (map[i][j] == 4)
                {
                    StdDraw.setPenColor(StdDraw.GREEN);
                    drawFillCell( (j *cellWidth + cellWidth/2),1.0 - i *cellHeight - cellHeight/2, 
                        cellWidth, cellHeight);
                }
            }
        }
    }
    
    private static void drawFillCell(double x, double y, double cellWidth, double cellHeight)
    {
        StdDraw.filledRectangle(x, y, cellWidth/2, cellHeight/2);        
    }
    
    private static void drawCell(double x, double y, double cellWidth, double cellHeight)
    {
        StdDraw.rectangle(x, y, cellWidth/2, cellHeight/2);        
    }
}
