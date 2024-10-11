package org.example.source;

import org.example.Util.Constants;
import org.example.Util.FormulaUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfectForms {
    private final TruthTable truthTable;

    private final List<Integer> formulaSolution;

    public PerfectForms(TruthTable truthTable) {
        this.truthTable = truthTable;
        this.formulaSolution = formulaSolution();
    }

    public String createPDNF() {
        StringBuilder PDNF = new StringBuilder();
        List<String> atomicFormulas = this.truthTable.getAtomicFormulas();
        List<List<Integer>> table = this.truthTable.getTable();
        for (int i = 0; i < this.formulaSolution.size(); i++) {
            if (this.formulaSolution.get(i) == 0) continue;
            PDNF.append("(");
            for (int j = 0; j < atomicFormulas.size(); j++) {
                if (table.get(i).get(j) == 0) {
                    PDNF.append("!" + atomicFormulas.get(j));
                } else PDNF.append(atomicFormulas.get(j));

                PDNF.append(Constants.CONJUNCTION);
            }
            if (!PDNF.isEmpty()) PDNF.deleteCharAt(PDNF.length() - 1);
            PDNF.append(")" + Constants.DISJUNCTION);
        }
        if (!PDNF.isEmpty()) PDNF.deleteCharAt(PDNF.length() - 1);
        return FormulaUtil.reverseExpressionTransformation(PDNF.toString());
    }

    public String createPCNF() {
        StringBuilder PCNF = new StringBuilder();
        List<String> atomicFormulas = this.truthTable.getAtomicFormulas();
        List<List<Integer>> table = this.truthTable.getTable();
        for (int i = 0; i < this.formulaSolution.size(); i++) {
            if (this.formulaSolution.get(i) == 1) continue;
            PCNF.append("(");
            for (int j = 0; j < atomicFormulas.size(); j++) {
                if (table.get(i).get(j) == 1) {
                    PCNF.append("!" + atomicFormulas.get(j));
                } else PCNF.append(atomicFormulas.get(j));

                PCNF.append(Constants.DISJUNCTION);
            }
            if (!PCNF.isEmpty()) PCNF.deleteCharAt(PCNF.length() - 1);
            PCNF.append(")" + Constants.CONJUNCTION);
        }
        if (!PCNF.isEmpty()) PCNF.deleteCharAt(PCNF.length() - 1);
        return FormulaUtil.reverseExpressionTransformation(PCNF.toString());
    }

    public String NumericFormPDNF() {
        StringBuilder numericPDNF = new StringBuilder();
        numericPDNF.append("(");
        for (int i = 0; i < this.formulaSolution.size(); i++) {
            if (this.formulaSolution.get(i) == 0) continue;
            else {
                numericPDNF.append(i);
                numericPDNF.append(",");
            }
        }
        if (!numericPDNF.isEmpty()) numericPDNF.deleteCharAt(numericPDNF.length() - 1);
        numericPDNF.append(")\\/");
        if (numericPDNF.length() == 3) numericPDNF = new StringBuilder();
        return numericPDNF.toString();
    }

    public String NumericFormPCNF() {
        StringBuilder numericPCNF = new StringBuilder();
        numericPCNF.append("(");
        for (int i = 0; i < this.formulaSolution.size(); i++) {
            if (this.formulaSolution.get(i) == 1) continue;
            else {
                numericPCNF.append(i);
                numericPCNF.append(",");
            }
        }
        if (!numericPCNF.isEmpty()) numericPCNF.deleteCharAt(numericPCNF.length() - 1);
        numericPCNF.append(")/\\");
        if (numericPCNF.length() == 3) numericPCNF = new StringBuilder();
        return numericPCNF.toString();
    }

    public String IndexForm() {
        int decimalNumber = 0;
        int powerOfTwo = 1;
        StringBuilder indexForm = new StringBuilder();
        for (int i = this.formulaSolution.size() - 1; i >= 0; i--) {
            int bit = this.formulaSolution.get(i);
            decimalNumber += bit * powerOfTwo;
            powerOfTwo *= 2;
            indexForm.insert(0, this.formulaSolution.get(i));
        }
        indexForm.insert(0, String.valueOf(decimalNumber) + " - ");
        return indexForm.toString();
    }

    private List<Integer> formulaSolution() {
        HashMap<String, List<Integer>> subformulas = this.truthTable.getSubformulas();

        Map.Entry<String, List<Integer>> lastEntry = null;
        for (Map.Entry<String, List<Integer>> entry : subformulas.entrySet()) {
            lastEntry = entry;
        }
        return lastEntry.getValue();
    }


}
