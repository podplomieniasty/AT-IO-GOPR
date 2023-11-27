package org.atar.supportsys.event;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SATValidator {

    private final String sat;
    private Map<String, String> errors = new HashMap<>();

    public SATValidator(String _sat) {
        this.sat = _sat;
    }

    public String getErrorsAsString() {
        StringBuilder bld = new StringBuilder();
        errors.forEach((key, val) -> {
            bld.append(String.format("%s -> %s", key, val)).append("\r\n");
        });
        return bld.toString();
    }

    private boolean arePrefixesCorrect(String formula) {
        String regex = "^E[0-9] ?d[0-9] ?:.*";
        boolean isCorrect = formula.matches(regex);
        if(!isCorrect) this.errors.put("Invalid prefix", formula);
        return isCorrect;
    }

    private String removePrefixes(String formula) {
        String regex = "^E[0-9] ?d[0-9] ?:";
        return formula.replaceAll(regex, "");
    }

    private boolean areParenthesesCorrect(String formula) {
        String s = formula;
        int openedParentheses = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') openedParentheses++;
            else if (s.charAt(i) == ')') {
                if(openedParentheses == 0) return false;
                openedParentheses--;
            }
        }
        if(!(openedParentheses == 0)) this.errors.put("Invalid parentheses", formula);
        return openedParentheses == 0;
    }

    private String removeParentheses(String formula) {
        return formula.replaceAll("\\(|\\)", "");
    }

    private boolean isFormulaCorrect(String formula) {
        String withoutSpaces = formula.replaceAll(" ", "");
        String regex = "^(([wftr][123]|a[12345])(v|\\^))*([wftr][123]|a[12345])$"; // what the fuck
        boolean isCorrect = withoutSpaces.matches(regex);
        if(!isCorrect) this.errors.put("Invalid formula", formula);
        return isCorrect;
    }

    public boolean isSyntaxCorrect(List<String> formulas) {
        // czemu ::funkcja? bo w taki sposob mozna przekazac
        // funkcje jako callback
        long correctFormulas = formulas.stream()
                .filter(this::arePrefixesCorrect)
                .map(this::removePrefixes)
                .filter(this::areParenthesesCorrect)
                .map(this::removeParentheses)
                .filter(this::isFormulaCorrect)
                .count();
        return formulas.size() == correctFormulas;
    }

    private boolean isComplete(List<String> formulas) {
        Set<String> necessaryPrefixes = new HashSet<>(Arrays.asList(
                "E5 d4", "E5 d3", "E5 d2",
                "E5 d1", "E4 d4", "E4 d3",
                "E4 d2", "E4 d1", "E3 d4",
                "E3 d3", "E3 d2", "E3 d1",
                "E2 d4", "E2 d3", "E2 d2",
                "E2 d1"
        ));
        Set<String> allPrefixes = formulas
                .stream()
                .map(formula -> formula
                    .replaceAll(" : .*", ""))
                    .collect(Collectors.toSet());
        boolean isCorrect = allPrefixes.containsAll(necessaryPrefixes);
        if(!isCorrect) {
            necessaryPrefixes.removeAll(allPrefixes);
            this.errors.put("Incomplete problem", "Missing prefixes " + necessaryPrefixes.toString());
        }
        return isCorrect;
    }

    public boolean isValid() {
        List<String> formulas = Arrays.asList(sat
                .replaceAll("\r", "")
                .split("\n")
        );
        if(!isSyntaxCorrect(formulas)) return false;
        if(!isComplete(formulas)) return false;
        return true;
    }
}
