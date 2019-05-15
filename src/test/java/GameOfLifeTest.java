import com.epam.dojo.gameoflife.services.GameOfLifeService;
import junit.framework.TestCase;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeTest {

    private GameOfLifeService gameOfLifeService = GameOfLifeService.getInstance();
    private List<Point> pointList = new ArrayList<>();
    private Dimension dimension = new Dimension();

    @Test
    public void GIVEN_a_block_input_WHEN_next_iteration_THEN_block_stays_the_same() {
        dimension.height = 4;
        dimension.width = 4;

        pointList.add(new Point(1,1 ));
        pointList.add(new Point(1,2 ));
        pointList.add(new Point(2,1 ));
        pointList.add(new Point(2,2 ));

        List<Point> result = gameOfLifeService.getNextIteration(pointList, dimension);

        TestCase.assertEquals(4, result.size());
        TestCase.assertTrue(result.contains(new Point(1,1)));
        TestCase.assertTrue(result.contains(new Point(1,2)));
        TestCase.assertTrue(result.contains(new Point(2,1)));

    }

    @Test
    public void GIVEN_a_blinker_input_WHEN_next_iteration_THEN_blinker_is_transposed() {
        dimension.height = 3;
        dimension.width = 3;

        pointList.add(new Point(1,0 ));
        pointList.add(new Point(1,1 ));
        pointList.add(new Point(1,2 ));

        List<Point> result = gameOfLifeService.getNextIteration(pointList, dimension);

        TestCase.assertEquals(3, result.size());
        TestCase.assertTrue(result.contains(new Point(0,1)));
        TestCase.assertTrue(result.contains(new Point(1,1)));
        TestCase.assertTrue(result.contains(new Point(2,1)));

    }

}
