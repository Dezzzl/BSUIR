import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.FormulaType.PCNF;
import static org.example.FormulaType.PDNF;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimizeTest {
    private Parser parser;
    private static final String formula1 = "(A\\/B\\/C)/\\(A\\/B\\/!C)/\\(A\\/!B\\/!C)/\\(!A\\/B\\/!C)/\\(!A\\/!B\\/!C)";
    private static final String formula2 = "(!A/\\B/\\!C)\\/(A/\\!B/\\!C)\\/(A/\\B/\\!C)";
    private static final String formula3 = "(!A/\\B/\\C)\\/(A/\\!B/\\!C)\\/(A/\\!B/\\C)\\/(A/\\B/\\!C)\\/(A/\\B/\\C)";

    @BeforeEach
    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void testParser() {
        List<Integer> first = List.of(0, 1, 0);
        List<Integer> second = List.of(1, 0, 0);
        List<Integer> third = List.of(1, 1, 0);
        assertEquals(List.of(first, second, third), parser.parse(formula2, FormulaType.PDNF));
    }

    @Test
    public void testFormulaUtil() {
        List<Integer> first = List.of(0, 1, 0);
        List<Integer> second = List.of(1, 0, 0);
        List<Integer> third = List.of(1, 1, 0);
        assertEquals(FormulaUtil.createDNF(formula2, List.of(first, second, third)), formula2);

        first = List.of(0, 0, 0);
        second = List.of(0, 0, 1);
        third = List.of(0, 1, 1);
        List<Integer> fourth = List.of(1, 0, 1);
        List<Integer> fifth = List.of(1, 1, 1);
        assertEquals(FormulaUtil.createCNF(formula1, List.of(first, second, third, fourth, fifth)), formula1);
    }


    @Test
    public void testCalculationMethod() {
        CalculationMethod calculationMethod1 = new CalculationMethod(formula3, parser.parse(formula3, PDNF));
        String dnf = calculationMethod1.getDnfWithCalculationMethod();
        assertEquals(dnf, "A\\/(B/\\C)");
        CalculationMethod calculationMethod2 = new CalculationMethod(formula1, parser.parse(formula1, PCNF));
        String cnf = calculationMethod2.getCnfWithCalculationMethod();
        assertEquals(cnf, "!C/\\(A\\/B)");
    }


    @Test
    public void testTabularMethodTable() {
        CalculationMethod calculationMethod1 = new CalculationMethod(formula3, parser.parse(formula3, PDNF));
        List<List<Integer>> merged1 = calculationMethod1.BondingStep();
        TabularMethod tabularMethod1 = new TabularMethod(formula3, parser.parse(formula3, PDNF), merged1);
        List<List<Integer>> table = tabularMethod1.getTable();
        List<Integer> first = List.of(0, 1, 1, 1, 1);
        List<Integer> second = List.of(1, 0, 0, 0, 1);
        assertEquals(table, List.of(first, second));
    }


    @Test
    public void testTabularMethod() {
        CalculationMethod calculationMethod1 = new CalculationMethod(formula3, parser.parse(formula3, PDNF));
        List<List<Integer>> merged1 = calculationMethod1.BondingStep();
        TabularMethod tabularMethod1 = new TabularMethod(formula3, parser.parse(formula3, PDNF), merged1);
        String dnf = FormulaUtil.createDNF(formula3, tabularMethod1.removeRedundantImplicants());
        assertEquals(dnf, "A\\/(B/\\C)");
        CalculationMethod calculationMethod2 = new CalculationMethod(formula1, parser.parse(formula1, PCNF));
        List<List<Integer>> merged2 = calculationMethod2.BondingStep();
        TabularMethod tabularMethod2 = new TabularMethod(formula1, parser.parse(formula1, PCNF), merged2);
        String cnf = FormulaUtil.createCNF(formula1, tabularMethod2.removeRedundantImplicants());
        assertEquals(cnf, "!C/\\(A\\/B)");
    }


    @Test
    public void testCarnoMap() {
        TableMethod tableMethod = new TableMethod(formula2, parser.parse(formula2, PDNF), PDNF);
        List<List<Integer>> carnoMap = tableMethod.getCarnoMap();
        List<Integer> first = List.of(0, 0, 0, 1);
        List<Integer> second = List.of(1, 0, 0, 1);
        assertEquals(carnoMap, List.of(first, second));
    }


    @Test
    public void testTableMethod() {
        TableMethod tableMethod1 = new TableMethod(formula2, parser.parse(formula2, PDNF), PDNF);
        String dnf = tableMethod1.Minimize(PDNF);
        assertEquals(dnf, "(A/\\!C)\\/(B/\\!C)");
        TableMethod tableMethod2 = new TableMethod(formula1, parser.parse(formula1, PCNF), PCNF);
        String cnf = tableMethod2.Minimize(PCNF);
        assertEquals(cnf, "!C/\\(A\\/B)");
    }


}
